package boundary;


import Admin.UserLoginController;
import Database.UserManager;
import Entity.Edge;
import Entity.Node;
import Database.EdgeManager;
import Database.NodeManager;
import MapNavigation.MapNavigationFacade;
import Pathfinding.Astar;
import Pathfinding.PathFindingFacade;
import boundary.sceneControllers.*;
import com.jfoenix.controls.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

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

    /* managers */
    final private NodeManager nodeManager = new NodeManager();
    final private EdgeManager edgeManager = new EdgeManager(nodeManager);
    final private MapNavigationFacade mapNavigationFacade = new MapNavigationFacade();
    final private PathFindingFacade pathFindingFacade = new PathFindingFacade();
    final private Astar astar = new Astar(edgeManager);
    final private UserLoginController userLoginController = new UserLoginController(new UserManager());

    ///////////////////////
    /** FXML Attributes **/
    ///////////////////////

   /* Scene Panes */
    @FXML
    private Pane mapPane, mainPane, loginPane, requestPane, adminHubPane, adminRequestPane, adminMapPane, adminEmployeePane, adminLogPane;

    @FXML
    private StackPane menuARStackPane;

    @FXML
    private Canvas canvas;

    @FXML
    private Label currentFloorNum;

    @FXML
    private TextField originField, destinationField, staffLoginText, staffPasswordText;

    @FXML
    private ImageView imageView;

    @FXML
    private ListView elevatorDir, restroomDir, stairsDir, deptDir, labDir, infoDeskDir, conferenceDir, exitDir, shopsDir, nonMedical;

    @FXML
    private JFXTextField spillsARNode,spillsARTimestamp, spillsARDescription, foodARNode, foodARTimestamp, foodARDescription, interpreterARNode,interpreterARTimestamp, interpreterARDescription, menuARName,menuARDescription,menuARCost;

    @FXML
    private JFXComboBox nodetypeCombo, buildingCombo, spillsARType, spillsARName, foodARType, foodARName, interpreterARType, interpreterARName;

    @FXML
    private JFXToggleButton spillsARDisplayToggle, foodARDisplayToggle, interpreterARDisplayToggle;

    @FXML
    private JFXButton spillsARAdd, spillsARCancel, spillsAREdit, spillsARDelete,spillsARDeleteAll,foodARAdd, foodARCancel, foodAREdit, foodARDelete,foodARDeleteAll,interpreterARAdd, interpreterARCancel, interpreterAREdit, interpreterARDelete,interpreterARDeleteAll, menuARAdd, menuARCancel, menuAREdit, menuARDelete,menuARDeleteAll;

    @FXML
    private JFXListView spillsARList, foodARList, interpreterARList, menuARList;

    ObservableList<String> nodeTypeList, buildingList;

    SceneSwitcher sceneSwitcher = new SceneSwitcher();
    /* Scene Controllers */
    MainSceneController mainSceneController;
    LoginController loginController;
    AdminHubController adminHubController = new AdminHubController();
    AdminEmployeeController adminEmployeeController = new AdminEmployeeController();
    AdminLogController adminLogController = new AdminLogController();
    AdminMapController adminMapController = new AdminMapController();
    StaffRequestController staffRequestController = new StaffRequestController();

//    /** Organize Functions by Scene **/
//
    @FXML
    private void initialize(){
        pathFindingFacade.setPathfinder(astar);
        initializeMainScene(imageView, mapPane, canvas, mapNavigationFacade, pathFindingFacade);
        initializeLoginScene(staffPasswordText, staffLoginText);
        initializeMapAdminScene();

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
//        currentFloor = "G";
//        currentFloorNum.setText(currentFloor);
//        initializeDirectory();
//        initializeDirectoryListeners();
    }

    private void initializeMapAdminScene() {
        nodeTypeList = FXCollections.observableArrayList("HALL","REST","ELEV","LABS","EXIT","STAI","DEPT","CONF");
        buildingList = FXCollections.observableArrayList("Shapiro", "Non-Shapiro");
    }

    private void initializeLoginScene(TextField staffPasswordText, TextField staffLoginText) {
        loginController = new LoginController();
    }

    private void initializeMainScene(ImageView imageView, Pane mapPane, Canvas canvas,
                                     MapNavigationFacade mapNavigationFacade, PathFindingFacade pathFindingFacade) {
       mainSceneController = new MainSceneController(imageView, mapPane, canvas, mapNavigationFacade, pathFindingFacade);
    }
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


    ////////////////
    /* Main scene */
    ////////////////
    @FXML
    private void setLoc1(ActionEvent e) { mainSceneController.setLoc1(originField); }

    //sets loc2 to nearest node to click location
    @FXML
    private void setLoc2(ActionEvent e) { mainSceneController.setLoc2(destinationField); }

    // finds the path from loc1 to loc2
    @FXML
    private void findPath(ActionEvent e) { mainSceneController.findPath(); }

    //zooms in by 0.1 on click of zoom in button
    @FXML
    private void zoomInMap(MouseEvent e) { mainSceneController.zoomInMap(); }

    //zooms out by 0.1 on click of zoom out button
    @FXML
    private void zoomOutMap(MouseEvent e) { mainSceneController.zoomOutMap(); }

    @FXML
    private void snapToNode(MouseEvent m) { mainSceneController.snapToNode(m); }

    private void drawPath() { mainSceneController.drawPath(); }

    private void drawCurrentNode(){ mainSceneController.drawCurrentNode(); }

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
    private void clickOnMap(MouseEvent m) { mainSceneController.clickOnMap(m); }

    //////////////////
    /* Login Scene */
    /////////////////

    @FXML
    private void switchToAdmin() throws IOException {
        loginController.switchToAdmin();
    }

    @FXML
    private void switchToStaff() throws IOException {
        loginController.switchToStaff();
    }

    ///////////////////
    /* Request Scene */
    ///////////////////



    ///////////////
    /* Admin Hub */
    ///////////////




    ////////////////////
    /* Employee Admin */
    ////////////////////




    ///////////////////
    /* Request Admin */
    ///////////////////





    ///////////////
    /* Map Admin */
    ///////////////

    @FXML
    private void drawEdge(Edge edge){ adminMapController.drawEdge(); }

//    @FXML
//    private void drawNode(Node n) { mainSceneController.drawNode(); }

    ////////////////
    /* Admin Logs */
    ////////////////





    /////////////////////
    /* Scene Switching */
    /////////////////////
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
    private void goToMainScene() throws IOException {
        sceneSwitcher.switchScene(this, loginPane, mainLoc);
    }

    @FXML
    private void goToRequests() throws IOException {
       if (userLoginController.authenticateStaff(staffLoginText.getText(), staffPasswordText.getText())){
            sceneSwitcher.switchScene(this, loginPane, requestLoc);
        }
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
        nodetypeCombo.setItems(nodeTypeList);
        buildingCombo.setItems(buildingList);
    }

    @FXML
    private void requestToAdminHub() throws IOException { //TODO this one is broken
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