package boundary.sceneControllers;
import Editor.NodeEditController;
import Entity.Node;
import MapNavigation.MapNavigationFacade;
import Pathfinding.PathFindingFacade;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;


public class AdminMapController extends AbstractMapController{
    private NodeEditController nodeEditController;

    private String longName = "";
    private String shortName = "";
    private String nodeID = "";
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
                              JFXTextField edgeYStartRemove, JFXTextField edgeXEndRemove, JFXTextField edgeYEndRemove, JFXComboBox nodetypeCombo, JFXComboBox buildingCombo) {
        super(i, mapPane, canvas, m, p, currentFloorNum);
        this.nodeEditController = nodeEditController;
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

    }

    public void editNode(){

    }

    public void deleteNode(){

    }
    public void setKioskLocation(){

    }


    public void addNodeButton() {
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


