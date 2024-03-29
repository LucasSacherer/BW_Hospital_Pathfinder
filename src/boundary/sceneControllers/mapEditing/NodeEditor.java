package boundary.sceneControllers.mapEditing;

import Editor.NodeEditController;
import Entity.Node;
import Entity.ErrorController;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class NodeEditor {
    private GraphicsContext gc;
    private Node current;
    private NodeEditController nodeEditController;
    private JFXTextField nodeID, xPos, yPos, shortName, longName, editNodeTypeField;
    private JFXComboBox nodeTypeCombo;
    private ObservableList nodeTypeList = FXCollections.observableArrayList(
            "HALL","REST","ELEV","LABS","EXIT","STAI","DEPT","CONF");
    private ErrorController errorController = new ErrorController();

    public NodeEditor(NodeEditController n, JFXTextField editNodeID, JFXTextField xPosEdit, JFXTextField yPosEdit,
                      JFXComboBox nodeTypeComboEdit, JFXTextField shortNameEdit, JFXTextField longNameEdit,
                      JFXTextField editNodeTypeField, GraphicsContext gc) {
        this.nodeEditController = n;
        this.nodeID = editNodeID;
        this.xPos = xPosEdit;
        this.yPos = yPosEdit;
        this.nodeTypeCombo = nodeTypeComboEdit;
        this.shortName = shortNameEdit;
        this.longName = longNameEdit;
        this.editNodeTypeField = editNodeTypeField;
        this.gc = gc;
        nodeTypeCombo.setItems(nodeTypeList);
    }

    public void clickOnMap(Node current) {
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
        editNodeTypeField.setText(current.getNodeType());
        nodeTypeCombo.setItems(null);
        nodeTypeCombo.setItems(nodeTypeList);
    }

    public void editNode() {
        //System.out.println(current);
        boolean success = true;
        try{
            nodeTypeCombo.getSelectionModel().getSelectedItem().equals(nodeTypeCombo.getSelectionModel().getSelectedItem());
            current.equals(current);
            if(xPos.getText().equals("")||yPos.getText().equals("")){
                throw new NullPointerException();
            }
        }
        catch(NullPointerException e){
            errorController.showError("Please complete all node information fields.");
            success = false;
        }
        if(success) {
            String nodeType = (nodeTypeCombo.getSelectionModel().getSelectedItem() == null) ? nodeType = current.getNodeType() :
                    nodeTypeCombo.getSelectionModel().getSelectedItem().toString();
            int x = Integer.parseInt(xPos.getText());
            int y = Integer.parseInt(yPos.getText());

            Node n = new Node(current.getNodeID(), x, y, current.getFloor(), current.getBuilding(), nodeType,
                    longName.getText(), shortName.getText());
            nodeEditController.editNode(n);
            reset();
        }
    }

    public void reset() {
        current = null;
        nodeTypeCombo.setItems(null);
        nodeTypeCombo.setItems(nodeTypeList);
        shortName.clear();
        longName.clear();
        nodeID.clear();
        editNodeTypeField.clear();
    }
}
