package Editor;

import Database.EdgeManager;
import Database.NodeManager;
import Database.SettingsManager;
import Entity.Node;
import Request.GenericRequestController;
import org.ejml.data.FMatrixRMaj;
import org.ejml.dense.row.misc.RrefGaussJordanRowPivot_FDRM;

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
        FMatrixRMaj matrix = new FMatrixRMaj(2,3);
        matrix.add(0,0,1); // sets 0,1 to 1 (y coefficient)
        matrix.add(1,0,1); // sets 1,1 to 1

        Node startNode = nodes.get(0);
        Node endNode = nodes.get(1);

        float slope = ((float)(endNode.getYcoord() - startNode.getYcoord())) / ((float)(endNode.getXcoord() - startNode.getXcoord()));
        System.out.println(endNode.getXcoord() + " endX");
        System.out.println(startNode.getXcoord() + " StartX");
        System.out.println(slope);
        matrix.add(0,1,-slope);

        float ba = ((-slope)*(startNode.getXcoord())) + startNode.getYcoord();
        matrix.add(0,2,ba);

        float inverseSlope = (-1/slope);
        matrix.add(1,1,-inverseSlope);

        RrefGaussJordanRowPivot_FDRM rref = new RrefGaussJordanRowPivot_FDRM();

        for (int i = 2; i < nodes.size(); i++){
            FMatrixRMaj temp = matrix.copy();

            System.out.println(nodes.get(i).getXcoord() + " i X coord");
            System.out.println(nodes.get(i).getYcoord() + " i Y coord");
            float bc = (inverseSlope*nodes.get(i).getXcoord()) + nodes.get(i).getYcoord();
            temp.add(1,2,bc);

            temp.print();
            rref.reduce(temp,3);

            float changeInY = temp.get(0,2);
            float changeInX = temp.get(1,2);

            temp.print();
            System.out.println(changeInX);
            System.out.println(changeInY);

            System.out.println("BREAK");
        }


    }

}
