package Pathfinding;

import Entity.ErrorScreen;
import Entity.Node;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class TextualDirections {
    ErrorScreen errorScreen = new ErrorScreen();

    private Node previousNode;
    private Node currentNode;
    private Node nextNode;
    private int leftLow = 220;
    private int leftHigh = 359;
    private int rightLow = 0;
    private int rightHigh = 140;

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

    //finds and returns the Node's name to be used in directions
    private String nameNode(Node input){
        return input.getNodeID();
    }

    //method that does the work creating the textual directions
    private List<String> makeTextDir(List<Node> path){
        List<String> writtenDirections = new LinkedList();

        //takes care of size errors
        if (path.size() == 0) {
            errorScreen.displayError("ERROR: No path selected!");
            return writtenDirections;
        }
        if (path.size() == 1){
            errorScreen.displayError("ERROR: You are already there!");
            return writtenDirections;
        }


        //sets up startNode and destNode
        Node startNode = path.get(0);
        Node destNode = path.get(path.size() - 1);

        //inserts directions overview
        writtenDirections.add("Directions from " + nameNode(startNode) + " to "
                + nameNode(destNode) + ".");

        //adds first step
        writtenDirections.add("Proceed to " + nameNode(path.get(1)));

        //accounts for the case where path size is 2
        if (path.size() == 2){
            return writtenDirections;
        }

        for(int i = 1; i < path.size() - 2; i++){
            //updates Nodes to match iteration
            previousNode = path.get(i-1);
            currentNode = path.get(i);
            nextNode = path.get(i+1);

            //deviates directions text if you're taking the stairs or elevator to a different floor
            if(currentNode.getNodeType().equals("ELEV") && !currentNode.getFloor().equals(nextNode.getFloor())){
                writtenDirections.add(Integer.toString(i+1) + ". Take " + nameNode(currentNode) +
                        " to floor " + nextNode.getFloor() + ".");
            } else if(currentNode.getNodeType().equals("STAI") && !currentNode.getFloor().equals(nextNode.getFloor())){
                writtenDirections.add(Integer.toString(i+1) + ". Take " + nameNode(currentNode) +
                        " to floor " + nextNode.getFloor() + ".");
            //deviates directions text if you're going to another building
            } else if (!currentNode.getBuilding().equals(nextNode.getBuilding())){
                writtenDirections.add(Integer.toString(i+1) + ". Exit " + currentNode.getBuilding() + " through " +
                        nameNode(currentNode) + " and enter " + nextNode.getBuilding() + " through "
                        + nameNode(nextNode) + ".");
            //default directions text
            } else{
                writtenDirections.add(Integer.toString(i+1) + ". " + findTurn() + " until you reach " +
                        nameNode(nextNode) + ".");
            }
        }
        writtenDirections.add("You have arrived! Thank you for visiting Brigham and Women's Hospital.");
        return writtenDirections;
    }

    //getter for textual directions
    public List<String> getDir(List<Node> path){return makeTextDir(path);}
}
