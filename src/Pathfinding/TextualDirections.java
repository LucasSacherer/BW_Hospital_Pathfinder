package Pathfinding;

import Entity.ErrorScreen;
import Entity.Node;

import java.util.ArrayList;
import java.util.LinkedList;


public class TextualDirections {
    ErrorScreen errorScreen = new ErrorScreen();

    Node startNode;
    Node destNode;
    Node previousNode;
    Node currentNode;
    Node nextNode;
    int leftLow = 150;
    int leftHigh = 359;
    int rightLow = 0;
    int rightHigh = 120;

    public TextualDirections(){
    }

    //determines angle person is turning at currentNode by comparing angle of edges
    //between previousNode/currentNode and currentNode/nextNode
    //TODO: write tests
    private double findAngle(Node previous, Node current, Node next){
        int prevX = previous.getXcoord();
        int prevY = previous.getYcoord();
        int currentX = current.getXcoord();
        int currentY = current.getYcoord();
        int nextX = next.getXcoord();
        int nextY = next.getYcoord();


        //handling in polar to avoid divide by zero errors
        double angle1 = Math.atan2(prevY - currentY,
                prevX - currentX);
        double angle2 = Math.atan2(currentY - nextY,
                currentX - nextX);

        double angleDiff = Math.abs(angle1) - Math.abs(angle2);

        return Math.toDegrees(angleDiff);
    }

    //uses the findAngle method to output the variable bits of string instructions
    //i.e. "left", "right", "straight"
    //TODO: write tests
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
    private LinkedList<String> makeTextDir(ArrayList<Node> path){
        LinkedList<String> writtenDirections = new LinkedList();

        //takes care of size errors
        if (path.size() == 0) {
            errorScreen.displayError("ERROR: No path selected!");
            return writtenDirections;
        }
        if (path.size() == 1){
            errorScreen.displayError("ERROR: You are already there!");
            return writtenDirections;
        }
        if (path.size() == 2){
            errorScreen.displayError("ERROR: Path too short!");
            return writtenDirections;
        }

        //updates startNode and destNode
        startNode = path.get(0);
        destNode = path.get(path.size() - 1);

        //inserts directions overview
        writtenDirections.add("Directions from " + nameNode(startNode) + " to "
                + nameNode(destNode) + ".");

        for(int i = 1; i < path.size() - 2; i++){
            //updates Nodes to match iteration
            previousNode = path.get(i-1);
            currentNode = path.get(i);
            nextNode = path.get(i+1);

            //deviates directions text if you're taking the stairs or elevator to a different floor
            if(currentNode.getNodeType().equals("ELEV") && currentNode.getFloor() != nextNode.getFloor()){
                writtenDirections.add(Integer.toString(i) + ". Take " + nameNode(currentNode) +
                        " to floor " + nextNode.getFloor() + ".");
            } else if(currentNode.getNodeType().equals("STAI") && currentNode.getFloor() != nextNode.getFloor()){
                writtenDirections.add(Integer.toString(i) + ". Take " + nameNode(currentNode) +
                        " to floor " + nextNode.getFloor() + ".");
            //deviates directions text if you're going to another building
            } else if(currentNode.getBuilding() != nextNode.getBuilding()){
                writtenDirections.add(Integer.toString(i) + ". Exit " + currentNode.getBuilding() + " through " +
                        nameNode(currentNode) + " and enter " + nextNode.getBuilding() + " through "
                        + nameNode(nextNode) + ".");
            //default directions text
            } else{
                writtenDirections.add(Integer.toString(i) + ". " + findTurn() + " until you reach " +
                        nameNode(nextNode) + ".");
            }
        }
        writtenDirections.add("You have arrived! Thank you for visiting Brigham and Women's Hospital.");
        return writtenDirections;
    }

    //getter for textual directions
    public LinkedList<String> getDir(ArrayList<Node> path){return makeTextDir(path);}
}
