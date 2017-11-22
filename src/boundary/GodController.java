package boundary;


import Admin.UserLoginController;
import Database.*;
import MapNavigation.*;
import Pathfinding.Astar;
import Pathfinding.PathFindingFacade;
import Request.RequestCleanupController;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.sql.SQLException;

public class GodController {

    /* managers */
    final private NodeManager nodeManager = new NodeManager();
    final private EdgeManager edgeManager = new EdgeManager(nodeManager);
    final private SettingsManager settingsManager = new SettingsManager();
    final private ClickController clickController = new ClickController(nodeManager);
    final private NearestPOIController nearestPOIController = new NearestPOIController(nodeManager);
    final private MapDisplayController mapDisplayController = new MapDisplayController();
    final private DirectoryController directoryController = new DirectoryController(nodeManager,settingsManager);
    final private MapNavigationFacade mapNavigationFacade = new MapNavigationFacade(
            clickController, nearestPOIController, mapDisplayController, directoryController);
    final private PathFindingFacade pathFindingFacade = new PathFindingFacade();
    final private Astar astar = new Astar(edgeManager);
    final private UserLoginController userLoginController = new UserLoginController(new UserManager());
    final private UserManager userManager = new UserManager();
    final private RequestCleanupController requestCleanupController = new RequestCleanupController(new CleanUpManager(nodeManager, userManager));

    ///////////////////////
    /** FXML Attributes **/
    ///////////////////////

   /* Scene Panes */
    @FXML
    private Pane requestMapPane, mapPane, mainPane, loginPane, requestPane, adminHubPane, adminRequestPane, adminMapPane, adminEmployeePane, adminLogPane;

    @FXML
    private StackPane menuARStackPane;

    @FXML
    private Canvas canvas, mapEditCanvas, requestCanvas;

    @FXML
    private TextField originField, destinationField; //TODO change to JFXTextField and in scenebuilder

    @FXML
    private ImageView imageView, mapEditImageView, requestImageView;

    @FXML
    private ListView elevatorDir, restroomDir, stairsDir, deptDir, labDir, infoDeskDir, conferenceDir, exitDir, shopsDir, nonMedical;

    //// MAP ADMIN FXML
    @FXML
    private Tab addNode, editNode, removeNode, nodesTab, edgesTab, setKioskTab, addEdge, removeEdge;

    @FXML
    private Label mapEditText, nodeLocation1, nodeLocation2, nodeLocation3, currentFloorNum, currentFloorNumRequest;

    @FXML
    private JFXComboBox nodetypeCombo, buildingCombo, nodetypeComboEdit;

    @FXML
    private JFXTextField xPosAddNode, yPosAddNode, xPosEdit, yPosEdit, xPosRemoveNode, yPosRemoveNode,
            xPosAddEdge, yPosAddEdge, xPosRemoveEdge, yPosRemoveEdge,
            setKioskX, setKioskY,
            shortNameAdd, shortNameEdit,
            longNameAdd, longNameEdit, requestName, requestDescription,
            edgeXStartAdd,edgeYStartAdd,edgeXEndAdd,edgeYEndAdd,
            edgeXStartRemove,edgeYStartRemove,edgeXEndRemove,edgeYEndRemove;
    @FXML
    private JFXButton addNodeButton, resetNodeButtonAdd,
            editNodeButton, resetNodeButtonEdit, resetNodeButtonRemove,
            removeEdgeButton,resetEdgeButtonRemove,addEdgeButton,resetEdgeButtonAdd;
    @FXML
    private JFXListView nodesListView, allStaffRequests;


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

    /* Scene Commandments */
    MainSceneController mainSceneController;
    AdminEmployeeController adminEmployeeController;
    AdminLogController adminLogController = new AdminLogController();
    AdminMapController adminMapController = new AdminMapController(mapEditImageView, adminMapPane, mapEditCanvas);
    StaffRequestController staffRequestController;
    AdminRequestController adminRequestController = new AdminRequestController();


    /** Organize Functions by Scene **/

    @FXML
    private void initialize(){
        nodeManager.updateNodes();
        edgeManager.updateEdges();
        pathFindingFacade.setPathfinder(astar);
        initializeMainScene();
        initializeRequestScene();
        initializeMapAdminScene();
        initializeAdminRequestScene();

    }

//    imageView, mapPane, canvas, mapNavigationFacade, pathFindingFacade, currentFloorNum
    private void initializeMainScene() {
        mainSceneController = new MainSceneController(imageView, mapPane, canvas,
                mapNavigationFacade, pathFindingFacade, currentFloorNum, elevatorDir,
                restroomDir, stairsDir, deptDir, labDir, infoDeskDir, conferenceDir, exitDir, shopsDir, nonMedical);
        mainSceneController.initializeScene();
    }

