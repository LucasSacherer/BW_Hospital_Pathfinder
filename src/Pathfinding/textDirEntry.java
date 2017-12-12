package Pathfinding;

import Entity.Node;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.scene.image.Image;

public class textDirEntry extends RecursiveTreeObject<textDirEntry> {


    Node currNode;
    Node nextNode;
    String direction;
    String distance;
    String instruction;
    Image symbol;

    textDirEntry(Node node, Node nextNode, String direction, String distance){
        this.currNode = node;
        this.nextNode = nextNode;
        this.direction = direction;
        this.distance = distance;

        //set symbol based on direction, for choosing icon later
        switch (direction) {
            case "Take a hard right and continue":
                symbol = new Image("/boundary/images/circle-outline.png");
                break;
            case "Turn right and continue":
                symbol = new Image("/boundary/images/circle-outline.png");
                break;
            case "Take a slight right and continue":
                symbol = new Image("/boundary/images/circle-outline.png");
                break;
            case "Take a hard left and continue":
                symbol = new Image("/boundary/images/circle-outline.png");
                break;
            case "Turn left and continue":
                symbol = new Image("/boundary/images/circle-outline.png");
                break;
            case "Take a slight left and continue":
                symbol = new Image("/boundary/images/circle-outline.png");
                break;
            default:
                symbol = new Image("/boundary/images/circle-outline.png");
        }

        //instructions are written based on this node and the next node
        //instructions for switching floors
        if(!node.getFloor().equals(nextNode.getFloor())){
            this.instruction = "Take " + sNameNode(node) + " to floor " + nextNode.getFloor() + ".";
            //set symbol to the next floor if changing floors
            String floor = nextNode.getFloor();

            switch(floor){
                case "L2":
                    symbol = new Image("/boundary/images/circle-outline.png");
                    break;
                case "L1":
                    symbol = new Image("/boundary/images/circle-outline.png");
                case "G":
                    symbol = new Image("/boundary/images/circle-outline.png");
                case "1":
                    symbol = new Image("/boundary/images/circle-outline.png");
                case "2":
                    symbol = new Image("/boundary/images/circle-outline.png");
                case "3":
                    symbol = new Image("/boundary/images/circle-outline.png");
            }
            //instructions for switching buildings
        } else if (!node.getBuilding().equals(nextNode.getBuilding())){
            this.instruction = "Exit " + node.getBuilding() + " through " + sNameNode(node) + " and enter " + nextNode.getBuilding() + " through "
                    + sNameNode(nextNode) + ".";
            //default instruction text
        } else if (node.getNodeType().equals("HALL") && nextNode.getNodeType().equals("HALL")){
            this.instruction = direction + " down the hall for " + distance + " feet.";
        }else{
            this.instruction = direction + " for " + distance + " feet until you reach " + sNameNode(nextNode) + ".";
        }
    }

    //finds and returns the Node's short name to be used in directions, or "the Hallway" if it's a hallway
    private String sNameNode(Node input) {

        if(input.getShortName().equals("")){
            return input.getNodeID();
        }
        else if(!input.getNodeType().equals("HALL")){
            return input.getShortName();
        }
        else{
            return "the hallway";
        }
    }
    //as for the start and end, maybe just use the first and last entries?
    public Node getCurrNode() {
        return currNode;
    }

    public Node getNextNode() {
        return nextNode;
    }

    public String getDirection() {
        return direction;
    }

    public String getDistance() {
        return distance;
    }

    public String getInstruction() {
        return instruction;
    }

    public Image getSymbol() {
        return symbol;
    }
}
