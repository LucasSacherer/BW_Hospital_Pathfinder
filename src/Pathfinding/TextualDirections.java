package Pathfinding;

import Entity.ErrorController;
import Entity.Node;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


public class TextualDirections {
    ErrorController errorController = new ErrorController();

    private Node previousNode;
    private Node currentNode;
    private Node nextNode;
    private int leftLow = 220;
    private int leftHigh = 359;
    private int rightLow = 0;
    private int rightHigh = 140;

    private String distance;
    private int distScale = 1;
    private final double pix2ft = 1.76;

    public TextualDirections(){
    }

    //determines angle person is turning at currentNode by comparing angle of edges
    //between previousNode/currentNode and currentNode/nextNode
    private double findAngle(Node previous, Node current, Node next){
        int prevX = previous.getXcoord();
        int prevY = previous.getYcoord();
        int currentX = current.getXcoord();
        int currentY = current.getYcoord();
        int nextX = next.getXcoord();
        int nextY = next.getYcoord();


        //handling in polar to avoid divide-by-zero errors
        double angle1 = (Math.atan2(prevY - currentY,
                prevX - currentX));
        double angle2 = (Math.atan2(currentY - nextY,
                currentX - nextX));

        double angleDiff = (angle1 - angle2);

        if(angleDiff == 0){
            return 180;
        }

        return Math.toDegrees(angleDiff);
    }

    //getter for testing
    public double getAngle(Node previous, Node current, Node next){
        return findAngle(previous, current, next);
    }

    //uses the findAngle method to output the variable bits of string instructions
    //i.e. "left", "right", "straight"
    private String findTurn(){
        double currentAngle = findAngle(previousNode, currentNode, nextNode);
        if (currentAngle >= leftLow && currentAngle <= leftHigh){
            return "Turn left and continue";
        }
        if (currentAngle >= rightLow && currentAngle <= rightHigh){
            return "Turn right and continue";
        }
        return "Continue";
    }

    //finds and returns the Node's ID to be used in directions
    private String nameNode(Node input){
        return input.getNodeID();
    }

    //finds and returns the Node's short name to be used in directions, or "the Hallway" if it's a hallway
    private String sNameNode(Node input) {
        if(!input.getNodeType().equals("HALL")){
            return input.getShortName();
        }
        else{
            return "the hallway";
        }
    }

    //for default zoom on main screen, 196 pixels/units ~ 344 feet -> 1.76 feet per pixel
    //currently, it doesn't depend on zoom
    //get the 'distance' to have them walk by calculating edge weight and converting from pixels to feet, will be scaled by a settings manager entry in the future
    private String distNode(Node start, Node end){
        return Integer.toString((int)(Math.sqrt (((start.getXcoord() - end.getXcoord()) * (start.getXcoord() - end.getXcoord()) +
                (start.getYcoord() - end.getYcoord()) *  (start.getYcoord() - end.getYcoord()))) * pix2ft * distScale));
    }

    //method that does the work creating the textual directions
    private List<String> makeTextDir(List<Node> path){
        List<String> writtenDirections = new LinkedList();

        Collections.reverse(path);

        //takes care of size errors
        if (path.size() == 0) {
            errorController.showError("ERROR: No path selected!");
            return writtenDirections;
        }
        if (path.size() == 1){
            errorController.showError("ERROR: You are already there!");
            return writtenDirections;
        }


        //sets up startNode and destNode
        Node startNode = path.get(0);
        Node destNode = path.get(path.size() - 1);

        //inserts directions overview
        writtenDirections.add("Directions from " + nameNode(startNode) + " to "
                + nameNode(destNode) + ".");

        //adds first step
        writtenDirections.add("1. Proceed to " + sNameNode(path.get(1)));

        //accounts for the case where path size is 2
        if (path.size() == 2){
            return writtenDirections;
        }

        //for(int i = path.size() - 1; i > 0; i--){
        for(int i = 1; i < path.size() - 1; i++){
            //updates Nodes to match iteration
            previousNode = path.get(i-1);
            currentNode = path.get(i);
            nextNode = path.get(i+1);

            distance = distNode(previousNode, currentNode);

            //deviates directions text if you're taking the stairs or elevator to a different floor
            if(currentNode.getNodeType().equals("ELEV") && !currentNode.getFloor().equals(nextNode.getFloor())){
                writtenDirections.add(Integer.toString(i+1) + ". Take " + sNameNode(currentNode) +
                        " to floor " + nextNode.getFloor() + ".");
            } else if(currentNode.getNodeType().equals("STAI") && !currentNode.getFloor().equals(nextNode.getFloor())){
                writtenDirections.add(Integer.toString(i+1) + ". Take " + sNameNode(currentNode) +
                        " to floor " + nextNode.getFloor() + ".");
            //deviates directions text if you're going to another building
            } else if (!currentNode.getBuilding().equals(nextNode.getBuilding())){
                writtenDirections.add(Integer.toString(i+1) + ". Exit " + currentNode.getBuilding() + " through " +
                        sNameNode(currentNode) + " and enter " + nextNode.getBuilding() + " through "
                        + sNameNode(nextNode) + ".");
            //default directions text
            } else if (currentNode.getNodeType().equals("HALL") && nextNode.getNodeType().equals("HALL")){
                writtenDirections.add(Integer.toString(i+1) + ". " + findTurn() + " down the hall for " + distance + " feet.");
            }else{
                writtenDirections.add(Integer.toString(i+1) + ". " + findTurn() + " for " + distance + " feet until you reach " +
                        sNameNode(nextNode) + ".");
            }
        }
        writtenDirections.add("You have arrived at " + nameNode(destNode) + "! Thank you for visiting Brigham and Women's Hospital.");
        return writtenDirections;
    }

    //getter for textual directions
    protected List<String> getDir(List<Node> path){return makeTextDir(path);}
}
