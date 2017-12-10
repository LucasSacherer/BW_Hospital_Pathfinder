package boundary.sceneControllers;

        import Editor.NodeEditController;
        import boundary.sceneControllers.mapEditing.KioskEditor;
        import com.jfoenix.controls.JFXTextField;
        import javafx.fxml.FXML;
        import javafx.scene.Node;
        import javafx.scene.canvas.GraphicsContext;

public class AdminSettingsPopUpController {
    private NodeEditController nodeEditController;
    protected GraphicsContext gc;

    @FXML
    private JFXTextField distanceScale;

    @FXML
    private void setDistanceScale(){
        nodeEditController.setScale(distanceScale);
    }

    public AdminSettingsPopUpController(NodeEditController nodeEditController ) {
        this.nodeEditController = nodeEditController;
    }


}
