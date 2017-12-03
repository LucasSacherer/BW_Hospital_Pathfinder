package Editor;

import Database.EdgeManager;
import Database.NodeManager;
import Database.SettingsManager;
import Entity.Node;
import Request.GenericRequestController;

import java.util.List;

public class NodeEditController {

    //should these be private? Or is package private fine?
    NodeManager nodeManager;
    SettingsManager settingsManager;
    EdgeManager edgeManager;
    GenericRequestController genericRequestController;

    public NodeEditController(NodeManager nodeM, SettingsManager setM, EdgeManager edgeM, GenericRequestController grm){
        this.nodeManager = nodeM;
        this.settingsManager = setM;
        this.edgeManager = edgeM;
        this.genericRequestController = grm;
    }

    // adds a new node to the lists of all nodes
    public void addNode(Node node) {
        nodeManager.addNode(node);
    }

    // update an already existing node
    public void editNode(Node node) {
        nodeManager.updateNode(node);
    }

    // deletes an already existing node
    public void deleteNode(Node node) {
        edgeManager.removeNeighborEdges(node);
        genericRequestController.deleteNodeRequests(node);
        nodeManager.removeNode(node);
    }

    public void setKioskLocation(Node defaultNode){
        settingsManager.setSetting("Default Node", defaultNode.getNodeID());
    }

    //first two nodes are start and end
    //it doesnt matter the order of which one is "start" or "end"
    //i=0 "Start" --  i=1 "End"
    public void alignNodes (List<Node> nodes){
        Node startNode = nodes.get(0);
        System.out.println(startNode.getNodeID());
        Node endNode = nodes.get(1);
        System.out.println(endNode.getNodeID());

        double abXdistance = endNode.getXcoord() - startNode.getXcoord();
        double abYdistance = endNode.getYcoord() - startNode.getYcoord();
        double abLineAngle = (180 * Math.atan2(abYdistance,abXdistance)) / Math.PI;
        System.out.println(abLineAngle);



        for (int i = 2; i < nodes.size(); i++){
            System.out.println(nodes.get(i).getNodeID());
            double outOfPlaceXDistance = nodes.get(i).getXcoord() - startNode.getXcoord();
            double outOfPlaceYDistance = nodes.get(i).getYcoord() - startNode.getYcoord();
            double outOfPlaceLineAngle = (180* Math.atan2(outOfPlaceYDistance,outOfPlaceXDistance)) / Math.PI;

            if (outOfPlaceLineAngle < 0){
                outOfPlaceLineAngle = 360 + outOfPlaceLineAngle;
            }
            System.out.println(outOfPlaceLineAngle);


            double bacAngle = (abLineAngle - outOfPlaceLineAngle);

            System.out.println(bacAngle);

            double xComponentToMove;
            double yComponentToMove;



        }


    }

}
