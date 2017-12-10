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
import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class GodController {
    /* Database Gargoyle */
    final private DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();

    /* managers */
    final private AdminLogManager adminLogManager = new AdminLogManager(databaseGargoyle);
    final private NodeManager nodeManager = new NodeManager(databaseGargoyle, adminLogManager);
    final private EdgeManager edgeManager = new EdgeManager(databaseGargoyle, nodeManager, adminLogManager);
    final private UserManager userManager = new UserManager(databaseGargoyle, adminLogManager);
    final private CleanUpManager cleanupManager = new CleanUpManager(databaseGargoyle, nodeManager, userManager);
    final private InterpreterManager interpreterManager = new InterpreterManager(databaseGargoyle, nodeManager, userManager);
    final private FoodManager foodManager = new FoodManager(databaseGargoyle, nodeManager, userManager);


    /* Controllers */
    final private GenericRequestController genericRequestController = new GenericRequestController(cleanupManager, foodManager, interpreterManager);
    final private NodeEditController nodeEditController = new NodeEditController(nodeManager, edgeManager, genericRequestController, adminLogManager, databaseGargoyle);
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
    final private SearchEngine searchEngine = new SearchEngine(directoryController);

    /* Facades */
    final private MapNavigationFacade mapNavigationFacade = new MapNavigationFacade(clickController,
            nearestPOIController, mapDisplayController, directoryController);
    final private PathFindingFacade pathFindingFacade = new PathFindingFacade();

    /* Entities */
    final private Astar astar = new Astar(edgeManager);
    final private BeamSearch beam = new BeamSearch(edgeManager);
    final private BreadthSearch breadth = new BreadthSearch(edgeManager);
    final private DepthSearch depth = new DepthSearch(edgeManager);
    final private BestFirst best = new BestFirst(edgeManager);
    final private Dijkstra dijkstra = new Dijkstra(edgeManager);


    ///////////////////////
    /** FXML Attributes **/
    ///////////////////////

    @FXML
    private JFXHamburger hamburger;

    @FXML
    private JFXDrawer drawer;

    @FXML
    private JFXButton directoryButton;

    @FXML
    private AnchorPane searchAnchor;

    @FXML
    private ScrollPane mainScrollPane, mapEditScrollPane, requestScrollPane, pathfindingScrollPane;

    /* Scene Panes */
    @FXML
    private Pane mainPane, loginPane, requestPane, adminHubPane, adminRequestPane, adminMapPane, adminEmployeePane, adminLogPane, pathfindingPane, requestHubPane;

    /* Zoom Sliders */
    @FXML
    private JFXSlider pathfindingZoomSlider, mapEditZoomSlider, zoomSlider, requestZoomSlider;


    @FXML
    private AnchorPane textPane, directoryPane; // search bar, directory

    @FXML
    private JFXComboBox originField;

    /* Pathfinding Scene */
    @FXML
    private JFXTextField pathfindingOrigin, pathfindingDestination;

    @FXML
    private JFXListView pathfindingTextDirections;


    /* Staff Request Scene */
    @FXML
    private JFXTextField requestNodeID, requestCleanupName, requestInterpreterName, requestFoodName, foodItem;

    @FXML
    private JFXTextArea requestCleanupDescription, requestInterpreterDescription, requestFoodDescription, requestInfo;

    @FXML
    private JFXComboBox languageSelect;

    @FXML
    private JFXListView currentFoodOrder;

    /* MAP Editing */
    @FXML
    private Tab addNode, editNode, removeNode, kioskTab, addEdge, removeEdge, edgesTab, nodesTab, straightenTab;

    @FXML
    private Label currentFloorNum, currentFloorNumRequest, currentFloorNumMapEdit, currentFloorNumPathfinding;

    @FXML
    private JFXComboBox nodeTypeCombo, buildingCombo, nodeTypeComboEdit;

    @FXML
    private JFXTextField xPosAddNode, yPosAddNode, xPosEdit, yPosEdit, xPosRemoveNode, yPosRemoveNode, setKioskX,
            setKioskY, editNodeTypeField, shortNameAdd, shortNameEdit, longNameAdd, longNameEdit, requestDescription,
            edgeXStartAdd, edgeYStartAdd, edgeXEndAdd,edgeYEndAdd, edgeXStartRemove, edgeYStartRemove, edgeXEndRemove,
            edgeYEndRemove, editNodeID, edgeXStartStraighten, edgeYStartStraighten, edgeXEndStraighten,
            edgeYEndStraighten, distanceScale;;

    @FXML
    private JFXListView allStaffRequests, requestsIMade;

    /* Login Screen */
    @FXML
    private JFXTextField staffLoginText, adminLoginText;

    @FXML
    private JFXPasswordField staffPasswordText, adminPasswordText;

    /* Admin Logs */

    @FXML
    private TreeTableView<AdminLog> adminLogs = new TreeTableView<AdminLog>();

    @FXML
    private TreeTableColumn<AdminLog,String> dateLogged = new TreeTableColumn<AdminLog,String>(),
            adminLogged = new TreeTableColumn<AdminLog,String>(), logContent = new TreeTableColumn<AdminLog,String>();

    /* Employee Editing */
    @FXML
    private JFXTreeTableView<User> employeeTable = new JFXTreeTableView<>();

    @FXML
    private TreeTableColumn<User, String> userIDColumn = new TreeTableColumn<>();

    @FXML
    private TreeTableColumn<User, String> departmentColumn = new TreeTableColumn<>();

    @FXML
    private TreeTableColumn<User, String> usernameColumn = new TreeTableColumn<>();

    @FXML
    private TreeTableColumn<User, String> passwordColumn = new TreeTableColumn<>();

    @FXML
    private TreeTableColumn<User, String> adminStatusColumn = new TreeTableColumn<>();

    @FXML
    private JFXTextField employeeUserIDAE, employeeUsernameAE;

    @FXML
    private JFXPasswordField employeePasswordAE;

    @FXML
    private JFXComboBox employeeTypeAE;

    @FXML
    private JFXToggleButton adminToggle;

    SceneSwitcher sceneSwitcher = new SceneSwitcher();

    /* Scene Facilitators */
    MainSceneController mainSceneController;
    AdminEmployeeController adminEmployeeController;
    AdminLogController adminLogController;
    AdminMapController adminMapController;
    StaffRequestController staffRequestController;
    AdminRequestController adminRequestController;
    RequestReportController requestReportController;
    DirectoryDrawerController directoryDrawerController;
    StaffRequestHubController staffRequestHubController;

    boolean firstTime = true;

    private Stage primaryStage;

    public GodController(Stage primaryStage) { this.primaryStage = primaryStage; } //TODO do we need the stage?

    @FXML
    private void initialize() throws IOException {
        //pathFindingFacade.setPathfinder(beamSearch);
        if (firstTime){ pathFindingFacade.setPathfinder(astar);}
        initializeDirectoryDrawer();
        initializeMainScene();
        initializeRequestScene();
        initializeRequestReportScene();
        initializeMapAdminScene();
        initializeAdminRequestScene();
        initializeAdminEmployeeScene();
        initializeAdminLogScene();
        initializeStaffRequestHubScene();
        firstTime = false;
    }

    private void initializeDirectoryDrawer() {
        directoryDrawerController = new DirectoryDrawerController(mapNavigationFacade);
    }

    private void initializeMainScene() throws IOException {
        mainSceneController = new MainSceneController(this, mapNavigationFacade, pathFindingFacade, currentFloorNum,
                originField, searchAnchor, zoomSlider, directoryController, directoryDrawerController, textPane, mainScrollPane, drawer, hamburger, mainPane);
        mainSceneController.initializeScene();
    }

    private void initializeRequestScene() {
        staffRequestController = new StaffRequestController(this, mapNavigationFacade, pathFindingFacade,
                currentFloorNumRequest, genericRequestController, requestCleanupController,
                requestInterpreterController, requestFoodController, allStaffRequests, requestsIMade, requestNodeID,
                requestCleanupName, requestInterpreterName, requestFoodName, requestCleanupDescription, languageSelect,
                requestInterpreterDescription, requestFoodDescription, requestInfo, currentFoodOrder, foodItem,
                requestZoomSlider, requestScrollPane);
    }

    private void initializeMapAdminScene() {
        adminMapController = new AdminMapController(this, databaseGargoyle, edgeManager, nodeManager,
                nodeEditController, edgeEditController, mapNavigationFacade, pathFindingFacade, currentFloorNumMapEdit,
                addNode, editNode, removeNode, addEdge, removeEdge, kioskTab, edgesTab, nodesTab, straightenTab, mapEditZoomSlider, mapEditScrollPane);
    }

    private void initializeAdminLogScene() {
        adminLogController = new AdminLogController (databaseGargoyle, adminLogs, dateLogged,
                adminLogged, logContent, adminLogManager,userManager);
    }

    private void initializeAdminRequestScene(){ adminRequestController = new AdminRequestController(); }

    private void initializeRequestReportScene(){ requestReportController = new RequestReportController(); }

    private void initializeAdminEmployeeScene() { adminEmployeeController = new AdminEmployeeController(userManager,
            genericRequestController, employeeUserIDAE, employeeUsernameAE, employeePasswordAE,
            employeeTypeAE, adminToggle, employeeTable,
              userIDColumn,  departmentColumn,
              usernameColumn,  passwordColumn,adminStatusColumn);
    }

    private void initializeStaffRequestHubScene(){ staffRequestHubController = new StaffRequestHubController(nodeManager); }

    /** Organize Functions by Scene **/

    ////////////////
    /* Main scene */
    ////////////////

    @FXML
    private void reversePath() throws IOException { mainSceneController.reversePath(); }
    @FXML
    private void openDirectory() throws IOException { mainSceneController.openDirectory(directoryPane); }

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

    @FXML
    private void snapToNode(MouseEvent m) { mainSceneController.snapToNode(m); }

    @FXML
    private void floorDown() throws IOException, SQLException { mainSceneController.floorDown(); }

    @FXML
    private void floorUp() throws IOException, SQLException { mainSceneController.floorUp(); }

    @FXML
    private void clickOnMap(MouseEvent m) { mainSceneController.clickOnMap(m); }

    @FXML
    private void navigateToHere() throws IOException { mainSceneController.navigateToHere(); }

    @FXML
    private void clearOriginMain(){}//TODO

    @FXML
    private void nearestInfoDeskMain() throws IOException { mainSceneController.infoClicked(); }

    @FXML
    private void nearestBathroomMain() throws IOException { mainSceneController.bathroomClicked(); }

    @FXML
    private void nearestExitMain() throws IOException { mainSceneController.exitClicked(); }

    /////////////////////////
    /* Staff Request Scene */
    /////////////////////////

    @FXML
    private void requestZoom() { staffRequestController.zoom(); }

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
    private void addEmployeeAE() { adminEmployeeController.addEmployeeAE(); }

    @FXML
    private void cancelEmployeeAE() { adminEmployeeController.cancelEmployeeAE(); }

    @FXML
    private void editEmployeeAE() { adminEmployeeController.editEmployeeAE(); }

    @FXML
    private void deleteEmployeeAE() { adminEmployeeController.deleteEmployeeAE(); }

    @FXML
    private void toggleAdmin(MouseEvent e) { adminEmployeeController.toggleAdmin(); }

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
    private void mapEditZoom() { adminMapController.zoom(); }

    @FXML
    private void straightenButton() { adminMapController.straighten(); }

    @FXML
    private void resetStraightenButton() { adminMapController.resetStraightener(); }

    @FXML
    private void addNodeButton() { adminMapController.addNode(); }

    @FXML
    private void removeNodeButton() { adminMapController.removeNodeButton(); }

    @FXML
    private void resetNodeButtonAdd() { adminMapController.resetNodeButtonAdd(); }

    @FXML
    private void editNode() { adminMapController.editNode(); }

    @FXML
    private void resetNodeButtonEdit() { adminMapController.resetNodeButtonEdit(); }

    @FXML
    private void resetNodeButtonRemove() { adminMapController.resetNodeButtonRemove(); }

    @FXML
    private void removeEdgeButton() { adminMapController.removeEdgeButton(); }

    @FXML
    private void resetEdgeButtonRemove() { adminMapController.resetEdgeButtonRemove(); }

    @FXML
    private void addEdgeButton() { adminMapController.addEdgeButton(); }

    @FXML
    private void resetEdgeButtonAdd() { adminMapController.resetEdgeButtonAdd(); }

    @FXML
    private void floorDownMapEdit() throws IOException, SQLException { adminMapController.floorDown(); }

    @FXML
    private void floorUpMapEdit() throws IOException, SQLException { adminMapController.floorUp(); }

    @FXML
    private void clickOnMapEdit(MouseEvent m) { adminMapController.clickOnMap(m); }

    @FXML
    private void setDefaultNode() { adminMapController.setKioskLocation(); }

    @FXML
    private void selectAstar() { pathFindingFacade.setPathfinder(astar); }

    @FXML
    private void selectBeam() { pathFindingFacade.setPathfinder(beam); }

    @FXML
    private void selectBreadth() { pathFindingFacade.setPathfinder(breadth); }

    @FXML
    private void selectDepth() { pathFindingFacade.setPathfinder(depth); }

    @FXML
    private void selectBest() { pathFindingFacade.setPathfinder(best);}

    @FXML
    private void selectDijkstras() { pathFindingFacade.setPathfinder(dijkstra);}

    @FXML
    private void resetDefaultNode() { adminMapController.resetKioskScene(); } //TODO

    @FXML
    private void setDistanceScale(){ adminMapController.setScale(distanceScale); }

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
    private void requestToHub() throws IOException {
        User u = staffRequestController.getUser();
        sceneSwitcher.toStaffRequestHub(this, requestPane);
        staffRequestHubController.setUser(u);
    }

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
            databaseGargoyle.setCurrentUser(userManager.getUserByName(adminLoginText.getText()));
            adminLogManager.addAdminLog(new AdminLog(databaseGargoyle.getCurrentUser().getUserID(), "Successfully logged in as " + databaseGargoyle.getCurrentUser().getUsername(), LocalDateTime.now()));
            System.out.println(databaseGargoyle.getCurrentUser().getUsername());
            sceneSwitcher.toAdminHub(this, loginPane);
            adminLogController.initializeScene(userManager.getUserByName(adminLoginText.getText()));
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
    private void adminHubtoRequest() throws IOException { //TODO this scene needs help
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
        adminMapController.initializeStraightener(edgeXStartStraighten, edgeYStartStraighten, edgeXEndStraighten, edgeYEndStraighten);
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

    @FXML
    private void toAboutPopUp() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/boundary/fxml/aboutPopUp.fxml"));
        fxmlLoader.setController(new AboutPopUpController());
        Parent root2 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("About");
        stage.setScene(new Scene(root2, 1000, 1000));
        stage.setMaximized(true);
        stage.show();
    }

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