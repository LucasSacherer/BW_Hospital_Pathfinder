package boundary.sceneControllers.mapEditing;

import Editor.NodeEditController;
import Entity.ErrorController;
import Entity.Node;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class NodeRemover {
    private NodeEditController nodeEditController;
    private ErrorController errorController = new ErrorController();
    private JFXTextField xPos, yPos;
    private Node current;
    private GraphicsContext gc;

    public NodeRemover(NodeEditController nodeEditController, GraphicsContext gc, JFXTextField xPosRemoveNode, JFXTextField yPosRemoveNode) {
        this.nodeEditController = nodeEditController;
        this.xPos = xPosRemoveNode;
        this.yPos = yPosRemoveNode;
        this.gc = gc;
    }

    public void remove() {
        if (current != null) {
            nodeEditController.deleteNode(current);
        } else errorController.showError("Please select a node.");
    }

    public void clickOnMap(Node current) {
        gc.setFill(Color.BLUE);
        gc.fillOval(current.getXcoord() - 7, current.getYcoord() - 7, 14, 14);
        gc.setFill(Color.YELLOW);
        gc.fillOval(current.getXcoord() - 3, current.getYcoord() - 3, 6, 6);
        xPos.setText("" + current.getXcoord());
        yPos.setText("" + current.getYcoord());
        this.current = current;
    }

    public void reset() {
        current = null;
        yPos.clear();
        xPos.clear();
    }
}
