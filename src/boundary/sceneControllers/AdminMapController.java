package boundary.sceneControllers;


        import javafx.collections.FXCollections;
        import javafx.collections.ObservableList;
        import javafx.fxml.FXML;
        import javafx.scene.control.*;

public class AdminMapController {

    ObservableList<String> nodeTypeList = FXCollections
            .observableArrayList("HALL","REST","ELEV","LABS","EXIT","STAI","DEPT","CONF");
    ObservableList<String> buildingList = FXCollections
            .observableArrayList("Shapiro", "Non-Shapiro");

    @FXML
    private Tab nodesTab, edgesTab, addNode, editNode, removeNode, addEdge, removeEdge;

    @FXML
    private TextField xNode, yNode, nodeShortName,nodeLongName ;

    @FXML
    private ComboBox nodetypeCombo, buildingCombo;

    @FXML
    private Button addNodeButton;

    @FXML
    private void initiliaze(){

        nodetypeCombo.setItems(nodeTypeList);

        buildingCombo.setItems(buildingList);

    }


}