    private void initializeRequestScene() {
        staffRequestController = new StaffRequestController(requestImageView, requestMapPane, requestCanvas,
                mapNavigationFacade, pathFindingFacade, currentFloorNumRequest, requestCleanupController, allStaffRequests);
    }

    private void initializeMapAdminScene() {
        nodeTypeList = FXCollections.observableArrayList("HALL","REST","ELEV","LABS","EXIT","STAI","DEPT","CONF");
        buildingList = FXCollections.observableArrayList("Shapiro", "Non-Shapiro");
    }

    private void initializeAdminRequestScene(){ adminRequestController = new AdminRequestController(); }

    ////////////////
    /* Main scene */
    ////////////////
    @FXML
    private void setLoc1(ActionEvent e) { mainSceneController.setOrigin(originField); }

    //sets loc2 to nearest node to click location
    @FXML
    private void setLoc2(ActionEvent e) { mainSceneController.setDestination(); }

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

    @FXML
    private void navigateToHere() {mainSceneController.navigateToHere();}

    @FXML
    private void setAsOrigin() {mainSceneController.setAsOrigin();}


    ///////////////////
    /* Request Scene */
    ///////////////////


    @FXML
    private void navigateToRequest() {
        staffRequestController.findPath();
    }

    @FXML
    private void addStaffRequest() {
        staffRequestController.addRequest(requestName, requestDescription);
    }

    @FXML
    private void completeStaffRequest() {
        staffRequestController.completeRequest();
    }

    @FXML
    private void editStaffRequest() {
        staffRequestController.editRequest();
    }

    @FXML
    private void deleteStaffRequest() {
        staffRequestController.deleteRequest();
    }

    @FXML
    private void zoomInRequestMap() {
        staffRequestController.zoomInMap();
    }

    @FXML
    private void zoomOutRequestMap() {
        staffRequestController.zoomOutMap();
    }

    @FXML
    private void floorDownRequest() throws IOException, SQLException { staffRequestController.floorDown(); }

    @FXML
    private void floorUpRequest() throws IOException, SQLException { staffRequestController.floorUp(); }

    @FXML
    private void clickOnRequestMap(MouseEvent m) {
        staffRequestController.clickOnMap(m);
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

    //Spills
    @FXML
    private void displayARSpillsOnMap() throws IOException { adminRequestController.displayARSpillsOnMap();
    }
    @FXML
    private void displayARSpillsType() throws IOException {
        adminRequestController.displayARSpillsType();
    }
    @FXML
    private void displayARSpillsName() throws IOException {
        adminRequestController.displayARSpillsName();
    }
    @FXML
    private void displayARSpillsNode() throws IOException {
        adminRequestController.displayARSpillsNode();
    }
    @FXML
    private void displayARSpillsTimestamp() throws IOException {
        adminRequestController.displayARSpillsTimestamp();
    }
    @FXML
    private void displayARSpillsDescription() throws IOException {
        adminRequestController.displayARSpillsDescription();
    }
    @FXML
    private void addARSpills() throws IOException {
        adminRequestController.addARSpills();
    }
    @FXML
    private void cancelARSpills() throws IOException {
        adminRequestController.cancelARSpills();
    }
    @FXML
    private void editARSpills() throws IOException {
        adminRequestController.editARSpills();
    }
    @FXML
    private void deleteARSpills() throws IOException {
        adminRequestController.deleteARSpills();
    }
    @FXML
    private void deleteAllARSpills() throws IOException {
        adminRequestController.deleteAllARSpills();
    }

    //Food
    @FXML
    private void displayARFoodOnMap(MouseEvent e) throws IOException {
        adminRequestController.displayARFoodOnMap();
    }
    @FXML
    private void displayARFoodType(MouseEvent e) throws IOException {
        adminRequestController.displayARFoodType();
    }
    @FXML
    private void displayARFoodName() throws IOException {
        adminRequestController.displayARFoodName();
    }
    @FXML
    private void displayARFoodNode() throws IOException {
        adminRequestController.displayARFoodNode();
    }
    @FXML
    private void displayARFoodTimestamp() throws IOException {
        adminRequestController.displayARFoodTimestamp();
    }
    @FXML
    private void displayARMenuFoodTimestamp() throws IOException {
        adminRequestController.displayARMenuFoodTimestamp();
    }
    @FXML
    private void displayARFoodDescription() throws IOException {
        adminRequestController.displayARFoodDescription();
    }
    @FXML
    private void addARFood() throws IOException {
        adminRequestController.addARFood();
    }
    @FXML
    private void cancelARFood() throws IOException {
        adminRequestController.cancelARFood();
    }
    @FXML
    private void editARFood() throws IOException {
        adminRequestController.editARFood();
    }
    @FXML
    private void deleteARFood() throws IOException {
        adminRequestController.deleteARFood();
    }
    @FXML
    private void deleteAllARFood() throws IOException {
        adminRequestController.deleteAllARFood();
    }
    @FXML
    public void editARMenuFoodPopUp() throws  IOException {
        adminRequestController.editARMenuFoodPopUp();
    }
    @FXML
    private void displayARMenuFoodName() throws IOException {
        adminRequestController.displayARMenuFoodName();
    }
    @FXML
    private void displayARMenuFoodNode() throws IOException {
        //TODO
    }
    @FXML
    private void displayARMenuFoodDescription() throws IOException {
        adminRequestController.displayARMenuFoodDescription();
    }
    @FXML
    private void displayARMenuFoodCost() throws IOException {
        adminRequestController.displayARMenuFoodCost();
    }
    @FXML
    private void addARMenuFood() throws IOException {
        adminRequestController.addARMenuFood();
    }
    @FXML
    private void cancelARMenuFood() throws IOException {
        adminRequestController.cancelARMenuFood();
    }
    @FXML
    private void editARMenuFood() throws IOException {
        adminRequestController.editARMenuFood();
    }
    @FXML
    private void deleteARMenuFood() throws IOException {
        adminRequestController.deleteARMenuFood();
    }
    @FXML
    private void deleteAllARMenuFood() throws IOException {
        adminRequestController.deleteAllARMenuFood();
    }

    //Interpreter

    @FXML
    private void displayARInterpreterOnMap() throws IOException {
        adminRequestController.displayARInterpreterOnMap();
    }
    @FXML
    private void displayARInterpreterType() throws IOException {
        adminRequestController.displayARInterpreterType();
    }
    @FXML
    private void displayARInterpreterName() throws IOException {
        adminRequestController.displayARInterpreterName();
    }
    @FXML
    private void displayARInterpreterNode() throws IOException {
        adminRequestController.displayARInterpreterNode();
    }
    @FXML
    private void displayARInterpreterTimestamp() throws IOException {
        adminRequestController.displayARInterpreterTimestamp();
    }
    @FXML
    private void displayARInterpreterDescription() throws IOException {
        adminRequestController.displayARInterpreterDescription();
    }
    @FXML
    private void addARInterpreter() throws IOException {
        adminRequestController.addARInterpreter();
    }
    @FXML
    private void cancelARInterpreter() throws IOException {
        adminRequestController.cancelARInterpreter();
    }
    @FXML
    private void editARInterpreter() throws IOException {
        adminRequestController.editARInterpreter();
    }
    @FXML
    private void deleteARInterpreter() throws IOException {
        adminRequestController.deleteARInterpreter();
    }
    @FXML
    private void deleteAllARInterpreter() throws IOException {
        adminRequestController.deleteAllARInterpreter();
    }


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
        sceneSwitcher.toLogin(this, mainPane);
    }

