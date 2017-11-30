package boundary;


import Admin.UserLoginController;
import Database.*;
import Editor.EdgeEditController;
import Editor.NodeEditController;
import Entity.AdminLog;
import Entity.ErrorController;
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
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class GodController {

    /* managers */
    final private NodeManager nodeManager = new NodeManager();
    final private EdgeManager edgeManager = new EdgeManager(nodeManager);
    final private SettingsManager settingsManager = new SettingsManager();
    final private NodeEditController nodeEditController = new NodeEditController(nodeManager, settingsManager, edgeManager);
    final private EdgeEditController edgeEditController = new EdgeEditController(edgeManager);
    final private ClickController clickController = new ClickController(nodeManager);
    final private NearestPOIController nearestPOIController = new NearestPOIController(nodeManager);
    final private MapDisplayController mapDisplayController = new MapDisplayController();
    final private DirectoryController directoryController = new DirectoryController(nodeManager,settingsManager);
    final private MapNavigationFacade mapNavigationFacade = new MapNavigationFacade(
            clickController, nearestPOIController, mapDisplayController, directoryController);
    final private PathFindingFacade pathFindingFacade = new PathFindingFacade();
    final private Astar astar = new Astar(edgeManager);
    final private BeamSearch beam = new BeamSearch(edgeManager);
    final private BreadthSearch breadth = new BreadthSearch(edgeManager);
    final private DepthSearch depth = new DepthSearch(edgeManager);
    final private UserLoginController userLoginController = new UserLoginController(new UserManager());
    final private UserManager userManager = new UserManager();
    final private AdminLogManager adminLogManager = new AdminLogManager(userManager);
    final private RequestCleanupController requestCleanupController = new RequestCleanupController(new CleanUpManager(nodeManager, userManager));
    String currentUser;
    final private CleanUpManager cleanup = new CleanUpManager(nodeManager, userManager);
    final private InterpreterManager interpreter = new InterpreterManager(nodeManager, userManager);
    final private FoodManager food = new FoodManager();
    final private RequestInterpreterController requestInterpreterController = new RequestInterpreterController(interpreter);
    final private RequestFoodController requestFoodController = new RequestFoodController(food);
    final private GenericRequestController genericRequestController = new GenericRequestController(cleanup, food, interpreter);
    final private ErrorController errorController = new ErrorController();

    ///////////////////////
    /** FXML Attributes **/
    ///////////////////////

    /* Scene Panes */
    @FXML
    private Pane mainPane, loginPane, requestPane, adminHubPane, adminRequestPane, adminMapPane, adminEmployeePane, adminLogPane;

    /* Map Panes */
    @FXML
    private Pane mapEditMapPane, mapPane, requestMapPane;

    @FXML
    private StackPane menuARStackPane;

    @FXML
    private Canvas canvas, mapEditCanvas, requestCanvas;

    @FXML
    private JFXTextField originField, destinationField;

    @FXML
    private ImageView imageView, mapEditImageView, requestImageView;

    @FXML
    private ListView elevatorDir, restroomDir, stairsDir, deptDir, labDir, infoDeskDir, conferenceDir, exitDir, shopsDir, nonMedical;

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
    private Label mapEditText, nodeLocation1, nodeLocation2, nodeLocation3, currentFloorNum, currentFloorNumRequest, currentFloorNumMapEdit;

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



    SceneSwitcher sceneSwitcher = new SceneSwitcher();



    /* Scene Commandments */
    MainSceneController mainSceneController;
    AdminEmployeeController adminEmployeeController;
    AdminLogController adminLogController;
    AdminMapController adminMapController;
    StaffRequestController staffRequestController;
    AdminRequestController adminRequestController;
    RequestReportController requestReportController;


    boolean firstTime = true;
    @FXML
    private void initialize() {
        nodeManager.updateNodes();
        edgeManager.updateEdges();
        userManager.updateUsers();
        //pathFindingFacade.setPathfinder(beamSearch);
        if (firstTime){ pathFindingFacade.setPathfinder(astar);}
        initializeMainScene();
        initializeRequestScene();
        initializeRequestReportScene();
        initializeMapAdminScene();
        initializeAdminRequestScene();
        initializeAdminEmployeeScene();
        initializeAdminLogScene();
        firstTime = false;
    }

    private void initializeMainScene() {
        mainSceneController = new MainSceneController(imageView, mapPane, canvas,
                mapNavigationFacade, pathFindingFacade, currentFloorNum, elevatorDir,
                restroomDir, stairsDir, deptDir, labDir, infoDeskDir, conferenceDir, exitDir, shopsDir, nonMedical,
                originField, destinationField);
        mainSceneController.initializeScene();
    }

    private void initializeRequestScene() {
        staffRequestController = new StaffRequestController(requestImageView, requestMapPane, requestCanvas,
                mapNavigationFacade, pathFindingFacade, currentFloorNumRequest, genericRequestController, requestCleanupController,
                requestInterpreterController, requestFoodController, allStaffRequests, requestsIMade, requestNodeID,
                requestCleanupName, requestInterpreterName, requestFoodName, requestCleanupDescription, languageSelect,
                requestInterpreterDescription, requestFoodDescription, requestInfo, currentFoodOrder, foodItem);
    }

    private void initializeMapAdminScene() {
        adminMapController = new AdminMapController(edgeManager, nodeManager, nodeEditController, edgeEditController,
                mapEditImageView, mapEditMapPane, mapEditCanvas, mapNavigationFacade, pathFindingFacade,
                currentFloorNumMapEdit, addNode, editNode, removeNode, addEdge, removeEdge, kioskTab, edgesTab, nodesTab);
    }

    private void initializeAdminLogScene() {
        adminLogController = new AdminLogController (adminLogs, dateLogged,
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
            employeeListAE, employeeUserIDAE, employeeUsernameAE, employeePasswordAE, employeeTypeAE, adminToggle);
    }


    /** Organize Functions by Scene **/

    ////////////////
    /* Main scene */
    ////////////////

    @FXML
    private void setOriginByMouse(MouseEvent m) { mainSceneController.setOrigin(m);}


    @FXML
    private void setLoc1(ActionEvent e) { mainSceneController.setOrigin(); }

    @FXML
    private void setLoc2(ActionEvent e) { mainSceneController.setDestination(); }

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



    /////////////////////////
    /* Staff Request Scene */
    /////////////////////////

    @FXML
    private void navigateToRequest() { staffRequestController.navigateToRequest(); } //TODO

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


    ////////////////
    /* Admin Logs */
    ////////////////

    @FXML
    public void clearLogButton() throws IOException{ adminLogController.clearLogButton(); }

    @FXML
    public void exportLogs() {adminLogController.exportLogs(); }

    //TODO

    /////////////////////
    /* Scene Switching */
    /////////////////////
    @FXML
    private void mainToLogin() throws IOException { sceneSwitcher.toLogin(this, mainPane); }

    @FXML
    private void requestToMain() throws IOException { sceneSwitcher.toMain(this, requestPane); }

    @FXML
    private void goToMainScene() throws IOException { sceneSwitcher.toMain(this, loginPane); }

    @FXML
    private void goToRequests() throws IOException {

       if (userLoginController.authenticateStaff(staffLoginText.getText(), staffPasswordText.getText())){
            sceneSwitcher.toStaffRequests(this, loginPane);
            userManager.updateUsers();
            staffRequestController.initializeScene(userManager.getUserByName(staffLoginText.getText()));
        } else errorController.showError("Invalid credentials! Please try again.");
    }

    @FXML
    private void goToAdminHub() throws IOException {
        userManager.updateUsers();
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
    private void getTextDirections() {mainSceneController.displayTextDir();}
}