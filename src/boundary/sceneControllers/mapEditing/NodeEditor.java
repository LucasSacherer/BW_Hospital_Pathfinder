package boundary.sceneControllers.mapEditing;

import Editor.NodeEditController;
import Entity.Node;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class NodeEditor {
    private Node current;
    private NodeEditController nodeEditController;
    private JFXTextField nodeID, xPos, yPos, shortName, longName;
    private JFXComboBox nodeTypeCombo;
    private ObservableList nodeTypeList = FXCollections.observableArrayList(
            "HALL","REST","ELEV","LABS","EXIT","STAI","DEPT","CONF");

    public NodeEditor(NodeEditController n, JFXTextField editNodeID, JFXTextField xPosEdit, JFXTextField yPosEdit,
                      JFXComboBox nodeTypeComboEdit, JFXTextField shortNameEdit, JFXTextField longNameEdit) {
        this.nodeEditController = n;
        this.nodeID = editNodeID;
        this.xPos = xPosEdit;
        this.yPos = yPosEdit;
        this.nodeTypeCombo = nodeTypeComboEdit;
        this.shortName = shortNameEdit;
        this.longName = longNameEdit;
        nodeTypeCombo.setItems(nodeTypeList);
    }

    public void clickOnMap(Node current, GraphicsContext gc) {
        this.current = current;
        gc.setFill(Color.BLUE);
        gc.fillOval(current.getXcoord() - 7, current.getYcoord() - 7, 14, 14);
        gc.setFill(Color.YELLOW);
        gc.fillOval(current.getXcoord() - 3, current.getYcoord() - 3, 6, 6);
        nodeID.setText(current.getNodeID());
        xPos.setText("" + current.getXcoord());
        yPos.setText("" + current.getYcoord());
        shortName.setText(current.getShortName());
        longName.setText(current.getLongName());
        nodeTypeCombo.setItems(null);
        nodeTypeCombo.setItems(nodeTypeList);
    }

    public void editNode() {
        nodeEditController.deleteNode(current);
        String building = current.getBuilding();
        String nodeType = nodeTypeCombo.getSelectionModel().getSelectedItem().toString();
        int x = Integer.parseInt(xPos.getText());
        int y = Integer.parseInt(yPos.getText());
        // TODO check for bounds
        // TODO add listeners to modify the x and y coordinates
        // TODO add listeners to nodeType
        if (nodeType == null) nodeType = "HALL"; // TODO this is bogus
        Node n = new Node(current.getNodeID(), x , y, current.getFloor(), building, nodeType, longName.getText(), shortName.getText());
        nodeEditController.addNode(n);
        System.out.println(n);
        reset();
    }

    public void reset() {
        current = null;
        nodeTypeCombo.setItems(null);
        nodeTypeCombo.setItems(nodeTypeList);
        shortName.clear();
        longName.clear();
    }
}