    @FXML
    private void requestToMain() throws IOException {
        sceneSwitcher.toMain(this, requestPane);
    }

    /* Login Page */
    @FXML
    private void goToMainScene() throws IOException {
        sceneSwitcher.toMain(this, loginPane);
    }

    @FXML
    private void goToRequests() throws IOException {
       if (userLoginController.authenticateStaff(staffLoginText.getText(), staffPasswordText.getText())){
            sceneSwitcher.toStaffRequests(this, loginPane);
            staffRequestController.initializeScene();
        }
    }

    @FXML
    private void goToAdminHub() throws IOException {
        if (userLoginController.authenticateAdmin(adminLoginText.getText(), adminPasswordText.getText())) {
            sceneSwitcher.toAdminHub(this, loginPane);
        }
    }

    @FXML
    private void adminHubToMain() throws IOException {
        sceneSwitcher.toMain(this, adminHubPane);
    }

    @FXML
    private void adminHubtoLog() throws IOException {
        sceneSwitcher.toAdminLog(this, adminHubPane);
    }

    @FXML
    private void adminHubtoRequest() throws IOException {
        sceneSwitcher.toAdminRequests(this, adminHubPane);
    }

    @FXML
    private void adminHubtoEmployee() throws IOException {
        sceneSwitcher.toAdminEmployee(this, adminHubPane);
    }

    @FXML
    private void adminHubtoMap() throws IOException {
        sceneSwitcher.toAdminMap(this, adminHubPane);
        nodetypeCombo.setItems(nodeTypeList);
        buildingCombo.setItems(buildingList);
        adminMapController.initializeScene();
    }

    @FXML
    private void requestToAdminHub() throws IOException { sceneSwitcher.toAdminHub(this, adminRequestPane); }

    @FXML
    private void mapToAdminHub() throws IOException {
        sceneSwitcher.toAdminHub(this, adminMapPane);
    }

    @FXML
    private void logToAdminHub() throws IOException {
        sceneSwitcher.toAdminHub(this, adminLogPane);
    }

    @FXML
    private void employeeToAdminHub() throws IOException {
        sceneSwitcher.toAdminHub(this, adminEmployeePane);
    }
}