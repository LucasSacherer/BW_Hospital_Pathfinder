package Pathfinding;

import Entity.Node;

public class textDirEntry {

    Node currNode;
    Node nextNode;
    String direction;
    String distance;
    String instruction;

    textDirEntry(Node node, Node nextNode, String direction, String distance){
        this.currNode = node;
        this.nextNode = nextNode;
        this.direction = direction;
        this.distance = distance;
        //instructions are written based on this node and the next node
        //instructions for switching floors
        if(!node.getFloor().equals(nextNode.getFloor())){
            this.instruction = "Take " + sNameNode(node) + " to floor " + nextNode.getFloor() + ".";
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
