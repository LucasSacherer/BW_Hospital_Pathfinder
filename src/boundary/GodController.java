package boundary;


import Entity.Edge;
import Entity.Node;
import boundary.sceneControllers.*;
import com.jfoenix.controls.JFXButton;
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

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class GodController {
    private final String mainLoc = "./fxml/main.fxml";
    private final String loginLoc = "/boundary/fxml/loginScene.fxml";
    private final String adminHubLoc = "./fxml/adminHub.fxml";
    private final String requestLoc = "./fxml/staffRequest.fxml";
    private final String adminLogLoc = "./fxml/adminLog.fxml";
    private final String adminRequestLoc = "./fxml/adminRequest.fxml";
    private final String adminEmployeeLoc = "./fxml/adminEmployee.fxml";
    private final String mapEditLoc = "./fxml/adminMap.fxml";
//
    SceneSwitcher sceneSwitcher = new SceneSwitcher();
//    /* Scene Controllers */
    MainSceneController mainSceneController = new MainSceneController();
    LoginController loginController = new LoginController();
    AdminEmployeeController adminEmployeeController = new AdminEmployeeController();
    AdminHubController adminHubController = new AdminHubController();
    AdminLogController adminLogController = new AdminLogController();
    AdminMapController adminMapController = new AdminMapController();
    StaffRequestController staffRequestController = new StaffRequestController();

    /* managers */
//    final private NodeManager nodeManager = new NodeManager();
//    final private EdgeManager edgeManager = new EdgeManager(nodeManager);
//    final private RequestManager requestManager = new RequestManager(nodeManager);
//    final private Astar aStar = new Astar(edgeManager);
//    final private FileSelector fileSelector = new FileSelector();

    /////////////////
    /* controllers */
    /////////////////
//    final private MapDisplayController mapDisplayController = new MapDisplayController();
//    final private ClickController clickController = new ClickController(nodeManager);
//    final private DirectoryController directoryController = new DirectoryController(nodeManager);
//    final private PathController pathController = new PathController(aStar);

    private Node loc1;
    private Node loc2;
    private Node currentLoc;
    private String currentFloor;
    private List<Node> currentPath;

    ///////////////////////
    /** FXML Attributes **/
    ///////////////////////

   /* Scene Panes */
    @FXML
    private Pane mainPane, loginPane, requestPane, adminHubPane, adminRequestPane, adminMapPane, adminEmployeePane, adminLogPane;

    @FXML
    private Pane mapPane;

    @FXML
    private Canvas canvas;

    @FXML
    private Label currentFloorNum;

    @FXML
    private TextField originField, destinationField;

    @FXML
    private ImageView imageView;

    @FXML
    GraphicsContext gc;

    @FXML
    private ListView elevatorDir, restroomDir, stairsDir, deptDir, labDir, infoDeskDir, conferenceDir, exitDir, shopsDir, nonMedical;

//
//    /** Organize Functions by Scene **/
//
//    @FXML
//    private void initialize(){
//        nodeManager.updateNodes();
//        edgeManager.updateEdges();
//
//        Image groundFloor = null;
//        try {
//            groundFloor = mapDisplayController.getMap("G");
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        imageView.setImage(groundFloor);
//        gc = canvas.getGraphicsContext2D();
//        currentFloor = "G";
//        currentFloorNum.setText(currentFloor);
//        initializeDirectory();
//        initializeDirectoryListeners();
//    }
//
//    private void initializeDirectory() {
//        elevatorDir.setItems(directoryController.getDirectory().get("Elevators"));
//        restroomDir.setItems(directoryController.getDirectory().get("Restrooms"));
//        stairsDir.setItems(directoryController.getDirectory().get("Stairs"));
//        labDir.setItems(directoryController.getDirectory().get("Departments"));
//        deptDir.setItems(directoryController.getDirectory().get("Labs"));
//        infoDeskDir.setItems(directoryController.getDirectory().get("Information Desks"));
//        conferenceDir.setItems(directoryController.getDirectory().get("Conference Rooms"));
//        exitDir.setItems(directoryController.getDirectory().get("Exits/Entrances"));
//        shopsDir.setItems(directoryController.getDirectory().get("Shops, Food, Phones"));
//        nonMedical.setItems(directoryController.getDirectory().get("Non-Medical Services"));
//    }
//
//    private void initializeDirectoryListeners(){
//        elevatorDir.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
//            currentLoc = (Node) elevatorDir.getItems().get(newValue.intValue());
//            clearCanvas();
//            drawPath();
//            drawCurrentNode();
//        });
//        restroomDir.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
//            currentLoc = (Node) restroomDir.getItems().get(newValue.intValue());
//            clearCanvas();
//            drawPath();
//            drawCurrentNode();
//        });
//        stairsDir.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
//            currentLoc = (Node) stairsDir.getItems().get(newValue.intValue());
//            clearCanvas();
//            drawPath();
//            drawCurrentNode();
//        });
//        labDir.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
//            currentLoc = (Node) labDir.getItems().get(newValue.intValue());
//            clearCanvas();
//            drawPath();
//            drawCurrentNode();
//        });
//        deptDir.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
//            currentLoc = (Node) deptDir.getItems().get(newValue.intValue());
//            clearCanvas();
//            drawPath();
//            drawCurrentNode();
//        });
//        infoDeskDir.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
//            currentLoc = (Node) infoDeskDir.getItems().get(newValue.intValue());
//            clearCanvas();
//            drawPath();
//            drawCurrentNode();
//        });
//        conferenceDir.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
//            currentLoc = (Node) conferenceDir.getItems().get(newValue.intValue());
//            clearCanvas();
//            drawPath();
//            drawCurrentNode();
//        });
//        exitDir.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
//            currentLoc = (Node) exitDir.getItems().get(newValue.intValue());
//            clearCanvas();
//            drawPath();
//            drawCurrentNode();
//        });
//        nonMedical.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
//            currentLoc = (Node) nonMedical.getItems().get(newValue.intValue());
//            clearCanvas();
//            drawPath();
//            drawCurrentNode();
//        });
//    }
//




//    ////////////////////////////////////////////////////////////
    /* Main scene */
    @FXML
    private void setLoc1(ActionEvent e) { mainSceneController.setLoc1(); }

    //sets loc2 to nearest node to click location
    @FXML
    private void setLoc2(ActionEvent e) { mainSceneController.setLoc2(); }

    // finds the path from loc1 to loc2
    @FXML
    private void findPath(ActionEvent e) { mainSceneController.findPath(); }

    //zooms in by 0.1 on click of zoom in button
    @FXML
    private void zoomInMap(MouseEvent e) { mainSceneController.zoomInMap(); }

    //zooms out by 0.1 on click of zoom out button
    @FXML
    private void zoomOutMap(MouseEvent e) { mainSceneController.zoomOutMap(); }

    private void snapToNode(MouseEvent m) { mainSceneController.snapToNode(); }

    private void drawPath() { mainSceneController.drawPath(); }

    private void drawCurrentNode(){ mainSceneController.drawCurrentNode(); }

    @FXML
    private void drawEdge(Edge edge){ mainSceneController.drawEdge(); }

    @FXML
    private void drawNode(Node n) { mainSceneController.drawNode(); }

    @FXML
    private void clearCanvas(){ mainSceneController.clearCanvas(); }

    @FXML
    private void bathroomClicked(ActionEvent e){ mainSceneController.bathroomClicked(); }

    @FXML
    private void infoClicked(ActionEvent e){ mainSceneController.infoClicked(); }

    @FXML
    private void elevatorClicked(ActionEvent e){ mainSceneController.elevatorClicked(); }

    @FXML
    private void floorDown(MouseEvent e) throws IOException, SQLException { mainSceneController.floorDown(); }

    @FXML
    private void floorUp(MouseEvent e) throws IOException, SQLException { mainSceneController.floorUp(); }

    @FXML
    private void clickOnMap(MouseEvent m) { mainSceneController.clickOnMap(); }

    ////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////

    /* Scene Switching */

    @FXML
    private void mainToLogin() throws IOException {
        sceneSwitcher.switchScene(this, mainPane, loginLoc);
    }

    @FXML
    private void requestToMain() throws IOException {
        sceneSwitcher.switchScene(this, requestPane, mainLoc);
    }

    /* Login Page */
    @FXML
    private void goToMainScreen() throws IOException {
        sceneSwitcher.switchScene(this, loginPane, mainLoc);
    }

    @FXML
    private void goToRequests() throws IOException {
        sceneSwitcher.switchScene(this, loginPane, requestLoc);
    }

    @FXML
    private void goToAdminHub() throws IOException {
        sceneSwitcher.switchScene(this, loginPane, adminHubLoc);
    }

    @FXML
    private void adminHubToMain() throws IOException {
        sceneSwitcher.switchScene(this, adminHubPane, mainLoc);
    }

    @FXML
    private void adminHubtoLog() throws IOException {
        sceneSwitcher.switchScene(this, adminHubPane, adminLogLoc);
    }

    @FXML
    private void adminHubtoRequest() throws IOException {
        sceneSwitcher.switchScene(this, adminHubPane, adminRequestLoc);
    }

    @FXML
    private void adminHubtoEmployee() throws IOException {
        sceneSwitcher.switchScene(this, adminHubPane, adminEmployeeLoc);
    }

    @FXML
    private void adminHubtoMap() throws IOException {
        sceneSwitcher.switchScene(this, adminHubPane, mapEditLoc);
    }

    @FXML
    private void requestToAdminHub() throws IOException {
        sceneSwitcher.switchScene(this, adminRequestPane, adminHubLoc);
    }

    @FXML
    private void mapToAdminHub() throws IOException {
        sceneSwitcher.switchScene(this, adminMapPane, adminHubLoc);
    }

    @FXML
    private void logToAdminHub() throws IOException {
        sceneSwitcher.switchScene(this, adminLogPane, adminHubLoc);
    }

    @FXML
    private void employeeToAdminHub() throws IOException {
        sceneSwitcher.switchScene(this, adminEmployeePane, adminHubLoc);
    }

}