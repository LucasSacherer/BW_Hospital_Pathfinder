package boundary.sceneControllers;
import Database.EdgeManager;
import Database.NodeManager;
import Editor.EdgeEditController;
import Editor.NodeEditController;
import Entity.Edge;
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
    private EdgeManager edgeManager;
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

    private Tab addNode, editNode, removeNode, addEdge, removeEdge, kioskTab, edgesTab, nodesTab;


    public AdminMapController(EdgeManager em, NodeManager nm, NodeEditController n, EdgeEditController e, ImageView i, Pane mapPane,
                              Canvas canvas, MapNavigationFacade m, PathFindingFacade p, Label currentFloorNum,
                              Tab addNode, Tab editNode, Tab removeNode, Tab addEdge, Tab removeEdge, Tab kioskTab, Tab edgesTab, Tab nodesTab) {
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
        this.edgesTab = edgesTab;
        this.nodesTab = nodesTab;
        this.edgeManager = em;
    }

    public void initializeNodeAdder(JFXTextField xPos, JFXTextField yPos, JFXComboBox nodeType, JFXComboBox building,
                                    JFXTextField shortName, JFXTextField longName) {
        this.nodeAdder = new NodeAdder(nodeManager, xPos, yPos, nodeType, building, shortName, longName, gc);
        refreshCanvas();
    }

    public void initializeNodeEditor(JFXTextField editNodeID, JFXTextField xPosEdit, JFXTextField yPosEdit,
                                     JFXComboBox nodeTypeComboEdit, JFXTextField shortNameEdit,
                                     JFXTextField longNameEdit, JFXTextField editNodeTypeField) {
        this.nodeEditor = new NodeEditor(nodeEditController, editNodeID, xPosEdit, yPosEdit, nodeTypeComboEdit,
                shortNameEdit, longNameEdit, editNodeTypeField, gc);
    }

    public void initializeNodeRemover(JFXTextField xPosRemoveNode, JFXTextField yPosRemoveNode) { // TODO
        this.nodeRemover = new NodeRemover(nodeEditController, gc, xPosRemoveNode, yPosRemoveNode);
    }

    public void initializeEdgeAdder(JFXTextField edgeXStartAdd, JFXTextField edgeYStartAdd,
                                    JFXTextField edgeXEndAdd, JFXTextField edgeYEndAdd) { // TODO
        this.edgeAdder = new EdgeAdder(edgeEditController, edgeXStartAdd, edgeYStartAdd, edgeXEndAdd, edgeYEndAdd, gc);
    }

    public void initializeEdgeRemover(JFXTextField edgeXStartRemove, JFXTextField edgeYStartRemove, JFXTextField edgeXEndRemove, JFXTextField edgeYEndRemove) { // TODO
        this.edgeRemover = new EdgeRemover(edgeEditController, gc, edgeXStartRemove, edgeYStartRemove, edgeXEndRemove, edgeYEndRemove);
    }

    public void initializeKioskEditor(JFXTextField setKioskX, JFXTextField setKioskY) {
        this.kioskEditor = new KioskEditor(nodeEditController, setKioskX, setKioskY);
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

    private void drawAllEdges() {
        edgeManager.updateEdges();
        for (Edge e : edgeManager.getAllEdges()) {
            if (e.getStartNode().getFloor().equals(currentFloor) && e.getEndNode().getFloor().equals(currentFloor)) {
                drawEdge(e);
            }
        }
    }

    public void clickOnMap(MouseEvent m) {
        refreshCanvas();
        if (nodesTab.isSelected()) {
            if (addNode.isSelected()) {
                nodeAdder.clickOnMap(m);
            } else if (editNode.isSelected()) {
                snapToNode(m);
                nodeEditor.clickOnMap(currentLoc);
            } else if (removeNode.isSelected()) {
                snapToNode(m);
                nodeRemover.clickOnMap(currentLoc);
            }
        }
        else if (edgesTab.isSelected()) {
            snapToNode(m);
            if (addEdge.isSelected()) edgeAdder.clickOnMap(currentLoc);
            else if (removeEdge.isSelected()) edgeRemover.clickOnMap(currentLoc);
        }
        else if (kioskTab.isSelected()) {
            snapToNode(m);
            kioskEditor.clickOnMap(currentLoc);
        }
    }

    public void drawEdge(Edge edge) {
        Node startNode = edge.getStartNode();
        int sx = startNode.getXcoord();
        int sy = startNode.getYcoord();
        Node endNode = edge.getEndNode();
        int ex = endNode.getXcoord();
        int ey = endNode.getYcoord();

        gc.setLineWidth(3);
        gc.strokeLine(sx,sy,ex,ey);
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
        refreshCanvas();
    }


    public void resetNodeButtonRemove() {
        nodeRemover.reset();
        refreshCanvas();
    }

    public void removeNodeButton() {
        nodeRemover.remove();
        refreshCanvas();
    }

    public void removeEdgeButton() {
        edgeRemover.remove();
        refreshCanvas();
    }

    public void resetEdgeButtonRemove() { }

    public void addEdgeButton() {
        edgeAdder.addEdge();
        refreshCanvas();
    }

    public void resetEdgeButtonAdd() {
        edgeAdder.reset();
        refreshCanvas();
    }

    public void setKioskLocation(){
        kioskEditor.setKiosk();
        refreshCanvas();
    }

    public void resetKioskScene() {
        kioskEditor.reset();
        refreshCanvas();
    }
}


