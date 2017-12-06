package boundary;


import Admin.UserLoginController;
import Database.*;
import DatabaseSetup.DatabaseGargoyle;
import Editor.EdgeEditController;
import Editor.NodeEditController;
import Entity.AdminLog;
import Entity.ErrorController;
import Entity.Node;
import Entity.User;
import MapNavigation.*;
import Pathfinding.*;
import Request.GenericRequestController;
import Request.RequestCleanupController;
import Request.RequestFoodController;
import Request.RequestInterpreterController;
import boundary.sceneControllers.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class GodController {
    /* Database Gargoyle */
    final private DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();

    /* managers */
    final private NodeManager nodeManager = new NodeManager(databaseGargoyle);
    final private EdgeManager edgeManager = new EdgeManager(databaseGargoyle, nodeManager);
    final private UserManager userManager = new UserManager(databaseGargoyle);
    final private CleanUpManager cleanupManager = new CleanUpManager(databaseGargoyle, nodeManager, userManager);
    final private InterpreterManager interpreterManager = new InterpreterManager(databaseGargoyle, nodeManager, userManager);
    final private FoodManager foodManager = new FoodManager(databaseGargoyle, nodeManager, userManager);
    final private AdminLogManager adminLogManager = new AdminLogManager(databaseGargoyle, userManager);

    /* Controllers */
    final private GenericRequestController genericRequestController = new GenericRequestController(cleanupManager, foodManager, interpreterManager);
    final private NodeEditController nodeEditController = new NodeEditController(nodeManager, edgeManager, genericRequestController);
    final private EdgeEditController edgeEditController = new EdgeEditController(edgeManager);
    final private ClickController clickController = new ClickController(nodeManager);
    final private NearestPOIController nearestPOIController = new NearestPOIController(nodeManager);
    final private MapDisplayController mapDisplayController = new MapDisplayController();
    final private DirectoryController directoryController = new DirectoryController(nodeManager);
    final private UserLoginController userLoginController = new UserLoginController(userManager);
    final private RequestCleanupController requestCleanupController = new RequestCleanupController(cleanupManager);
    final private RequestInterpreterController requestInterpreterController = new RequestInterpreterController(interpreterManager);
    final private RequestFoodController requestFoodController = new RequestFoodController(foodManager);
    final private ErrorController errorController = new ErrorController();

    /* Facades */
    final private MapNavigationFacade mapNavigationFacade = new MapNavigationFacade(
            clickController, nearestPOIController, mapDisplayController, directoryController);
    final private PathFindingFacade pathFindingFacade = new PathFindingFacade();

    /* Entities */
    final private Astar astar = new Astar(edgeManager);
    final private BeamSearch beam = new BeamSearch(edgeManager);
    final private BreadthSearch breadth = new BreadthSearch(edgeManager);
    final private DepthSearch depth = new DepthSearch(edgeManager);

    /* Current user that is logged in */
    String currentUser;


    ///////////////////////
    /** FXML Attributes **/
    ///////////////////////

    @FXML
    private ScrollPane mainScrollPane;

    /* Scene Panes */
    @FXML
    private Pane mainPane, loginPane, requestPane, adminHubPane, adminRequestPane, adminMapPane, adminEmployeePane, adminLogPane, pathfindingPane, requestHubPane;

    @FXML
    private AnchorPane searchPane; // search bar

    /* Map Panes */
    @FXML
    private Pane mapEditMapPane, mapPane, requestMapPane, pathfindingMapPane;

    @FXML
    private StackPane menuARStackPane;

    @FXML
    private Canvas canvas, mapEditCanvas, requestCanvas, pathfindingCanvas;

    @FXML
    private JFXTextField originField, destinationField;

    @FXML
    private ImageView imageView, mapEditImageView, requestImageView, pathfindingImageView;

    /* Pathfinding Scene */
    @FXML
    private JFXTextField pathfindingOrigin, pathfindingDestination;

    @FXML
    private JFXListView pathfindingTextDirections;

    @FXML
    private JFXSlider pathfindingZoomSlider;


    /* Staff Request Scene */
    @FXML
    private JFXTextField requestNodeID, requestCleanupName, requestInterpreterName, requestFoodName, foodItem;

    @FXML
    private JFXTextArea requestCleanupDescription, requestInterpreterDescription, requestFoodDescription, requestInfo;

    @FXML
    private Tab requestFoodTab, requestCleanupTab, requestInterpreterTab;

    @FXML
    private JFXComboBox languageSelect;

    @FXML
    private JFXListView currentFoodOrder;

    /* Request Report Scene */
    @FXML
    private JFXButton printLogSR, sendLogSR, clearLogSR;

    /* MAP ADMIN FXML */
    @FXML
    private Tab addNode, editNode, removeNode, kioskTab, addEdge, removeEdge, edgesTab, nodesTab;

    @FXML
    private Label mapEditText, nodeLocation1, nodeLocation2, nodeLocation3, currentFloorNum, currentFloorNumRequest, currentFloorNumMapEdit, currentFloorNumPathfinding;

    @FXML
    private JFXComboBox nodeTypeCombo, buildingCombo, nodeTypeComboEdit;

    @FXML
    private JFXTextField xPosAddNode, yPosAddNode, xPosEdit, yPosEdit, xPosRemoveNode, yPosRemoveNode,
            xPosAddEdge, yPosAddEdge, xPosRemoveEdge, yPosRemoveEdge,
            setKioskX, setKioskY, editNodeTypeField,
            shortNameAdd, shortNameEdit,
            longNameAdd, longNameEdit, requestName, requestDescription,
            edgeXStartAdd,edgeYStartAdd,edgeXEndAdd,edgeYEndAdd,
            edgeXStartRemove,edgeYStartRemove,edgeXEndRemove,edgeYEndRemove, editNodeID;

    @FXML
    private JFXListView nodesListView, allStaffRequests, requestsIMade;


    /* Requests ADMIN FXML */
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

    /* Login Screen */
    @FXML
    private JFXButton staffLogin, staffCancel, adminLogin, adminCancel;

    @FXML
    private JFXTextField staffLoginText, adminLoginText;

    @FXML
    private JFXPasswordField staffPasswordText, adminPasswordText;

    /* Admin Logs */

    @FXML
    private TreeTableView<AdminLog> adminLogs = new TreeTableView<AdminLog>();

    @FXML
    private TreeTableColumn<AdminLog,String> dateLogged = new TreeTableColumn<AdminLog,String>();

    @FXML
    private TreeTableColumn<AdminLog,String> adminLogged = new TreeTableColumn<AdminLog,String>();

    @FXML
    private TreeTableColumn<AdminLog,String> logContent = new TreeTableColumn<AdminLog,String>();

    @FXML
    private ImageView logImage;

    @FXML
    private Label logLabel;

    @FXML
    private JFXButton printLog, sendLog, clearLog, backAdminHub;

    /* Employee Editing */
    @FXML
    private JFXListView employeeListAE;

    @FXML
    private JFXTextField employeeUserIDAE;

    @FXML
    private JFXTextField employeeUsernameAE;

    @FXML
    private JFXPasswordField employeePasswordAE;

    @FXML
    private JFXComboBox employeeTypeAE;

    @FXML
    private JFXToggleButton adminToggle;

    @FXML
    private JFXSlider zoomSlider;

    /* Staff Request Hub */


    @FXML
    private JFXButton foodButton, interpreterButton, cleanupButton, APITestButton, serviceHubBack;



    SceneSwitcher sceneSwitcher = new SceneSwitcher();



    /* Scene Commandments */
    MainSceneController mainSceneController;
    AdminEmployeeController adminEmployeeController;
    AdminLogController adminLogController;
    AdminMapController adminMapController;
    StaffRequestController staffRequestController;
    AdminRequestController adminRequestController;
    RequestReportController requestReportController;
    DirectorySceneController directorySceneController;
    PathfindingSceneController pathfindingSceneController;
    StaffRequestHubController staffRequestHubController;

    boolean firstTime = true;
    @FXML
    private void initialize() {
        //pathFindingFacade.setPathfinder(beamSearch);
        if (firstTime){ pathFindingFacade.setPathfinder(astar);}
        initializeDirectoryScene();
        initializeMainScene();
        initializeRequestScene();
        initializeRequestReportScene();
        initializeMapAdminScene();
        initializeAdminRequestScene();
        initializeAdminEmployeeScene();
        initializeAdminLogScene();
        initializePathfindingScene();
        initializeStaffRequestHubScene();
        firstTime = false;
    }

    private void initializePathfindingScene() {
        pathfindingSceneController = new PathfindingSceneController(this, pathfindingImageView, pathfindingMapPane,
                pathfindingCanvas, mapNavigationFacade, pathFindingFacade, currentFloorNumPathfinding,
                pathfindingOrigin, pathfindingDestination, pathfindingTextDirections, pathfindingZoomSlider);
    }

    private void initializeDirectoryScene() {
        directorySceneController = new DirectorySceneController(mapNavigationFacade);
    }

    private void initializeMainScene() {
        mainSceneController = new MainSceneController(this, null, null, null, mapNavigationFacade,
                pathFindingFacade, currentFloorNum, originField, destinationField, zoomSlider, directorySceneController, searchPane, mainScrollPane);
        mainSceneController.initializeScene();
        directorySceneController.setMainSceneController(mainSceneController);
    }

    private void initializeRequestScene() {
        staffRequestController = new StaffRequestController(this, requestImageView, requestMapPane, requestCanvas,
                mapNavigationFacade, pathFindingFacade, currentFloorNumRequest, genericRequestController, requestCleanupController,
                requestInterpreterController, requestFoodController, allStaffRequests, requestsIMade, requestNodeID,
                requestCleanupName, requestInterpreterName, requestFoodName, requestCleanupDescription, languageSelect,
                requestInterpreterDescription, requestFoodDescription, requestInfo, currentFoodOrder, foodItem, null);
    }

    private void initializeMapAdminScene() {
        adminMapController = new AdminMapController(this, databaseGargoyle, edgeManager, nodeManager, nodeEditController, edgeEditController,
                mapEditImageView, mapEditMapPane, mapEditCanvas, mapNavigationFacade, pathFindingFacade,
                currentFloorNumMapEdit, addNode, editNode, removeNode, addEdge, removeEdge, kioskTab, edgesTab, nodesTab, null);
    }

    private void initializeAdminLogScene() {
        adminLogController = new AdminLogController (databaseGargoyle, adminLogs, dateLogged,
                 adminLogged, logContent, adminLogManager,userManager);
    }

    private void initializeAdminRequestScene(){
        adminRequestController = new AdminRequestController();
//        TreeItem<Log> log1 = new TreeItem<>(new Log("11/27/2017","admin1","added Node"));
//        TreeItem<Log> log2 = new TreeItem<>(new Log("11/27/2017","admin1","logged in"));
//        TreeItem<Log> log3 = new TreeItem<>(new Log("11/27/2017","admin1","added Node"));
//        TreeItem<Log> log4 = new TreeItem<>(new Log("11/27/2017","admin1","added Node"));
//        TreeItem<Log> log5 = new TreeItem<>(new Log("11/27/2017","admin1","added Node"));
    }

    private void initializeRequestReportScene(){ requestReportController = new RequestReportController(); }

    private void initializeAdminEmployeeScene() { adminEmployeeController = new AdminEmployeeController(userManager,
            genericRequestController,employeeListAE, employeeUserIDAE, employeeUsernameAE, employeePasswordAE,
            employeeTypeAE, adminToggle);
    }

    private void initializeStaffRequestHubScene(){ staffRequestHubController = new StaffRequestHubController(nodeManager); }

    /** Organize Functions by Scene **/

    ////////////////
    /* Main scene */
    ////////////////

    @FXML
    private void openDirectory() throws IOException { mainSceneController.openDirectory(this); }

    @FXML
    private void directoryNavigate() {mainSceneController.directoryNavigate(); }
    @FXML
    private void mainZoom() { mainSceneController.zoom(); }

    @FXML
    private void setOriginByMouse(MouseEvent m) { mainSceneController.setOrigin(m);}

    @FXML
    private void setLoc1(ActionEvent e) { mainSceneController.setOrigin(); }

    @FXML
    private void setLoc2(ActionEvent e) { mainSceneController.setDestination(); }

    @FXML
    private void findPath(ActionEvent e) throws IOException { mainSceneController.findPath(); }

    //zooms in by 0.1 on click of zoom in button
    @FXML
    private void zoomInMap() { mainSceneController.zoomInMap(); }

    //zooms out by 0.1 on click of zoom out button
    @FXML
    private void zoomOutMap() { mainSceneController.zoomOutMap(); }

    @FXML
    private void snapToNode(MouseEvent m) { mainSceneController.snapToNode(m); }

    @FXML
    private void clearCanvas(){ mainSceneController.clearCanvas(); }

    @FXML
    private void bathroomClicked(ActionEvent e){ mainSceneController.bathroomClicked(); }

    @FXML
    private void infoClicked(ActionEvent e){ mainSceneController.infoClicked(); }

    @FXML
    private void elevatorClicked(ActionEvent e){ mainSceneController.elevatorClicked(); }

    @FXML
    private void floorDown() throws IOException, SQLException { mainSceneController.floorDown(); }

    @FXML
    private void floorUp() throws IOException, SQLException { mainSceneController.floorUp(); }

    @FXML
    private void clickOnMap(MouseEvent m) { mainSceneController.clickOnMap(m); }

    @FXML
    private void navigateToHere() throws IOException {mainSceneController.navigateToHere();}

    @FXML //opens up the info popup
    private void informationMain(){}//TODO

    @FXML
    private void clearOriginMain(){}//TODO

    @FXML
    private void clearDestinationMain(){}//TODO

    @FXML
    private void nearestInfoDeskMain(){}//TODO

    @FXML
    private void nearestBathroomMain(){}//TODO

    @FXML
    private void nearestElevatorMain(){}//TODO

    ///////////////////////
    /* Pathfinding Scene */
    ///////////////////////
    @FXML
    private void clearOriginPathfinding(){}//TODO

    @FXML
    private void clearDestinationPathfinding(){}//TODO

    @FXML
    private void floorUpPathfinding(){}//TODO

    @FXML
    private void floorDownPathfinding(){}//TODO

    @FXML
    private void nearestInfoDeskPathfinding(){}//TODO

    @FXML
    private void nearestBathroomPathfinding(){}//TODO

    @FXML
    private void nearestElevatorPathfinding(){}//TODO

    @FXML
    private void pathfindingZoom() { pathfindingSceneController.zoom(); }

    @FXML//switches the origin and the destination nodes, then renavagates.
    private void switchOrigWDest(){}//TODO


    /////////////////////////
    /* Staff Request Scene */
    /////////////////////////

    @FXML
    private void navigateToRequest() throws IOException { staffRequestController.navigateToRequest(); } //TODO

    @FXML
    private void completeStaffRequest() { staffRequestController.completeRequest(); } //TODO

    @FXML
    private void generateStaffReport() {} //TODO

    @FXML
    private void editStaffRequest() { staffRequestController.editRequest(); }

    @FXML
    private void deleteStaffRequest() { staffRequestController.deleteRequest(); }

    @FXML
    private void zoomInRequestMap() { staffRequestController.zoomInMap(); }

    @FXML
    private void zoomOutRequestMap() { staffRequestController.zoomOutMap(); }

    @FXML
    private void floorDownRequest() throws IOException, SQLException { staffRequestController.floorDown(); }

    @FXML
    private void floorUpRequest() throws IOException, SQLException { staffRequestController.floorUp(); }

    @FXML
    private void clickOnRequestMap(MouseEvent m) { staffRequestController.clickOnMap(m); }

    @FXML
    private void addCleanupRequest() { staffRequestController.addCleanup(); }

    @FXML
    private void resetCleanupRequest() { staffRequestController.resetCleanup(); }

    @FXML
    private void addInterpreter() { staffRequestController.addInterpreter(); }

    @FXML
    private void resetInterpreter() { staffRequestController.resetInterpreter(); }

    @FXML
    private void completeRequest() { staffRequestController.completeRequest(); }

    @FXML
    private void resetOrder() { staffRequestController.resetCurrentOrder(); }

    @FXML
    private void addFoodItem() { staffRequestController.addFoodItem(); }

    @FXML
    private void submitFoodRequest() { staffRequestController.submitFoodRequest(); }

    @FXML
    private void resetFoodRequest() { staffRequestController.resetFoodRequest(); }

    /////////////////////
    /* Request Reports */
    /////////////////////

    @FXML
    private void printLogSR(MouseEvent e){requestReportController.printLogSR();}

    @FXML
    private void sendLogSR(MouseEvent e){requestReportController.sendLogSR();}

    @FXML
    private void clearLogSR(MouseEvent e){requestReportController.clearLogSR();}

    ////////////////////
    /* Employee Admin */
    ////////////////////

   @FXML
   private void addEmployeeAE() {
       adminEmployeeController.addEmployeeAE();
       adminLogManager.addAdminLog(new AdminLog(userManager.getUserByName(currentUser),"Added a new Employee", LocalDateTime.now()));
   }

   @FXML
   private void cancelEmployeeAE() {adminEmployeeController.cancelEmployeeAE();}

    @FXML
    private void editEmployeeAE() {
       adminEmployeeController.editEmployeeAE();
        adminLogManager.addAdminLog(new AdminLog(userManager.getUserByName(currentUser),"Edited a Employee", LocalDateTime.now()));
   }

    @FXML
    private void deleteEmployeeAE() {
        adminEmployeeController.deleteEmployeeAE();
        adminLogManager.addAdminLog(new AdminLog(userManager.getUserByName(currentUser),"Removed an Employee", LocalDateTime.now()));

    }

    @FXML
    private void toggleAdmin(MouseEvent e) {adminEmployeeController.toggleAdmin();}

    ///////////////////
    /* Request Admin */
    ///////////////////

    //Spills
    @FXML
    private void displayARSpillsOnMap() throws IOException { adminRequestController.displayARSpillsOnMap(); }

    @FXML
    private void addARSpills() throws IOException { adminRequestController.addARSpills(); }

    @FXML
    private void cancelARSpills() throws IOException { adminRequestController.cancelARSpills(); }

    @FXML
    private void editARSpills() throws IOException { adminRequestController.editARSpills(); }

    @FXML
    private void deleteARSpills() throws IOException { adminRequestController.deleteARSpills(); }

    @FXML
    private void deleteAllARSpills() throws IOException { adminRequestController.deleteAllARSpills(); }

    //Food
    @FXML
    private void displayARFoodOnMap(MouseEvent e) throws IOException { adminRequestController.displayARFoodOnMap(); }

    @FXML
    private void addARFood() throws IOException { adminRequestController.addARFood(); }

    @FXML
    private void cancelARFood() throws IOException { adminRequestController.cancelARFood(); }

    @FXML
    private void editARFood() throws IOException { adminRequestController.editARFood(); }

    @FXML
    private void deleteARFood() throws IOException { adminRequestController.deleteARFood(); }

    @FXML
    private void deleteAllARFood() throws IOException { adminRequestController.deleteAllARFood(); }

    @FXML
    public void editARMenuFoodPopUp() throws  IOException { adminRequestController.editARMenuFoodPopUp(); }

    @FXML
    private void addARMenuFood() throws IOException { adminRequestController.addARMenuFood(); }

    @FXML
    private void cancelARMenuFood() throws IOException { adminRequestController.cancelARMenuFood(); }

    @FXML
    private void editARMenuFood() throws IOException { adminRequestController.editARMenuFood(); }

    @FXML
    private void deleteARMenuFood() throws IOException { adminRequestController.deleteARMenuFood(); }

    @FXML
    private void deleteAllARMenuFood() throws IOException { adminRequestController.deleteAllARMenuFood(); }

    //Interpreter

    @FXML
    private void displayARInterpreterOnMap() throws IOException { adminRequestController.displayARInterpreterOnMap(); }

    @FXML
    private void addARInterpreter() throws IOException { adminRequestController.addARInterpreter(); }

    @FXML
    private void cancelARInterpreter() throws IOException { adminRequestController.cancelARInterpreter(); }

    @FXML
    private void editARInterpreter() throws IOException { adminRequestController.editARInterpreter(); }

    @FXML
    private void deleteARInterpreter() throws IOException { adminRequestController.deleteARInterpreter(); }

    @FXML
    private void deleteAllARInterpreter() throws IOException { adminRequestController.deleteAllARInterpreter(); }

    /////////////////
    /* Map Editing */
    /////////////////

    @FXML
    private void addNodeButton() {
        adminMapController.addNode();
        adminLogManager.addAdminLog(new AdminLog(userManager.getUserByName(currentUser),"Added a new Node", LocalDateTime.now()));
    }

    @FXML
    private void removeNodeButton() {
        adminMapController.removeNodeButton();
        adminLogManager.addAdminLog(new AdminLog(userManager.getUserByName(currentUser),"Removed a Node", LocalDateTime.now()));
    }

    @FXML
    private void resetNodeButtonAdd() {
        adminMapController.resetNodeButtonAdd();
    }

    @FXML
    private void editNode() {
        adminMapController.editNode();
        adminLogManager.addAdminLog(new AdminLog(userManager.getUserByName(currentUser),"Edited a Node", LocalDateTime.now()));
    }

    @FXML
    private void resetNodeButtonEdit() { adminMapController.resetNodeButtonEdit(); }

    @FXML
    private void resetNodeButtonRemove() { adminMapController.resetNodeButtonRemove(); }

    @FXML
    private void removeEdgeButton() {
        adminMapController.removeEdgeButton();
        adminLogManager.addAdminLog(new AdminLog(userManager.getUserByName(currentUser),"Removed a Node", LocalDateTime.now()));
    }

    @FXML
    private void resetEdgeButtonRemove() { adminMapController.resetEdgeButtonRemove(); }

    @FXML
    private void addEdgeButton() {
        adminMapController.addEdgeButton();
        adminLogManager.addAdminLog(new AdminLog(userManager.getUserByName(currentUser),"Added a new Edge", LocalDateTime.now()));
    }

    @FXML
    private void resetEdgeButtonAdd() { adminMapController.resetEdgeButtonAdd(); }

    @FXML
    private void zoomInMapEdit() { adminMapController.zoomInMap(); }

    @FXML
    private void zoomOutMapEdit() { adminMapController.zoomOutMap(); }

    @FXML
    private void floorDownMapEdit() throws IOException, SQLException { adminMapController.floorDown(); }

    @FXML
    private void floorUpMapEdit() throws IOException, SQLException { adminMapController.floorUp(); }

    @FXML
    private void clickOnMapEdit(MouseEvent m) { adminMapController.clickOnMap(m); }

    @FXML
    private void setDefaultNode() {
        adminMapController.setKioskLocation();
        adminLogManager.addAdminLog(new AdminLog(userManager.getUserByName(currentUser),"Changed Kiosk Default Node", LocalDateTime.now()));
    }

    @FXML
    private void selectAstar() { pathFindingFacade.setPathfinder(astar); }

    @FXML
    private void selectBeam() { pathFindingFacade.setPathfinder(beam); } //todo

    @FXML
    private void selectBreadth() { pathFindingFacade.setPathfinder(breadth); }

    @FXML
    private void selectDepth() { pathFindingFacade.setPathfinder(depth);
    }

    @FXML
    private void resetDefaultNode() { adminMapController.resetKioskScene(); } //TODO

    @FXML
    private void exportNodes() { adminMapController.exportNodes(); }

    @FXML
    private void exportEdges() { adminMapController.exportEdges(); }


    /////////////////////////
    /* Service Request Hub */
    /////////////////////////

    @FXML
    public void serviceHubtoAPITest() { staffRequestHubController.serviceHubtoAPITest(); }

    @FXML
    public void serviceHubtoFoodAPI() { staffRequestHubController.serviceHubtoFoodAPI(); }

    @FXML
    public void serviceHubtoRequest() throws IOException {
        User u = staffRequestHubController.getUser();
        sceneSwitcher.toStaffRequests(this, requestHubPane);
        staffRequestController.initializeScene(userManager.getUser(u.getUserID()));
    }

    @FXML
    public void serviceHubToMain() throws IOException { sceneSwitcher.toMain(this, requestHubPane); }


    ////////////////
    /* Admin Logs */
    ////////////////

    @FXML
    public void clearLogButton() throws IOException{ adminLogController.clearLogButton(); }

    @FXML
    public void exportLogs() {adminLogController.exportLogs(); }

    /////////////////////
    /* Scene Switching */
    /////////////////////
    @FXML
    private void mainToLogin() throws IOException { sceneSwitcher.toLogin(this, mainPane); }

    @FXML
    public void mainToPathfinding() throws IOException {
        Node o = mainSceneController.getOrigin();
        Node d =  mainSceneController.getDestination();
        sceneSwitcher.toPathfinding(this, mainPane);
        pathfindingSceneController.initializeScene();
        pathfindingSceneController.findPath(o, d);
    }

    @FXML
    private void pathfindingToMain() throws IOException { sceneSwitcher.toMain(this, pathfindingPane);}

    @FXML
    private void requestToMain() throws IOException { sceneSwitcher.toMain(this, requestPane); }

    @FXML
    private void goToMainScene() throws IOException { sceneSwitcher.toMain(this, loginPane); }

    @FXML
    private void goToRequests() throws IOException {
       if (userLoginController.authenticateStaff(staffLoginText.getText(), staffPasswordText.getText())){
            sceneSwitcher.toStaffRequestHub(this, loginPane);
            staffRequestHubController.setUser(userManager.getUserByName(staffLoginText.getText()));
        } else errorController.showError("Invalid credentials! Please try again.");
    }

    @FXML
    private void serviceHubToRequest() throws IOException {
        sceneSwitcher.toStaffRequests(this, requestPane);
        System.out.println(staffRequestHubController.getUser());
        staffRequestController.initializeScene(userManager.getUser(staffRequestHubController.getUser().getUserID()));
    }

    @FXML
    private void goToAdminHub() throws IOException {
        if (userLoginController.authenticateAdmin(adminLoginText.getText(), adminPasswordText.getText())) {
            currentUser = adminLoginText.getText();
            System.out.println(currentUser);
            sceneSwitcher.toAdminHub(this, loginPane);
            adminLogController.initializeScene(userManager.getUserByName(adminLoginText.getText()));
            adminLogManager.addAdminLog(new AdminLog(userManager.getUserByName(currentUser),"Logged in", LocalDateTime.now()));
        } else errorController.showError("Invalid credentials! Please try again.");

    }

    @FXML
    private void adminHubToMain() throws IOException { sceneSwitcher.toMain(this, adminHubPane); }

    @FXML
    private void adminHubtoLog() throws IOException {
        sceneSwitcher.toAdminLog(this, adminHubPane);
        adminLogController.initializeScene(userManager.getUserByName(adminLoginText.getText()));
    }

    @FXML
    private void adminHubtoRequest() throws IOException {
        // sceneSwitcher.toAdminRequests(this, adminHubPane);
        // adminRequestController.initializeScene();
    }

    @FXML
    private void adminHubtoEmployee() throws IOException {
        sceneSwitcher.toAdminEmployee(this, adminHubPane);
        adminEmployeeController.initializeScene();
    }

    @FXML
    private void adminHubtoMap() throws IOException {
        sceneSwitcher.toAdminMap(this, adminHubPane);
        adminMapController.initializeScene();
        adminMapController.initializeNodeAdder(xPosAddNode, yPosAddNode, nodeTypeCombo, buildingCombo, shortNameAdd, longNameAdd);
        adminMapController.initializeNodeEditor(editNodeID, xPosEdit, yPosEdit, nodeTypeComboEdit, shortNameEdit, longNameEdit, editNodeTypeField);
        adminMapController.initializeNodeRemover(xPosRemoveNode, yPosRemoveNode);
        adminMapController.initializeEdgeAdder(edgeXStartAdd, edgeYStartAdd, edgeXEndAdd, edgeYEndAdd);
        adminMapController.initializeEdgeRemover(edgeXStartRemove, edgeYStartRemove, edgeXEndRemove, edgeYEndRemove);
        adminMapController.initializeKioskEditor(setKioskX, setKioskY);
    }

    @FXML
    private void requestToAdminHub() throws IOException { sceneSwitcher.toAdminHub(this, adminRequestPane); }

    @FXML
    private void mapToAdminHub() throws IOException { sceneSwitcher.toAdminHub(this, adminMapPane); }

    @FXML
    private void logToAdminHub() throws IOException { sceneSwitcher.toAdminHub(this, adminLogPane); }

    @FXML
    private void employeeToAdminHub() throws IOException { sceneSwitcher.toAdminHub(this, adminEmployeePane); }

    @FXML
    private void getTextDirections() throws IOException {mainSceneController.displayTextDir();}

    public void initializeObservers(){
        databaseGargoyle.attachManager(nodeManager);
        databaseGargoyle.attachManager(userManager);
        databaseGargoyle.attachManager(edgeManager);
        databaseGargoyle.attachManager(cleanupManager);
        databaseGargoyle.attachManager(interpreterManager);
        databaseGargoyle.attachManager(foodManager);
        databaseGargoyle.attachManager(adminLogManager);
        databaseGargoyle.notifyManagers();
    }
}