package boundary.sceneControllers.mapEditing;

import Editor.NodeEditController;
import com.jfoenix.controls.JFXTextField;

public class NodeRemover {
    private NodeEditController nodeEditController;
    private JFXTextField xPos, yPos;

    public NodeRemover(NodeEditController nodeEditController, JFXTextField xPosRemoveNode, JFXTextField yPosRemoveNode) {
        this.nodeEditController = nodeEditController;
        this.xPos = xPosRemoveNode;
        this.yPos = yPosRemoveNode;
    }

    public void remove() {
        // nodeEditController.deleteNode();
    }
}
