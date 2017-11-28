package boundary;


import Admin.UserLoginController;
import Database.*;
import Editor.EdgeEditController;
import Editor.NodeEditController;
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
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;

import java.io.IOException;
import java.sql.SQLException;

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
    final private UserLoginController userLoginController = new UserLoginController(new UserManager());
    final private UserManager userManager = new UserManager();
    final private RequestCleanupController requestCleanupController = new RequestCleanupController(new CleanUpManager(nodeManager, userManager));

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

    /* Request Scene */
    @FXML
    private JFXTextField selectedRequestTextField;

    @FXML
    private ChoiceBox requestChoiceBox;

    @FXML
    private StackPane requestStack;

    @FXML
    private AnchorPane requestAnchor1, requestAnchor2, requestAnchor3;

    /* MAP ADMIN FXML */
    @FXML
    private Tab addNode, editNode, removeNode, edgesTab, setKioskTab, addEdge, removeEdge;

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

    @FXML
    private JFXTabPane edgeTab, kioskTab, addNodeTab, editNodeTab, removeNodeTab, addEdgeTab, removeEdgeTab;

    /* Admin Logs */

    @FXML
    private TreeTableView<Log> adminLogs = new TreeTableView<Log>();

    @FXML
    private TreeTableColumn<Log,Number> logID =  new TreeTableColumn<Log,Number>();

    @FXML
    private TreeTableColumn<Log,String> dateLogged = new TreeTableColumn<Log,String>();

    @FXML
    private TreeTableColumn<Log,String> adminLogged = new TreeTableColumn<Log,String>();

    @FXML
    private TreeTableColumn<Log,String> logContent = new TreeTableColumn<Log,String>();

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
    private JFXTextField employeeUserIDAE, employeeUserNameAE, employeePasswordAE;

    @FXML
    private JFXComboBox employeeTypeAE;
    TreeItem<Log> log1 = new TreeItem<>(new Log(1,"11/27/2017","admin1","added Node"));
    TreeItem<Log> log2 = new TreeItem<>(new Log(2,"11/27/2017","admin1","logged in"));
    TreeItem<Log> log3 = new TreeItem<>(new Log(3,"11/27/2017","admin1","added Node"));
    TreeItem<Log> log4 = new TreeItem<>(new Log(4,"11/27/2017","admin1","added Node"));

    TreeItem<Log> logRoot = new TreeItem<>(new Log(0,"11/27/2017","admin1","root"));



    SceneSwitcher sceneSwitcher = new SceneSwitcher();



    /* Scene Commandments */
    MainSceneController mainSceneController;
    AdminEmployeeController adminEmployeeController;
    AdminLogController adminLogController;
    AdminMapController adminMapController;
    StaffRequestController staffRequestController;
    AdminRequestController adminRequestController;

    @FXML
    private void initialize() {
        nodeManager.updateNodes();
        edgeManager.updateEdges();
        pathFindingFacade.setPathfinder(astar);
        initializeMainScene();
        initializeRequestScene();
        initializeMapAdminScene();
        initializeAdminRequestScene();
        initializeAdminEmployeeScene();
        initializeAdminLogScene();
    }

    private void initializeMainScene() {
        mainSceneController = new MainSceneController(imageView, mapPane, canvas,
                mapNavigationFacade, pathFindingFacade, currentFloorNum, elevatorDir,
                restroomDir, stairsDir, deptDir, labDir, infoDeskDir, conferenceDir, exitDir, shopsDir, nonMedical,
                originField, destinationField);
        mainSceneController.initializeScene();
    }

    private void initializeRequestScene() {
        staffRequestController = new StaffRequestController(requestAnchor1, requestAnchor2, requestAnchor3,
                requestStack, requestChoiceBox, requestImageView, requestMapPane, requestCanvas,
                mapNavigationFacade, pathFindingFacade, currentFloorNumRequest, requestCleanupController,
                allStaffRequests, requestsIMade, selectedRequestTextField);
    }

    private void initializeMapAdminScene() {
        adminMapController = new AdminMapController(nodeManager, nodeEditController, edgeEditController,
                mapEditImageView, mapEditMapPane, mapEditCanvas, mapNavigationFacade, pathFindingFacade,
                currentFloorNumMapEdit, addNode, editNode, removeNode);
    }

    private void initializeAdminLogScene() {
        logRoot.getChildren().setAll(log1,log2,log3,log4);

        logID.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<Log,Number> param) -> param.getValue().getValue().getLogIDProperty());
        dateLogged.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<Log,String> param) -> param.getValue().getValue().getDateLoggedProperty());
        adminLogged.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<Log,String> param) -> param.getValue().getValue().getAdminLoggedProperty());
        logContent.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<Log,String> param) -> param.getValue().getValue().getLogContentProperty());

        adminLogs.setRoot(logRoot);
        adminLogs.setShowRoot(false);
    }

    private void initializeAdminRequestScene(){ adminRequestController = new AdminRequestController(); }


    private void initializeAdminEmployeeScene() { adminEmployeeController = new AdminEmployeeController(userManager,
            employeeListAE, employeeUserIDAE, employeeUserNameAE, employeePasswordAE, employeeTypeAE);
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

    @FXML
    private void setAsOrigin() {mainSceneController.setAsOrigin();}

    ///////////////////
    /* Request Scene */
    ///////////////////

    @FXML
    private void navigateToRequest() { staffRequestController.navigateToRequest(); } //TODO

    @FXML
    private void addStaffRequest() { staffRequestController.addRequest(requestName, requestDescription); }

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

    ////////////////////
    /* Employee Admin */
    ////////////////////

   @FXML
   private void addEmployeeAE() {adminEmployeeController.addEmployeeAE();}

   @FXML
   private void cancelEmployeeAE() {adminEmployeeController.cancelEmployeeAE();}

    @FXML
    private void editEmployeeAE() {adminEmployeeController.editEmployeeAE();}

    @FXML
    private void deleteEmployeeAE() {adminEmployeeController.deleteEmployeeAE();}

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
    private void setDefaultNode() { } //TODO

    @FXML
    private void resetDefaultNode() { } //TODO

    ////////////////
    /* Admin Logs */
    ////////////////

    //TODO

    @FXML
    public void printLogButton(){}

    //TODO
    @FXML
    public void sendLogButton(){}

    //TODO
    @FXML
    public void clearLogButton() throws IOException{
        logRoot.getChildren().clear();
    }

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
            staffRequestController.initializeScene(userManager.getUserByName(staffLoginText.getText()));
        }
        //TODO error screen
    }

    @FXML
    private void goToAdminHub() throws IOException {
        if (userLoginController.authenticateAdmin(adminLoginText.getText(), adminPasswordText.getText())) {
            sceneSwitcher.toAdminHub(this, loginPane);
        }
        //TODO Error screen
    }

    @FXML
    private void adminHubToMain() throws IOException { sceneSwitcher.toMain(this, adminHubPane); }

    @FXML
    private void adminHubtoLog() throws IOException { sceneSwitcher.toAdminLog(this, adminHubPane); }

    @FXML
    private void adminHubtoRequest() throws IOException {
        sceneSwitcher.toAdminRequests(this, adminHubPane);
        adminRequestController.initializeScene();
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
        adminMapController.initializeNodeAdder(nodeManager, xPosAddNode, yPosAddNode, nodeTypeCombo, buildingCombo, shortNameAdd, longNameAdd);
        adminMapController.initializeNodeEditor(editNodeID, xPosEdit, yPosEdit, nodeTypeComboEdit, shortNameEdit, longNameEdit, editNodeTypeField);
        adminMapController.initializeNodeRemover(xPosRemoveNode, yPosRemoveNode);
        adminMapController.initializeEdgeAdder(edgeManager, edgeXStartAdd, edgeYStartAdd, edgeXEndAdd, edgeYEndAdd);
    }

    @FXML
    private void requestToAdminHub() throws IOException { sceneSwitcher.toAdminHub(this, adminRequestPane); }

    @FXML
    private void mapToAdminHub() throws IOException { sceneSwitcher.toAdminHub(this, adminMapPane); }

    @FXML
    private void logToAdminHub() throws IOException { sceneSwitcher.toAdminHub(this, adminLogPane); }

    @FXML
    private void employeeToAdminHub() throws IOException { sceneSwitcher.toAdminHub(this, adminEmployeePane); }
}