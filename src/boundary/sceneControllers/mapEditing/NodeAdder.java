package boundary.sceneControllers.mapEditing;

import Database.NodeManager;
import Editor.NodeEditController;
import Entity.ErrorController;
import Entity.Node;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class NodeAdder {
    private GraphicsContext gc;
    private NodeManager nodeManager;
    private int addNodeX, addNodeY;
    private ErrorController errorController = new ErrorController();
    private JFXTextField xPos, yPos, shortName, longName;
    private JFXComboBox nodeTypeCombo, buildingCombo;
    private ObservableList nodeTypeList = FXCollections.observableArrayList("HALL","REST","ELEV","LABS","EXIT","STAI","DEPT","CONF"),
    buildingList = FXCollections.observableArrayList("Shapiro", "BTM", "Tower", "15 Francis", "45 Francis");

    public NodeAdder(NodeManager n, JFXTextField xPos, JFXTextField yPos, JFXComboBox nodeTypeCombo,
                     JFXComboBox buildingCombo, JFXTextField shortName, JFXTextField longName, GraphicsContext gc) {
        this.nodeManager = n;
        this.xPos = xPos;
        this.yPos = yPos;
        this.nodeTypeCombo = nodeTypeCombo;
        this.buildingCombo = buildingCombo;
        this.shortName = shortName;
        this.longName = longName;
        this.gc = gc;

        this.nodeTypeCombo.setItems(nodeTypeList);
        this.buildingCombo.setItems(buildingList);
    }

    public void clickOnMap(MouseEvent m) {
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
        boolean success = true;
        try {
            nodeTypeCombo.getSelectionModel().getSelectedItem().toString();
            buildingCombo.getSelectionModel().getSelectedItem().toString();

        }
        catch(NullPointerException e){
            errorController.showError("Please fill out the node information");
            success = false;
        }
        if(success) {
            String building = buildingCombo.getSelectionModel().getSelectedItem().toString();
            String nodeType = nodeTypeCombo.getSelectionModel().getSelectedItem().toString();
            String nodeID = getUniqueID(nodeType, currentFloor);
            if (nodeID == null) return; //todo error
            Node n = new Node(nodeID, addNodeX, addNodeY, currentFloor, building, nodeType, longName.getText(), shortName.getText());
            nodeEditController.addNode(n);
            System.out.println(building + " " + nodeType + " " + nodeID + " " + currentFloor + " " + longName + " " + shortName);
            reset();
        }
    }

    private String getUniqueID(String nodeType, String currentFloor) {
        nodeManager.update();
        String firstNum, potential;
        for (int i = 0; i < 999; i++) {
            StringBuilder ID = new StringBuilder();
            if (i < 10) firstNum = "00" + i;
            else if (i < 100) firstNum = "0" + i;
            else firstNum = "" + i;
            ID.append("G");
            ID.append(nodeType);
            ID.append(firstNum);
            ID.append(getFloorString(currentFloor));
            potential = ID.toString();
            if (nodeManager.getNode(potential) == null) return potential;
        }
        return null;
    }

    private String getFloorString(String currentFloor) {
        switch(currentFloor) {
            case "2":
                return "02";
            case "1":
                return "01";
            case "G":
                return "0G";
        }
        return currentFloor;
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
