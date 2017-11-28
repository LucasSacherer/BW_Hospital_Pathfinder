package boundary.sceneControllers.mapEditing;

import Database.EdgeManager;
import com.jfoenix.controls.JFXTextField;

public class EdgeAdder {
    private EdgeManager edgeManager;
    private JFXTextField edgeXStart, edgeYStart, edgeXEnd, edgeYend;
    public EdgeAdder(EdgeManager edgeManager, JFXTextField edgeXStartAdd, JFXTextField edgeYStartAdd,
                     JFXTextField edgeXEndAdd, JFXTextField edgeYEndAdd) {
        this.edgeManager = edgeManager;
        this.edgeXEnd = edgeXEndAdd;
        this.edgeYend = edgeYEndAdd;
        this.edgeXStart = edgeXStartAdd;
        this.edgeYStart = edgeYStartAdd;
    }

    public void clickOnMap() {

    }
}
