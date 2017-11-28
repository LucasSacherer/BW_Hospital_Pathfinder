package boundary.sceneControllers.mapEditing;

import Database.EdgeManager;
import Editor.EdgeEditController;
import Entity.Node;
import com.jfoenix.controls.JFXTextField;

public class EdgeAdder {
    private Node startNode, endNode;
    private EdgeManager edgeManager;
    private EdgeEditController edgeEditController;
    private JFXTextField edgeXStart, edgeYStart, edgeXEnd, edgeYend;
    public EdgeAdder(EdgeEditController edgeEditController, EdgeManager edgeManager, JFXTextField edgeXStartAdd, JFXTextField edgeYStartAdd,
                     JFXTextField edgeXEndAdd, JFXTextField edgeYEndAdd) {
        this.edgeEditController = edgeEditController;
        this.edgeManager = edgeManager;
        this.edgeXEnd = edgeXEndAdd;
        this.edgeYend = edgeYEndAdd;
        this.edgeXStart = edgeXStartAdd;
        this.edgeYStart = edgeYStartAdd;
    }

    public void clickOnMap() {

    }
}
