package boundary.sceneControllers.mapEditing;

import Database.EdgeManager;
import Editor.EdgeEditController;
import Entity.Node;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.canvas.GraphicsContext;

public class EdgeAdder {
    private Node startNode, endNode;
    private EdgeManager edgeManager;
    private EdgeEditController edgeEditController;
    private JFXTextField edgeXStart, edgeYStart, edgeXEnd, edgeYEnd;

    public EdgeAdder(EdgeEditController edgeEditController, EdgeManager edgeManager, JFXTextField edgeXStartAdd,
                     JFXTextField edgeYStartAdd, JFXTextField edgeXEndAdd, JFXTextField edgeYEndAdd) {
        this.edgeEditController = edgeEditController;
        this.edgeManager = edgeManager;
        this.edgeXEnd = edgeXEndAdd;
        this.edgeYEnd = edgeYEndAdd;
        this.edgeXStart = edgeXStartAdd;
        this.edgeYStart = edgeYStartAdd;
    }

    public void clickOnMap(Node currentLoc, GraphicsContext gc) {
        if (startNode == null) setStart(currentLoc);
        else if (endNode == null) setEnd(currentLoc);
        //todo draw the node
    }

    private void setStart(Node currentLoc) {
        startNode = currentLoc;
        edgeXStart.setText("" + startNode.getXcoord());
        edgeYStart.setText("" + startNode.getYcoord());
    }

    private void setEnd(Node currentLoc) {
        endNode = currentLoc;
        edgeXEnd.setText("" + endNode.getXcoord());
        edgeYEnd.setText("" + endNode.getYcoord());

        //todo draw the Edge
    }

    public void addNode() {

    }

    public void reset() {
        startNode = null;
        endNode = null;
        edgeYStart.clear();
        edgeXStart.clear();
        edgeYEnd.clear();
        edgeXEnd.clear();
    }
}
