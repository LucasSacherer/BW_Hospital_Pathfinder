package boundary.sceneControllers;
import Database.EdgeManager;
import Database.NodeManager;
import Editor.EdgeEditController;
import Editor.NodeEditController;
import Entity.Node;
import MapNavigation.MapNavigationFacade;
import Pathfinding.PathFindingFacade;
import boundary.sceneControllers.mapEditing.*;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.ObservableList;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;


public class AdminMapController extends AbstractMapController{
    private NodeEditController nodeEditController;
    private EdgeEditController edgeEditController;
    private NodeManager nodeManager;
    private NodeAdder nodeAdder;
    private NodeEditor nodeEditor;
    private NodeRemover nodeRemover;
    private EdgeAdder edgeAdder;
    private EdgeRemover edgeRemover;
    private KioskEditor kioskEditor;
    private String longName = "";
    private String shortName = "";
    private String nodeID = "";
    private String building, nodeType;
    private ObservableList<String> nodeTypeList, buildingList; //TODO remove

    private Tab addNode, editNode, removeNode, addEdge, removeEdge, kioskTab;


    public AdminMapController(NodeManager nm, NodeEditController n, EdgeEditController e, ImageView i, Pane mapPane,
                              Canvas canvas, MapNavigationFacade m, PathFindingFacade p, Label currentFloorNum,
                              Tab addNode, Tab editNode, Tab removeNode,Tab addEdge, Tab removeEdge, Tab kioskTab) {
        super(i, mapPane, canvas, m, p, currentFloorNum);
        this.edgeEditController = e;
        this.nodeEditController = n;
        this.nodeManager = nm;
        this.addNode = addNode;
        this.editNode = editNode;
        this.removeNode = removeNode;
        this.addEdge = addEdge;
        this.removeEdge = removeEdge;
        this.kioskTab = kioskTab;
    }

    public void initializeNodeAdder(NodeManager nodeManager, JFXTextField xPos, JFXTextField yPos, JFXComboBox nodeType, JFXComboBox building,
                                    JFXTextField shortName, JFXTextField longName) {
        this.nodeAdder = new NodeAdder(nodeManager, xPos, yPos, nodeType, building, shortName, longName);
        refreshCanvas();
    }

    public void initializeNodeEditor(JFXTextField editNodeID, JFXTextField xPosEdit, JFXTextField yPosEdit,
                                     JFXComboBox nodeTypeComboEdit, JFXTextField shortNameEdit,
                                     JFXTextField longNameEdit, JFXTextField editNodeTypeField) {
        this.nodeEditor = new NodeEditor(nodeEditController, editNodeID, xPosEdit, yPosEdit, nodeTypeComboEdit,
                shortNameEdit, longNameEdit, editNodeTypeField);
    }

    public void initializeNodeRemover(JFXTextField xPosRemoveNode, JFXTextField yPosRemoveNode) { // TODO
        this.nodeRemover = new NodeRemover(nodeEditController, xPosRemoveNode, yPosRemoveNode);
    }

    public void initializeEdgeAdder(EdgeManager edgeManager, JFXTextField edgeXStartAdd, JFXTextField edgeYStartAdd,
                                    JFXTextField edgeXEndAdd, JFXTextField edgeYEndAdd) { // TODO
        this.edgeAdder = new EdgeAdder(edgeEditController, edgeManager, edgeXStartAdd, edgeYStartAdd, edgeXEndAdd, edgeYEndAdd);
    }

    public void initializeEdgeRemove() { // TODO
        this.edgeRemover = new EdgeRemover();
    }

    public void refreshCanvas() {
        super.refreshCanvas();
        drawAllNodes();
        drawAllEdges();
    }

    private void drawAllNodes() {
        nodeManager.updateNodes();
        for (Node n : nodeManager.getAllNodes()){
            if (n.getFloor().equals(currentFloor)) {
                gc.setFill(Color.GREENYELLOW);
                gc.fillOval(n.getXcoord() - 5, n.getYcoord() - 5, 10, 10);
            }
        }
    }

    private void drawAllEdges() { }

    public void clickOnMap(MouseEvent m) {
        refreshCanvas();
        if (addNode.isSelected()) {
            nodeAdder.clickOnMap(m, gc);
        }
        else if (editNode.isSelected()) {
            snapToNode(m);
            nodeEditor.clickOnMap(currentLoc, gc);
        }
        else if (removeNode.isSelected()) {
            snapToNode(m);
            nodeRemover.clickOnMap(currentLoc, gc);
        }
        else if (addEdge.isSelected()) {
            System.out.println("in the statement");
            snapToNode(m);
            edgeAdder.clickOnMap(currentLoc, gc);
        }
        else if (removeEdge.isSelected()) {
            edgeRemover.clickOnMap();
        }
        else if (kioskTab.isSelected()) {
            snapToNode(m);
            kioskEditor.clickOnMap(currentLoc, gc);
        }
    }

    public void drawEdge() {
//        Node startNode = edge.getStartNode();
//        int sx = startNode.getXcoord();
//        int sy = startNode.getYcoord();
//        Node endNode = edge.getEndNode();
//        int ex = endNode.getXcoord();
//        int ey = endNode.getYcoord();
//
//        gc.setLineWidth(3);
//        gc.strokeLine(sx,sy,ex,ey);
    }

    public void addNode(){
        nodeAdder.addNode(nodeEditController, currentFloor);
        refreshCanvas();
    }

    public void resetNodeButtonAdd() {
        nodeAdder.reset();
        refreshCanvas();
    }

    public void editNode(){
        nodeEditor.editNode();
        refreshCanvas();
    }

    public void resetNodeButtonEdit() {
        nodeEditor.reset();
    }


    public void resetNodeButtonRemove() { nodeRemover.reset(); }

    public void removeNodeButton() {
        nodeRemover.remove();
        refreshCanvas();
    }

    public void removeEdgeButton() { }

    public void resetEdgeButtonRemove() { }

    public void addEdgeButton() { }

    public void resetEdgeButtonAdd() { edgeAdder.reset(); }

    public void setKioskLocation(){ }

}


