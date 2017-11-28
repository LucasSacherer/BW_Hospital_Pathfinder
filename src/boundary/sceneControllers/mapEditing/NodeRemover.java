package boundary.sceneControllers.mapEditing;

import Editor.NodeEditController;
import Entity.Node;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class NodeRemover {
    private NodeEditController nodeEditController;
    private JFXTextField xPos, yPos;
    private Node current;

    public NodeRemover(NodeEditController nodeEditController, JFXTextField xPosRemoveNode, JFXTextField yPosRemoveNode) {
        this.nodeEditController = nodeEditController;
        this.xPos = xPosRemoveNode;
        this.yPos = yPosRemoveNode;
    }

    public void remove() { if (current != null) nodeEditController.deleteNode(current); }

    public void clickOnMap(Node current, GraphicsContext gc) {
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