package boundary.sceneControllers;


        import entity.Edge;
        import Entity.Node;
        import boundary.sceneControllers.*;
        import com.jfoenix.controls.JFXButton;
        import javafx.collections.FXCollections;
        import javafx.collections.ObservableList;
        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.fxml.FXMLLoader;
        import javafx.scene.Parent;
        import javafx.scene.Scene;
        import javafx.scene.canvas.Canvas;
        import javafx.scene.canvas.GraphicsContext;
        import javafx.scene.control.*;
        import javafx.scene.image.Image;
        import javafx.scene.image.ImageView;
        import javafx.scene.input.MouseEvent;
        import javafx.scene.layout.AnchorPane;
        import javafx.scene.layout.Pane;
        import javafx.scene.paint.Color;
        import javafx.stage.Stage;

        import javax.xml.soap.Text;
        import java.io.IOException;
        import java.sql.SQLException;
        import java.util.List;
        import java.util.Observable;

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
