package Pathfinding;

import Database.SettingsManager;
import Entity.ErrorController;
import Entity.Node;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


public class TextualDirections {
    ErrorController errorController = new ErrorController();

    private Node previousNode;
    private Node currentNode;
    private Node nextNode;
    private textDirEntry nextEntry;

    private int sRightLow = 220;
    private int rightLow = 250;
    private int rightHigh = 290;
    private int hRightHigh = 359;
    private int sLeftLow = 5;
    private int leftLow = 70;
    private int leftHigh = 110;
    private int hLeftHigh = 140;

    private String distance;
    private int distScale = 1;
    private double pix2ft;

    public TextualDirections(){
        SettingsManager settingsManager = SettingsManager.getInstance();
        pix2ft = Double.parseDouble(settingsManager.getSetting("Distance Scale"));
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

        double degrees = Math.toDegrees(angleDiff);
        if(degrees < 0){
            return (((degrees * -1) + 180.0) % 360);
        }
        else{
            return degrees;
        }
    }

    //getter for testing
    public double getAngle(Node previous, Node current, Node next){
        return findAngle(previous, current, next);
    }

    //uses the findAngle method to output the variable bits of string instructions
    //i.e. "left", "right", "straight"
    private String findTurn(){
        double currentAngle = findAngle(previousNode, currentNode, nextNode);

        if (currentAngle > rightHigh && currentAngle <= hRightHigh){
            return "Take a hard right and continue";

        }
        if (currentAngle >= rightLow && currentAngle <= rightHigh){
            return "Turn right and continue";

        }
        if (currentAngle >= sRightLow && currentAngle < rightLow){
            return "Take a slight right and continue";

        }
        if (currentAngle > leftHigh && currentAngle <= hLeftHigh){
            return "Take a hard left and continue";

        }
        if (currentAngle >= leftLow && currentAngle <= leftHigh){
            return "Turn left and continue";

        }
        if (currentAngle >= sLeftLow && currentAngle < leftLow){
            return "Take a slight left and continue";

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

        //return input.getNodeID();
    }

    //for default zoom on main screen, 196 pixels/units ~ 344 feet -> 1.76 feet per pixel
    //currently, it doesn't depend on zoom
    //get the 'distance' to have them walk by calculating edge weight and converting from pixels to feet, will be scaled by a settings manager entry in the future
    private String distNode(Node start, Node end){
        return Integer.toString((int)(Math.sqrt (((start.getXcoord() - end.getXcoord()) * (start.getXcoord() - end.getXcoord()) +
                (start.getYcoord() - end.getYcoord()) *  (start.getYcoord() - end.getYcoord()))) * pix2ft * distScale));
    }

    //method that does the work creating the textual directions
    public List<List<textDirEntry>> makeTextDir(List<Node> path){
        List<List<textDirEntry>> writtenDirections = new LinkedList();

        Collections.reverse(path);

        //takes care of size errors
        if (path.size() == 0) {
            errorController.showError("No path selected!");
            return writtenDirections;
        }
        if (path.size() == 1){
            errorController.showError("You are already there!");
            return writtenDirections;
        }


        //sets up startNode and destNode
        Node startNode = path.get(0);
        Node destNode = path.get(path.size() - 1);

        //make readable names for start and dest node. If they're hallways, just say hallway. Otherwise, use short name.
        //if no short name, use NodeID
        String startName = sNameNode(startNode);
        if(startName.equals("")){
            startName = startNode.getNodeID();
        }
        if(startNode.getNodeType().equals("HALL")){
            startName = startName + " in " + startNode.getBuilding();
        }
        String destName = sNameNode(destNode);
        if(destName.equals("")){
            destName = destNode.getNodeID();
        }
        if(destNode.getNodeType().equals("HALL")){
            destName = destName + " in " + destNode.getBuilding();
        }
        //inserts directions overview
        /*writtenDirections.add("Directions from " + startName + " to "
                + destName + ".");
        */
        //adds first step
        //writtenDirections.add("1. Proceed to " + sNameNode(path.get(1)));
        //add the list for the first floor in the directions
        writtenDirections.add(new LinkedList<textDirEntry>());
        nextEntry = (new textDirEntry(path.get(0), path.get(1), "Directions from " + startName +
                " to " + destName + "\nProceed to ",distNode(path.get(0), path.get(1))));
        writtenDirections.get(0).add(nextEntry);

        //accounts for the case where path size is 2
        if (path.size() == 2){
            return writtenDirections;
        }

        for(int i = 1; i < path.size() - 1; i++){
            //updates Nodes to match iteration
            previousNode = path.get(i-1);
            currentNode = path.get(i);
            nextNode = path.get(i+1);

            distance = distNode(currentNode, nextNode);

            nextEntry = new textDirEntry(currentNode, nextNode, findTurn(), distNode(currentNode, nextNode));
            writtenDirections.get(writtenDirections.size() - 1).add(nextEntry);

            if(!currentNode.getFloor().equals(nextNode.getFloor())){
                writtenDirections.add(new LinkedList<textDirEntry>());
            }
        }
        //need a good way to set the arrival message
        //writtenDirections.get(writtenDirections.size() - 1).get(writtenDirections.get(writtenDirections.size() - 1).size()).instruction +=
          //      "You have arrived at " + destName + "! Thank you for visiting Brigham and Women's Hospital.";

        Collections.reverse(path);
        return writtenDirections;
    }

    //getter for textual directions
    //protected List<String> getDir(List<Node> path){return makeTextDir(path);}
    protected List<List<textDirEntry>> getDir(List<Node> path){return makeTextDir(path);}


    //for now just returns an observable list of strings so it works with how we have it now
    //once we figure out how we want to do text directions, change it to do stuff with lists of lists
    public ObservableList<String> getTextDirections(List<Node> path) {
        ObservableList textualDirections = FXCollections.observableArrayList();
        List<List<textDirEntry>> thePath = makeTextDir(path);
        for(int i  = 0; i < thePath.size(); i++) {
            for(int j = 0; j < thePath.get(i).size(); j++) {
                textualDirections.add(thePath.get(i).get(j).instruction);
            }
        }
        return textualDirections;
    }

    //converts a list of lists to a numbered, intended, string of text directions
    public String toStringTextDir(List<List<textDirEntry>> list) {
        String result = "";

        for (int i = 0; i < list.size(); i++) {
            result += ("Floor " + list.get(i).get(0).currNode.getFloor() + "\n");
            for (int j = 0; j < list.get(i).size(); j++) {
                result += ("    " + (j+1) + ". " + list.get(i).get(j).instruction + "\n");
            }
        }

        return result;
    }
//PathfindingSceneController line 45
    //PathFindingFacade line 25
    //both use text directions, go change them to process the new way.

}
