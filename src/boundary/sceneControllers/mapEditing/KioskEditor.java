package boundary.sceneControllers.mapEditing;

import Editor.NodeEditController;
import Entity.Node;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.canvas.GraphicsContext;

public class KioskEditor {
    private NodeEditController nodeEditController;
    private JFXTextField kioskX, kioskY;
    private Node potential;

    public KioskEditor(NodeEditController nodeEditController, JFXTextField setKioskX, JFXTextField setKioskY) {
        this.nodeEditController = nodeEditController;
    }


    public void clickOnMap(Node currentLoc) { potential = currentLoc; }

    public void setKiosk() {
        if (potential != null) nodeEditController.setKioskLocation(potential);
        reset();
    }

    public void reset() {
        kioskX.clear();
        kioskY.clear();
        potential = null;
    }
}
