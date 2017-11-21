package boundary;


import Admin.UserLoginController;
import Database.UserManager;
import Database.EdgeManager;
import Database.NodeManager;
import MapNavigation.*;
import Pathfinding.Astar;
import Pathfinding.PathFindingFacade;
import boundary.sceneControllers.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.sql.SQLException;

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
    final private ClickController clickController = new ClickController(nodeManager);
    final private NearestPOIController nearestPOIController = new NearestPOIController(nodeManager);
    final private MapDisplayController mapDisplayController = new MapDisplayController();
    final private DirectoryController directoryController = new DirectoryController(nodeManager);
    final private MapNavigationFacade mapNavigationFacade = new MapNavigationFacade(
            clickController, nearestPOIController, mapDisplayController, directoryController);
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
    private Canvas canvas, mapEditCanvas;

    @FXML
    private Label currentFloorNum;

    @FXML
    private TextField originField, destinationField;

    @FXML
    private ImageView imageView, mapEditImageView;

    @FXML
    private ListView elevatorDir, restroomDir, stairsDir, deptDir, labDir, infoDeskDir, conferenceDir, exitDir, shopsDir, nonMedical;


    //// MAP ADMIN FXML
    @FXML
    private Tab addNode, editNode, removeNode, nodesTab, edgesTab;

    @FXML
    private Label mapEditText, nodeLocation1, nodeLocation2, nodeLocation3;

    @FXML
    private JFXComboBox nodetypeCombo, buildingCombo, nodetypeComboEdit;

    @FXML
    private JFXTextField xPosAdd, yPossAdd, xPosEdit, yPossEdit, xPosRemove, yPossRemove,
            shortNameAdd, shortNameEdit, shortNameRemove,
            longNameAdd, longNameEdit, longNameRemove;
    @FXML
    private JFXButton addNodeButton, resetNodeButtonAdd,
            editNodeButton, resetNodeButtonEdit, resetNodeButtonRemove;
    @FXML
    private JFXListView nodesListView;


    //// Requests ADMIN FXML
    @FXML
    private JFXTextField spillsARNode,spillsARTimestamp, spillsARDescription,
            foodARNode, foodARTimestamp, foodARDescription,
            interpreterARNode,interpreterARTimestamp, interpreterARDescription,
            menuARName,menuARDescription,menuARCost;

    @FXML
    private JFXComboBox spillsARType, spillsARName,
            foodARType, foodARName,
            interpreterARType, interpreterARName;

    @FXML
    private JFXToggleButton spillsARDisplayToggle, foodARDisplayToggle, interpreterARDisplayToggle;

    @FXML
    private JFXButton spillsARAdd, spillsARCancel, spillsAREdit, spillsARDelete,spillsARDeleteAll,
            foodARAdd, foodARCancel, foodAREdit, foodARDelete,foodARDeleteAll,
            interpreterARAdd, interpreterARCancel, interpreterAREdit, interpreterARDelete,interpreterARDeleteAll,
            menuARAdd, menuARCancel, menuAREdit, menuARDelete,menuARDeleteAll;

    @FXML
    private JFXListView spillsARList, foodARList, interpreterARList, menuARList;

    //Login Screen
    @FXML
    private JFXButton staffLogin, staffCancel, adminLogin, adminCancel;

    @FXML
    private JFXTextField staffLoginText, adminLoginText;

    @FXML
    private JFXPasswordField staffPasswordText, adminPasswordText;

    ObservableList<String> nodeTypeList, buildingList;

    SceneSwitcher sceneSwitcher = new SceneSwitcher();

    /* Scene Controllers */
    MainSceneController mainSceneController;
    LoginController loginController;
    AdminHubController adminHubController = new AdminHubController();
    AdminEmployeeController adminEmployeeController = new AdminEmployeeController();
    AdminLogController adminLogController = new AdminLogController();
    AdminMapController adminMapController = new AdminMapController(mapEditImageView, adminMapPane, mapEditCanvas);
    StaffRequestController staffRequestController = new StaffRequestController();
    AdminRequestController adminRequestController = new AdminRequestController();


//    /** Organize Functions by Scene **/

    @FXML
    private void initialize(){
        nodeManager.updateNodes();
        edgeManager.updateEdges();
        pathFindingFacade.setPathfinder(astar);
        initializeLoginScene(staffPasswordText, staffLoginText);
        initializeMapAdminScene();
        initializeAdminRequestScene();
        Image groundFloor = null;
        groundFloor = mapNavigationFacade.getFloorMap("G");
        imageView.setImage(groundFloor);
        initializeDirectory();
        initializeMainScene(imageView, mapPane, canvas, mapNavigationFacade, pathFindingFacade, currentFloorNum);
    }

    private void initializeMapAdminScene() {
        nodeTypeList = FXCollections.observableArrayList("HALL","REST","ELEV","LABS","EXIT","STAI","DEPT","CONF");
        buildingList = FXCollections.observableArrayList("Shapiro", "Non-Shapiro");
    }

    private void initializeLoginScene(TextField staffPasswordText, TextField staffLoginText) {
        loginController = new LoginController();
    }

    private void initializeMainScene(
            ImageView imageView, Pane mapPane, Canvas canvas, MapNavigationFacade mapNavigationFacade,
            PathFindingFacade pathFindingFacade, Label currentFloorNum) {
        mainSceneController = new MainSceneController(
                imageView, mapPane, canvas, mapNavigationFacade, pathFindingFacade, currentFloorNum,
                elevatorDir, restroomDir, stairsDir, deptDir, labDir, infoDeskDir, conferenceDir, exitDir, shopsDir, nonMedical);
    }

    private void initializeDirectory() {
        elevatorDir.setItems(mapNavigationFacade.getDirectory().get("Elevators"));
        restroomDir.setItems(mapNavigationFacade.getDirectory().get("Restrooms"));
        stairsDir.setItems(mapNavigationFacade.getDirectory().get("Stairs"));
        labDir.setItems(mapNavigationFacade.getDirectory().get("Departments"));
        deptDir.setItems(mapNavigationFacade.getDirectory().get("Labs"));
        infoDeskDir.setItems(mapNavigationFacade.getDirectory().get("Information Desks"));
        conferenceDir.setItems(mapNavigationFacade.getDirectory().get("Conference Rooms"));
        exitDir.setItems(mapNavigationFacade.getDirectory().get("Exits/Entrances"));
        shopsDir.setItems(mapNavigationFacade.getDirectory().get("Shops, Food, Phones"));
        nonMedical.setItems(mapNavigationFacade.getDirectory().get("Non-Medical Services"));
    }

    private void initializeAdminRequestScene(){
        adminRequestController = new AdminRequestController(
        );
    }


    ////////////////
    /* Main scene */
    ////////////////
    @FXML
    private void setLoc1(ActionEvent e) { mainSceneController.setOrigin(originField); }

    //sets loc2 to nearest node to click location
    @FXML
    private void setLoc2(ActionEvent e) { mainSceneController.setDestination(destinationField); }

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

    //Spills
    @FXML
    private void displaySpillsOnMap(MouseEvent e) throws IOException {
        adminRequestController.displaySpillsOnMap();
    }
    @FXML
    private void displaySpillsOnMap() throws IOException {
        adminRequestController.displaySpillsOnMap();
    }



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



//    @FXML
//    private void drawEdge(Edge edge){ adminMapController.drawEdge(); }

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
        if (userLoginController.authenticateAdmin(adminLoginText.getText(), adminPasswordText.getText())) {
            sceneSwitcher.switchScene(this, loginPane, adminHubLoc);
        }
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
        adminMapController.initializeScene();
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