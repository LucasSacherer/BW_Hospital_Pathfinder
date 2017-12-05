package boundary.sceneControllers.mapEditing;

import Editor.NodeEditController;
import Entity.Node;
import Entity.ErrorController;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class KioskEditor {
    private NodeEditController nodeEditController;
    private JFXTextField kioskX, kioskY;
    private Node potential;
    private GraphicsContext gc;
    private ErrorController errorController = new ErrorController();

    public KioskEditor(GraphicsContext gc, NodeEditController nodeEditController, JFXTextField setKioskX, JFXTextField setKioskY) {
        this.nodeEditController = nodeEditController;
        this.gc = gc;
        this.kioskY = setKioskY;
        this.kioskX = setKioskX;
    }


    public void clickOnMap(Node currentLoc) {
        potential = currentLoc;
        kioskX.setText("" + potential.getXcoord());
        kioskY.setText("" + potential.getYcoord());

        gc.setFill(Color.RED);
        gc.fillOval(potential.getXcoord() - 12, potential.getYcoord() - 12, 24, 24);

        gc.setFill(Color.YELLOW);
        gc.fillOval(potential.getXcoord() - 10, potential.getYcoord() - 10, 20, 20);

        gc.setFill(Color.BLUE);
        gc.fillOval(potential.getXcoord() - 8, potential.getYcoord() - 8, 16, 16);

        gc.setFill(Color.GREEN);
        gc.fillOval(potential.getXcoord() - 4, potential.getYcoord() - 4, 8, 8);

        gc.setFill(Color.BLACK);
    }

    public void setKiosk() {
        boolean success = true;
        try{
            potential.equals(potential);
        }
        catch(NullPointerException e){
            errorController.showError("Please select a node");
        }
        if (success) {
            nodeEditController.setKioskLocation(potential);
            reset();
        }
    }

    public void reset() {
        kioskX.clear();
        kioskY.clear();
        potential = null;
    }
}
