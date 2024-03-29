package boundary;


import Admin.UserLoginController;
import Database.*;
import DatabaseSetup.DatabaseGargoyle;
import Editor.EdgeEditController;
import Editor.NodeEditController;
import Entity.*;
import GoogleNodes.GoogleNodeController;
import MapNavigation.*;
import MementoPattern.*;
import Pathfinding.*;
import Request.GenericRequestController;
import Request.RequestCleanupController;
import Request.RequestFoodController;
import Request.RequestInterpreterController;
import boundary.sceneControllers.*;
import boundary.sceneControllers.AdminSettingsPopUpController;
import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import sun.security.x509.AVA;

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
    final private CleanUpManager cleanUpManager = new CleanUpManager(databaseGargoyle,nodeManager,userManager);
    final private GoogleNodeManager googleNodeManager = new GoogleNodeManager(databaseGargoyle);


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
    final private GoogleNodeController googleNodeController = new GoogleNodeController(googleNodeManager);
    //final private AdminSettingsPopUpController adminSettingsPopUpController = new AdminSettingsPopUpController(nodeEditController);

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

    private InactivityListener memento;
    /* Drawer */
    private JFXTreeTableView<textDirEntry> textDirectionsTable;

    private TreeTableColumn<textDirEntry, String> textDirectionsColumn;

    private TreeTableColumn<textDirEntry,ImageView> imageDirectionColumn;


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
    private ScrollPane mainScrollPane, mapEditScrollPane, requestScrollPane, pathfindingScrollPane;

    /* Admin Request */

    @FXML
    private ImageView requestsImageView;

    @FXML
    private JFXButton deleteRequestIntButton;

    @FXML
    private JFXButton deleteAllRequestsIntColumn;

    @FXML
    private JFXTreeTableView<InterpreterRequest> requestsIntTable = new JFXTreeTableView<>();

    @FXML
    private TreeTableColumn<InterpreterRequest, String> requestIntNameColumn = new TreeTableColumn<>();

    @FXML
    private TreeTableColumn<InterpreterRequest, String> timeCreatedIntColumn = new TreeTableColumn<>();

    @FXML
    private TreeTableColumn<InterpreterRequest, String> timeCompleteIntColumn = new TreeTableColumn<>();

    @FXML
    private TreeTableColumn<InterpreterRequest, String> requestTypeIntColumn = new TreeTableColumn<>();

    @FXML
    private TreeTableColumn<InterpreterRequest, String> requestDescriptionIntColumn = new TreeTableColumn<>();

    @FXML
    private TreeTableColumn<InterpreterRequest, String> requestLocationIntColumn = new TreeTableColumn<>();

    @FXML
    private TreeTableColumn<InterpreterRequest, String> requestUserIntColumn = new TreeTableColumn<>();

    @FXML
    private JFXButton deleteRequestSpillButton;

    @FXML
    private JFXButton deleteAllRequestsSpillButton;

    @FXML
    private JFXTreeTableView<CleanUpRequest> requestsTableSpills = new JFXTreeTableView<>();

    @FXML
    private TreeTableColumn<CleanUpRequest, String> requestNameSpillsColumn = new TreeTableColumn<>();

    @FXML
    private TreeTableColumn<CleanUpRequest, String> timeCreatedSpillsColumn = new TreeTableColumn<>();

    @FXML
    private TreeTableColumn<CleanUpRequest, String> timeCompletedSpillsColumn = new TreeTableColumn<>();

    @FXML
    private TreeTableColumn<CleanUpRequest, String> requestTypeSpillsColumn = new TreeTableColumn<>();

    @FXML
    private TreeTableColumn<CleanUpRequest, String> requestDescriptionSpillsColumn = new TreeTableColumn<>();

    @FXML
    private TreeTableColumn<CleanUpRequest, String> requestLocationSpillsColumn = new TreeTableColumn<>();

    @FXML
    private TreeTableColumn<CleanUpRequest, String> requestUserSpillsColumn = new TreeTableColumn<>();

    @FXML
    private JFXButton deleteRequestFoodButton;

    @FXML
    private JFXButton deleteAllRequestsFoodButton;

    @FXML
    private JFXTreeTableView<FoodRequest> requestsTableFood = new JFXTreeTableView<>();

    @FXML
    private TreeTableColumn<FoodRequest, String> requestNameFoodColumn = new TreeTableColumn<>();

    @FXML
    private TreeTableColumn<FoodRequest, String> timeCreatedFoodColumn = new TreeTableColumn<>();

    @FXML
    private TreeTableColumn<FoodRequest, String> timeCompletedFoodColumn = new TreeTableColumn<>();

    @FXML
    private TreeTableColumn<FoodRequest, String> requestTypeFoodColumn = new TreeTableColumn<>();

    @FXML
    private TreeTableColumn<FoodRequest, String> requestDescriptionFoodColumn = new TreeTableColumn<>();

    @FXML
    private TreeTableColumn<FoodRequest, String> requestLocationFoodColumn = new TreeTableColumn<>();

    @FXML
    private TreeTableColumn<FoodRequest, String> requestUserFoodColumn = new TreeTableColumn<>();


    /* Scene Panes */
    @FXML
    private Pane mainPane, loginPane, requestPane, adminHubPane, adminRequestPane, adminMapPane, adminEmployeePane, adminLogPane, pathfindingPane, requestHubPane;

    /* Zoom Sliders */
    @FXML
    private JFXSlider pathfindingZoomSlider, mapEditZoomSlider, zoomSlider, requestZoomSlider;


    @FXML
    private AnchorPane searchAnchor, directoryPane; // search bar, directory

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
    private JFXComboBox nodeTypeCombo, buildingCombo, nodeTypeComboEdit;

    @FXML
    private JFXTextField xPosAddNode, yPosAddNode, xPosEdit, yPosEdit, xPosRemoveNode, yPosRemoveNode, setKioskX,
            setKioskY, editNodeTypeField, shortNameAdd, shortNameEdit, longNameAdd, longNameEdit, requestDescription,
            edgeXStartAdd, edgeYStartAdd, edgeXEndAdd,edgeYEndAdd, edgeXStartRemove, edgeYStartRemove, edgeXEndRemove,
            edgeYEndRemove, editNodeID, edgeXStartStraighten, edgeYStartStraighten, edgeXEndStraighten,
            edgeYEndStraighten;

    @FXML
    private JFXListView allStaffRequests, requestsIMade;

    /* Login Screen */
    @FXML
    private JFXTextField loginText;

    @FXML
    private JFXPasswordField passwordText;

    @FXML
    private JFXToggleButton loginAdminToggle;

    /* Admin Logs */

    @FXML
    private JFXTreeTableView<AdminLog> adminLogs = new JFXTreeTableView<AdminLog>();

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
    NavigationDrawerController navigationDrawerController;
    StaffRequestHubController staffRequestHubController;
    AdminSettingsPopUpController adminSettingsPopUpController;

    boolean firstTime = true;

    private Stage primaryStage;


    public GodController(Stage primaryStage) { this.primaryStage = primaryStage; } //TODO do we need the stage?

    @FXML
    private void initialize() throws IOException {
        //pathFindingFacade.setPathfinder(beamSearch);
        if (firstTime){ pathFindingFacade.setPathfinder(astar);}
        initializeDrawers();
        initializeMainScene();
        initializeRequestScene();
        initializeRequestReportScene();
        initializeMapAdminScene();
        initializeAdminRequestScene();
        initializeAdminEmployeeScene();
        initializeAdminLogScene();
        initializeStaffRequestHubScene();
        initializeAdminSettingsPopUpController();
        firstTime = false;

        memento = new InactivityListener(mainPane, this, sceneSwitcher);
    }

    private void initializeDrawers() {
        navigationDrawerController = new NavigationDrawerController( drawer, directoryController,
                 textDirectionsTable,  textDirectionsColumn,
                 imageDirectionColumn);
        directoryDrawerController = new DirectoryDrawerController(drawer, mapNavigationFacade);
    }

    private void initializeMainScene() throws IOException {
        mainSceneController = new MainSceneController(this, mapNavigationFacade, pathFindingFacade,
                searchAnchor, zoomSlider, directoryController, directoryDrawerController,
                navigationDrawerController, mainScrollPane, drawer, hamburger, mainPane, googleNodeController);
        mainSceneController.initializeScene();
    }


    private void initializeRequestScene() {
        staffRequestController = new StaffRequestController(this, mapNavigationFacade, pathFindingFacade,
                genericRequestController, requestCleanupController, requestInterpreterController, requestFoodController, allStaffRequests, requestsIMade, requestNodeID,
                requestCleanupName, requestInterpreterName, requestFoodName, requestCleanupDescription, languageSelect,
                requestInterpreterDescription, requestFoodDescription, requestInfo, currentFoodOrder, foodItem,
                requestZoomSlider, requestScrollPane);
    }

    private void initializeMapAdminScene() {
        adminMapController = new AdminMapController(this, databaseGargoyle, edgeManager, nodeManager,
                nodeEditController, edgeEditController, mapNavigationFacade, pathFindingFacade,
                addNode, editNode, removeNode, addEdge, removeEdge, kioskTab, edgesTab, nodesTab, straightenTab, mapEditZoomSlider, mapEditScrollPane);
    }

    private void initializeAdminLogScene() {
        adminLogController = new AdminLogController (databaseGargoyle, adminLogs, dateLogged,
                adminLogged, logContent, adminLogManager,userManager);
    }

    private void initializeAdminRequestScene(){ adminRequestController = new AdminRequestController( interpreterManager,  foodManager,
             cleanUpManager, requestsIntTable,
                requestIntNameColumn,   timeCreatedIntColumn,
              timeCompleteIntColumn,  requestTypeIntColumn,
             requestDescriptionIntColumn,  requestLocationIntColumn,
             requestUserIntColumn,  requestsTableSpills,
             requestNameSpillsColumn,  timeCreatedSpillsColumn,
             timeCompletedSpillsColumn,  requestTypeSpillsColumn,
             requestDescriptionSpillsColumn,  requestLocationSpillsColumn,
             requestUserSpillsColumn,  requestsTableFood,
             requestNameFoodColumn,  timeCreatedFoodColumn,
             timeCompletedFoodColumn,   requestTypeFoodColumn,
             requestDescriptionFoodColumn,  requestLocationFoodColumn,
             requestUserFoodColumn); }

    private void initializeRequestReportScene() { requestReportController = new RequestReportController(); }

    private void initializeAdminEmployeeScene() { adminEmployeeController = new AdminEmployeeController(userManager,
            genericRequestController, employeeUserIDAE, employeeUsernameAE, employeePasswordAE,
            employeeTypeAE, adminToggle, employeeTable,
              userIDColumn,  departmentColumn,
              usernameColumn,  passwordColumn,adminStatusColumn);
    }

    private void initializeStaffRequestHubScene(){ staffRequestHubController = new StaffRequestHubController(nodeManager); }

    private void initializeAdminSettingsPopUpController(){adminSettingsPopUpController = new AdminSettingsPopUpController(nodeEditController,
            pathFindingFacade, astar, beam, breadth, depth, best, dijkstra);}


    /** Organize Functions by Scene **/

    ////////////////
    /* Main scene */
    ////////////////

    @FXML
    private void openDirectory() throws IOException { }

    @FXML
    private void zoom() { mainSceneController.zoom(); }

    @FXML
    private void setOriginByMouse(MouseEvent m) { mainSceneController.setOrigin(m);}

    @FXML
    private void setLoc1(ActionEvent e) { mainSceneController.setOrigin(); }

    @FXML
    private void setLoc2(ActionEvent e) { mainSceneController.setDestination(); }

    @FXML
    private void resetOrigin() { mainSceneController.refreshKiosk(); }

    @FXML
    private void findPath(ActionEvent e) throws IOException { mainSceneController.findPath(); }

    @FXML
    private void snapToNode(MouseEvent m) { mainSceneController.snapToNode(m); }

    @FXML
    private void floorL2() throws IOException, SQLException { mainSceneController.floorL2(); }

    @FXML
    private void floorL1() throws IOException, SQLException { mainSceneController.floorL1(); }

    @FXML
    private void floorG() throws IOException, SQLException { mainSceneController.floorG(); }

    @FXML
    private void floor1() throws IOException, SQLException { mainSceneController.floor1(); }

    @FXML
    private void floor2() throws IOException, SQLException { mainSceneController.floor2(); }

    @FXML
    private void floor3() throws IOException, SQLException { mainSceneController.floor3(); }

    @FXML
    private void streetView() { mainSceneController.streetView(); }

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
    private void floorDownRequest() throws IOException, SQLException {  }

    @FXML
    private void floorUpRequest() throws IOException, SQLException {  }

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

    @FXML
    private void floorL2StaffRequest() throws IOException, SQLException { staffRequestController.floorL2(); }

    @FXML
    private void floorL1StaffRequest() throws IOException, SQLException { staffRequestController.floorL1(); }

    @FXML
    private void floorGStaffRequest() throws IOException, SQLException { staffRequestController.floorG(); }

    @FXML
    private void floor1StaffRequest() throws IOException, SQLException { staffRequestController.floor1(); }

    @FXML
    private void floor2StaffRequest() throws IOException, SQLException { staffRequestController.floor2(); }

    @FXML
    private void floor3StaffRequest() throws IOException, SQLException { staffRequestController.floor3(); }


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
    @FXML
    public void deleteSpill() {adminRequestController.deleteSpill();}

    @FXML
    public void deleteAllSpills() {adminRequestController.deleteAllSpills();}

    @FXML
    public void deleteFood() {adminRequestController.deleteFood();}

    @FXML
    public void deleteAllFoods() {adminRequestController.deleteAllFoods();}

    @FXML
    public void deleteInterpeter() {adminRequestController.deleteInterpeter();}

    @FXML
    public void deleteAllInterpeter() {adminRequestController.deleteAllInterpeter();}




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
    private void floorDownMapEdit() throws IOException, SQLException {  }

    @FXML
    private void floorUpMapEdit() throws IOException, SQLException {  }

    @FXML
    private void clickOnMapEdit(MouseEvent m) { adminMapController.clickOnMap(m); }

    @FXML
    private void setDefaultNode() { adminMapController.setKioskLocation(); }


    @FXML
    private void resetDefaultNode() { adminMapController.resetKioskScene(); } //TODO



    @FXML
    private void exportNodes() { adminMapController.exportNodes(); }

    @FXML
    private void exportEdges() { adminMapController.exportEdges(); }

    @FXML
    private void floorL2AdminMap() throws IOException, SQLException { adminMapController.floorL2(); }

    @FXML
    private void floorL1AdminMap() throws IOException, SQLException { adminMapController.floorL1(); }

    @FXML
    private void floorGAdminMap() throws IOException, SQLException { adminMapController.floorG(); }

    @FXML
    private void floor1AdminMap() throws IOException, SQLException { adminMapController.floor1(); }

    @FXML
    private void floor2AdminMap() throws IOException, SQLException { adminMapController.floor2(); }

    @FXML
    private void floor3AdminMap() throws IOException, SQLException { adminMapController.floor3(); }

    @FXML
    private void adminMapZoom() { adminMapController.zoom(); }



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
    private void mainToLogin() throws IOException {
        sceneSwitcher.toLogin(this, mainPane);
        memento.stopListening();
        memento.setPane(loginPane);
        memento.startListening();
    }

    @FXML
    private void requestToHub() throws IOException {
        User u = staffRequestController.getUser();
        sceneSwitcher.toStaffRequestHub(this, requestPane);
        staffRequestHubController.setUser(u);
        memento.stopListening();
        memento.setPane(requestHubPane);
        memento.startListening();
        System.out.println("from requests to requests hub");
    }

    @FXML
    private void goToMainScene() throws IOException {
        sceneSwitcher.toMain(this, loginPane);
        memento.stopListening();
    }

    @FXML
    private void goToHub() throws IOException {
        if (loginAdminToggle.isSelected()) {
            if (userLoginController.authenticateAdmin(loginText.getText(), passwordText.getText())) {
                databaseGargoyle.setCurrentUser(userManager.getUserByName(loginText.getText()));
                adminLogManager.addAdminLog(new AdminLog(databaseGargoyle.getCurrentUser().getUserID(), "Successfully logged in as " + databaseGargoyle.getCurrentUser().getUsername(), LocalDateTime.now()));
                sceneSwitcher.toAdminHub(this, loginPane);
                adminLogController.initializeScene(userManager.getUserByName(loginText.getText()));

                memento.stopListening();
                memento.setPane(adminHubPane);
                memento.startListening();
                System.out.println("from login to admin hub");
            } else errorController.showError("Invalid credentials! Please try again.");
        } else {
            if (userLoginController.authenticateStaff(loginText.getText(), passwordText.getText())) {
                sceneSwitcher.toStaffRequestHub(this, loginPane);
                staffRequestHubController.setUser(userManager.getUserByName(loginText.getText()));

                memento.stopListening();
                memento.setPane(requestHubPane);
                memento.startListening();
                System.out.println("from login to staff request hub");
            } else errorController.showError("Invalid credentials! Please try again.");

        }
    }

    //where does this go?
    @FXML
    private void serviceHubToRequest() throws IOException {
        sceneSwitcher.toStaffRequests(this, requestPane);
        System.out.println(staffRequestHubController.getUser());
        staffRequestController.initializeScene(userManager.getUser(staffRequestHubController.getUser().getUserID()));

        memento.stopListening();
        memento.setPane(requestPane);
        memento.startListening();
        System.out.println("from service hub to requests");
    }

    @FXML
    private void adminHubToMain() throws IOException {
        sceneSwitcher.toMain(this, adminHubPane);
        memento.stopListening();
    }

    @FXML
    private void adminHubtoLog() throws IOException {
        sceneSwitcher.toAdminLog(this, adminHubPane);
        adminLogController.initializeScene(userManager.getUserByName(loginText.getText()));

        memento.stopListening();
        memento.setPane(adminLogPane);
        memento.startListening();
        System.out.println("from admin hub to admin log");
    }

    @FXML
    private void adminHubtoRequest() throws IOException { //TODO this scene needs help
         sceneSwitcher.toAdminRequests(this, adminHubPane);
         adminRequestController.initializeScene();


        memento.stopListening();
        memento.setPane(adminRequestPane);
        memento.startListening();
        System.out.println("from admin hub to admin requests");
    }

    @FXML
    private void adminHubtoEmployee() throws IOException {
        sceneSwitcher.toAdminEmployee(this, adminHubPane);
        adminEmployeeController.initializeScene();

        memento.stopListening();
        memento.setPane(adminEmployeePane);
        memento.startListening();
        System.out.println("from admin hub to employees");
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

        //what pane is this? Should we even have it here?
        memento.stopListening();
        memento.setPane(adminMapPane);
        memento.startListening();
        System.out.println("from hub to admin map");
    }

    @FXML
    private void requestToAdminHub() throws IOException {
        sceneSwitcher.toAdminHub(this, adminRequestPane);

        memento.stopListening();
        memento.setPane(adminHubPane);
        memento.startListening();
        System.out.println("from admin request to admin hub");
    }

    @FXML
    private void mapToAdminHub() throws IOException {
        sceneSwitcher.toAdminHub(this, adminMapPane);

        memento.stopListening();
        memento.setPane(adminHubPane);
        memento.startListening();
        System.out.println("from map to admin hub");
    }

    @FXML
    private void logToAdminHub() throws IOException {
        sceneSwitcher.toAdminHub(this, adminLogPane);

        memento.stopListening();
        memento.setPane(adminHubPane);
        memento.startListening();
        System.out.println("from admin log to admin hub");
    }

    @FXML
    private void employeeToAdminHub() throws IOException {
        sceneSwitcher.toAdminHub(this, adminEmployeePane);

        memento.stopListening();
        memento.setPane(adminHubPane);
        memento.startListening();
        System.out.println("from employees to admin hub");
    }

    @FXML
    private void toSettingsPopUp() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/boundary/fxml/adminSettings.fxml"));
        fxmlLoader.setController(adminSettingsPopUpController);
        Parent root2 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Admin Settings");
        stage.setScene(new Scene(root2, 600, 800));
        stage.show();
    }

    @FXML
    private void toAboutPopUp() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/boundary/fxml/aboutPopUp.fxml"));
        fxmlLoader.setController(new AboutPopUpController());
        Parent root2 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("About");
        stage.setScene(new Scene(root2, 1000, 1000));
        stage.show();
    }


    @FXML
    private void toNewAboutPopUp() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/boundary/fxml/newAboutPage.fxml"));
        fxmlLoader.setController(new AboutPopUpController());
        Parent root2 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("About");
        stage.setScene(new Scene(root2, 1000, 1000));
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

    public MainSceneController getMainSceneController() {
        return mainSceneController;
    }
}