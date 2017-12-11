package Pathfinding;

import Entity.Node;

public class textDirEntry {

    Node currNode;
    Node nextNode;
    String direction;
    String distance;
    String instruction;
    String symbol;

    textDirEntry(Node node, Node nextNode, String direction, String distance){
        this.currNode = node;
        this.nextNode = nextNode;
        this.direction = direction;
        this.distance = distance;

        //set symbol based on direction, for choosing icon later
        switch (direction) {
            case "Take a hard right and continue":
                symbol = "hardRight";
                break;
            case "Turn right and continue":
                symbol = "right";
                break;
            case "Take a slight right and continue":
                symbol = "slightRight";
                break;
            case "Take a hard left and continue":
                symbol = "hardLeft";
                break;
            case "Turn left and continue":
                symbol = "left";
                break;
            case "Take a slight left and continue":
                symbol = "slightLeft";
                break;
            default:
                symbol = "continue";
        }

        //instructions are written based on this node and the next node
        //instructions for switching floors
        if(!node.getFloor().equals(nextNode.getFloor())){
            this.instruction = "Take " + sNameNode(node) + " to floor " + nextNode.getFloor() + ".";
            //set symbol to the next floor if changing floors
            this.symbol = nextNode.getFloor();
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
}
