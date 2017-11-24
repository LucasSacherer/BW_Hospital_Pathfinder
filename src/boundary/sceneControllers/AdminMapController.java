package boundary.sceneControllers;
import Editor.NodeEditController;
import Entity.CleanUpRequest;
import Entity.Node;
import MapNavigation.MapNavigationFacade;
import Pathfinding.PathFindingFacade;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;


public class AdminMapController extends AbstractMapController{
    private NodeEditController nodeEditController;

    private String longName = "";
    private String shortName = "";
    private String nodeID = "";
    private String building, nodeType;
    private int addNodeX, addNodeY, ID=999;
    private ObservableList<String> nodeTypeList, buildingList;

    private JFXTextField xPosAddNode, yPosAddNode, xPosEdit, yPosEdit, xPosRemoveNode, yPosRemoveNode,
    xPosAddEdge, yPosAddEdge, xPosRemoveEdge, yPosRemoveEdge,
    setKioskX, setKioskY,
    shortNameAdd, shortNameEdit,
    longNameAdd, longNameEdit, requestName, requestDescription,
    edgeXStartAdd,edgeYStartAdd,edgeXEndAdd,edgeYEndAdd,
    edgeXStartRemove,edgeYStartRemove,edgeXEndRemove,edgeYEndRemove;

    private JFXComboBox nodeTypeCombo, buildingCombo;

    public AdminMapController(ImageView i, Pane mapPane, Canvas canvas, MapNavigationFacade m, PathFindingFacade p,
                              Label currentFloorNum, JFXTextField xPosAddNode, JFXTextField yPosAddNode,
                              JFXTextField xPosEdit, JFXTextField yPosEdit, JFXTextField xPosRemoveNode,
                              JFXTextField yPosRemoveNode, JFXTextField xPosAddEdge, JFXTextField yPosAddEdge,
                              JFXTextField xPosRemoveEdge, JFXTextField yPosRemoveEdge, JFXTextField setKioskX,
                              JFXTextField setKioskY, JFXTextField shortNameAdd, JFXTextField shortNameEdit,
                              JFXTextField longNameAdd, JFXTextField longNameEdit, JFXTextField requestName,
                              JFXTextField requestDescription, JFXTextField edgeXStartAdd, JFXTextField edgeYStartAdd,
                              JFXTextField edgeXEndAdd, JFXTextField edgeYEndAdd, JFXTextField edgeXStartRemove,
                              JFXTextField edgeYStartRemove, JFXTextField edgeXEndRemove, JFXTextField edgeYEndRemove,
                              JFXComboBox nodeTypeCombo, JFXComboBox buildingCombo, JFXTabPane edgeTab,
                              JFXTabPane kioskTab, JFXTabPane addNodeTab, JFXTabPane editNodeTab,
                              JFXTabPane removeNodeTab, JFXTabPane addEdgeTab, JFXTabPane removeEdgeTab) {
        super(i, mapPane, canvas, m, p, currentFloorNum);
        this.nodeEditController = nodeEditController;
        this.xPosAddNode = xPosAddNode;
        this.yPosAddNode = yPosAddNode;
        this.buildingCombo = buildingCombo;
        this.nodeTypeCombo = nodeTypeCombo;

    }

    public void initializeScene(){
        super.initializeScene();
        nodeTypeList = FXCollections.observableArrayList("HALL","REST","ELEV","LABS","EXIT","STAI","DEPT","CONF");
        buildingList = FXCollections.observableArrayList("Shapiro", "Non-Shapiro");
        nodeTypeCombo.setItems(nodeTypeList);
        buildingCombo.setItems(buildingList);
        buildingCombo.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            building = (String) buildingCombo.getItems().get(newValue.intValue());
        });
        nodeTypeCombo.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            nodeType = (String) nodeTypeCombo.getItems().get(newValue.intValue());
        });
    }

    public void refreshCanvas() {
        super.refreshCanvas();
        // drawAllNodes;
        // drawAllEdges;
    }

    public void clickOnMap(MouseEvent m) {
        // if node tool is selected:
        refreshCanvas();
        addNodeX = (int) m.getX();
        addNodeY = (int) m.getY();
        gc.setFill(Color.GREEN);
        gc.fillOval(addNodeX - 10, addNodeY - 10, 20, 20);
        xPosAddNode.setText("" + addNodeX);
        yPosAddNode.setText("" + addNodeY);
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
        String longName = "What should go here?";
        String shortName = "How about here?";
        String nodeID = "G" + nodeType + ID-- + currentFloor;
        // Node n = new Node(nodeID, addNodeX, addNodeY, currentFloor, building, nodeType, longName, shortName);
        // nodeEditController.addNode(n);
        System.out.println(building + " " + nodeType + " " + nodeID + " " + currentFloor + " " + longName + " " + shortName);
    }

    public void editNode(){

    }

    public void deleteNode(){

    }
    public void setKioskLocation(){

    }

    public void resetNodeButtonAdd() {
    }

    public void resetNodeButtonEdit() {
    }

    public void resetNodeButtonRemove() {
    }

    public void removeEdgeButton() {
    }

    public void resetEdgeButtonRemove() {
    }

    public void addEdgeButton() {
    }

    public void resetEdgeButtonAdd() {
    }

    public void removeNodeButton() {
    }
}


