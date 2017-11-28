package boundary.sceneControllers.mapEditing;

import Database.NodeManager;
import Editor.NodeEditController;
import Entity.Node;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.HashSet;

public class NodeAdder {
    private NodeManager nodeManager;
    private int addNodeX, addNodeY;
    private HashSet<Integer> usedNumbers;

    private JFXTextField xPos, yPos, shortName, longName;
    private JFXComboBox nodeTypeCombo, buildingCombo;
    private ObservableList nodeTypeList = FXCollections.observableArrayList("HALL","REST","ELEV","LABS","EXIT","STAI","DEPT","CONF"),
    buildingList = FXCollections.observableArrayList("Shapiro", "Non-Shapiro");

    public NodeAdder(NodeManager n, JFXTextField xPos, JFXTextField yPos, JFXComboBox nodeTypeCombo, JFXComboBox buildingCombo,
                     JFXTextField shortName, JFXTextField longName) {
        this.nodeManager = n;
        this.xPos = xPos;
        this.yPos = yPos;
        this.nodeTypeCombo = nodeTypeCombo;
        this.buildingCombo = buildingCombo;
        this.shortName = shortName;
        this.longName = longName;

        this.nodeTypeCombo.setItems(nodeTypeList);
        this.buildingCombo.setItems(buildingList);
    }

    public void clickOnMap(MouseEvent m, GraphicsContext gc) {
        addNodeX = (int) m.getX();
        addNodeY = (int) m.getY();
        gc.setFill(Color.BLUE);
        gc.fillOval(addNodeX - 7, addNodeY - 7, 14, 14);
        gc.setFill(Color.YELLOW);
        gc.fillOval(addNodeX - 3, addNodeY - 3, 6, 6);
        xPos.setText("" + addNodeX);
        yPos.setText("" + addNodeY);
    }

    public void addNode(NodeEditController nodeEditController, String currentFloor) {
        if (buildingCombo.getSelectionModel().getSelectedItem() == null) return; //todo error
        if (nodeTypeCombo.getSelectionModel().getSelectedItem() == null) return; //todo error
        String building = buildingCombo.getSelectionModel().getSelectedItem().toString();
        String nodeType = nodeTypeCombo.getSelectionModel().getSelectedItem().toString();
        String nodeID = "G" + nodeType + getNodeNumber() + currentFloor;
        Node n = new Node(nodeID, addNodeX, addNodeY, currentFloor, building, nodeType, longName.getText(), shortName.getText());
        nodeEditController.addNode(n);
        System.out.println(building + " " + nodeType + " " + nodeID + " " + currentFloor + " " + longName + " " + shortName);
        reset();
    }

    private String getNodeNumber() {
        return "0"; //TODO !!
    }

    public void reset() {
        addNodeX = 0;
        addNodeY = 0;
        longName.clear();
        shortName.clear();
        nodeTypeCombo.setItems(null);
        nodeTypeCombo.setItems(nodeTypeList);
        buildingCombo.setItems(null);
        buildingCombo.setItems(buildingList);
    }
}
