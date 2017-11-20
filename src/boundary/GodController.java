package boundary;

import boundary.sceneControllers.*;
import com.jfoenix.controls.JFXButton;
import controller.ClickController;
import controller.DirectoryController;
import controller.MapDisplayController;
import controller.PathController;
import entity.*;
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

    SceneSwitcher sceneSwitcher = new SceneSwitcher();
    /* Scene Controllers */
    MainSceneController mainSceneController = new MainSceneController();
    LoginController loginController = new LoginController();
    AdminEmployeeController adminEmployeeController = new AdminEmployeeController();
    AdminHubController adminHubController = new AdminHubController();
    AdminLogController adminLogController = new AdminLogController();
    AdminMapController adminMapController = new AdminMapController();
    StaffRequestController staffRequestController = new StaffRequestController();


    /* managers */
    final private NodeManager nodeManager = new NodeManager();
    final private EdgeManager edgeManager = new EdgeManager(nodeManager);
    final private RequestManager requestManager = new RequestManager(nodeManager);
    final private Astar aStar = new Astar(edgeManager);
    final private FileSelector fileSelector = new FileSelector();

    /////////////////
    /* controllers */
    /////////////////
    final private MapDisplayController mapDisplayController = new MapDisplayController();
    final private ClickController clickController = new ClickController(nodeManager);
    final private DirectoryController directoryController = new DirectoryController(nodeManager);
    final private PathController pathController = new PathController(aStar);

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

    /** Organize Functions by Scene **/

    @FXML
    private void initialize(){
        nodeManager.updateNodes();
        edgeManager.updateEdges();

        Image groundFloor = null;
        try {
            groundFloor = mapDisplayController.getMap("G");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        imageView.setImage(groundFloor);
        gc = canvas.getGraphicsContext2D();
        currentFloor = "G";
        currentFloorNum.setText(currentFloor);
        initializeDirectory();
        initializeDirectoryListeners();
    }

    private void initializeDirectory() {
        elevatorDir.setItems(directoryController.getDirectory().get("Elevators"));
        restroomDir.setItems(directoryController.getDirectory().get("Restrooms"));
        stairsDir.setItems(directoryController.getDirectory().get("Stairs"));
        labDir.setItems(directoryController.getDirectory().get("Departments"));
        deptDir.setItems(directoryController.getDirectory().get("Labs"));
        infoDeskDir.setItems(directoryController.getDirectory().get("Information Desks"));
        conferenceDir.setItems(directoryController.getDirectory().get("Conference Rooms"));
        exitDir.setItems(directoryController.getDirectory().get("Exits/Entrances"));
        shopsDir.setItems(directoryController.getDirectory().get("Shops, Food, Phones"));
        nonMedical.setItems(directoryController.getDirectory().get("Non-Medical Services"));
    }

    private void initializeDirectoryListeners(){
        elevatorDir.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            currentLoc = (Node) elevatorDir.getItems().get(newValue.intValue());
            clearCanvas();
            drawPath();
            drawCurrentNode();
        });
        restroomDir.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            currentLoc = (Node) restroomDir.getItems().get(newValue.intValue());
            clearCanvas();
            drawPath();
            drawCurrentNode();
        });
        stairsDir.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            currentLoc = (Node) stairsDir.getItems().get(newValue.intValue());
            clearCanvas();
            drawPath();
            drawCurrentNode();
        });
        labDir.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            currentLoc = (Node) labDir.getItems().get(newValue.intValue());
            clearCanvas();
            drawPath();
            drawCurrentNode();
        });
        deptDir.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            currentLoc = (Node) deptDir.getItems().get(newValue.intValue());
            clearCanvas();
            drawPath();
            drawCurrentNode();
        });
        infoDeskDir.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            currentLoc = (Node) infoDeskDir.getItems().get(newValue.intValue());
            clearCanvas();
            drawPath();
            drawCurrentNode();
        });
        conferenceDir.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            currentLoc = (Node) conferenceDir.getItems().get(newValue.intValue());
            clearCanvas();
            drawPath();
            drawCurrentNode();
        });
        exitDir.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            currentLoc = (Node) exitDir.getItems().get(newValue.intValue());
            clearCanvas();
            drawPath();
            drawCurrentNode();
        });
        nonMedical.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            currentLoc = (Node) nonMedical.getItems().get(newValue.intValue());
            clearCanvas();
            drawPath();
            drawCurrentNode();
        });
    }

    ////////////////////////////////////////////////////////////
    /* Main scene */

    @FXML
    private void mainToLogin() throws IOException {
        sceneSwitcher.switchScene(this, mainPane, loginLoc);
    }

    @FXML
    private void setLoc1(ActionEvent e) {
        loc1 = currentLoc;
        originField.setText(loc1.getShortName());
    }

    //sets loc2 to nearest node to click location
    @FXML
    private void setLoc2(ActionEvent e) {
        loc2 = currentLoc;
        destinationField.setText(loc2.getShortName());
    }

    // finds the path from loc1 to loc2
    @FXML
    private void findPath(ActionEvent e) {
        currentPath = pathController.findPath(loc1,loc2);
        clearCanvas();
        drawPath();
        drawCurrentNode();
    }

    //zooms in by 0.1 on click of zoom in button
    @FXML
    private void zoomInMap(MouseEvent e) {

        if (mapPane.getScaleX() >= 0.8 || mapPane.getScaleY() >= 0.8) return;
        mapPane.setScaleX(mapPane.getScaleX() + 0.1);
        mapPane.setScaleY(mapPane.getScaleY() + 0.1);
    }

    //zooms out by 0.1 on click of zoom out button
    @FXML //TODO fix
    private void zoomOutMap(MouseEvent e) {
        if (mapPane.getScaleX() <= 0.5 || mapPane.getScaleY() <= 0.5) return;
        mapPane.setScaleX(mapPane.getScaleX() - 0.1);
        mapPane.setScaleY(mapPane.getScaleY() - 0.1);
    }

    private void snapToNode(MouseEvent m) {
        int x = (int) m.getX();
        int y = (int) m.getY();
        currentLoc = clickController.getNearestNode(x,y,currentFloor);
        clearCanvas();
        drawCurrentNode();
        drawPath();
    }

    private void drawPath() {
        List<Node> pathToDraw = currentPath;

        if(pathToDraw == null || pathToDraw.size() == 0||!pathToDraw.get(0).getFloor().equals(currentFloor)){
            return;
        }

        /** Testing Only **
         ArrayList<Node> pathToDraw = new ArrayList<>(); //TODO this list is for testing
         pathToDraw.add(new Node("a",10, 10, "a","a","a","a","a",true));
         pathToDraw.add(new Node("b",300, 300, "a","a","a","a","a",true));
         pathToDraw.add(new Node("c",2000, 300, "a","a","a","a","a",true));
         /** testing over **/

        for(int i=0;i<pathToDraw.size()-1;i++) {
            int x1 = pathToDraw.get(i).getXcoord();
            int y1 = pathToDraw.get(i).getYcoord();
            int x2 = pathToDraw.get(i+1).getXcoord();
            int y2 = pathToDraw.get(i+1).getYcoord();
            gc.setLineWidth(5);
            gc.strokeLine(x1,y1,x2,y2);
        }
    }

    private void drawCurrentNode(){
        Node toDraw = currentLoc;
        if(toDraw == null || !toDraw.getFloor().equals(currentFloor)){
            return;
        }
        gc.fillOval(toDraw.getXcoord()-10,toDraw.getYcoord()-10,20,20);
    }

    @FXML
    private void drawEdge(Edge edge){

        Node startNode = edge.getStartNode();
        int sx = startNode.getXcoord();
        int sy = startNode.getYcoord();
        Node endNode = edge.getEndNode();
        int ex = endNode.getXcoord();
        int ey = endNode.getYcoord();

        gc.setLineWidth(3);
        gc.strokeLine(sx,sy,ex,ey);
    }

    @FXML
    private void drawNode(Node n) {
        gc.setFill(Color.BLUE);
        gc.fillOval(n.getXcoord() - 10, n.getYcoord() - 10, 20, 20);
        gc.setFill(Color.BLACK);
    }

    @FXML
    private void clearCanvas(){
        gc.clearRect(0,0,canvas.getWidth(),canvas.getHeight());
    }

    @FXML
    private void bathroomClicked(ActionEvent e){
        findNearest(currentLoc, "REST");
    }
    @FXML
    private void infoClicked(ActionEvent e){
        findNearest(currentLoc, "INFO");
    }
    @FXML
    private void elevatorClicked(ActionEvent e){
        findNearest(currentLoc, "ELEV");
    }

    private void findNearest(Node node, String type){

    }

    @FXML
    private void floorDown(MouseEvent e) throws IOException, SQLException {
        switch(currentFloor) {
            case "L2" :
                return;
            case "L1" :
                imageView.setImage(mapDisplayController.getMap("L2"));
                currentFloor = "L2";
                currentFloorNum.setText(currentFloor);
                break;
            case "G" :
                imageView.setImage(mapDisplayController.getMap("L1"));
                currentFloor = "L1";
                currentFloorNum.setText(currentFloor);
                break;
            case "1" :
                imageView.setImage(mapDisplayController.getMap("G"));
                currentFloor = "G";
                currentFloorNum.setText(currentFloor);
                break;
            case "2" :
                imageView.setImage(mapDisplayController.getMap("1"));
                currentFloor = "1";
                currentFloorNum.setText(currentFloor);
                break;
            case "3" :
                imageView.setImage(mapDisplayController.getMap("2"));
                currentFloor = "2";
                currentFloorNum.setText(currentFloor);
                break;
        }
        clearCanvas();
        drawPath();
        drawCurrentNode();
    }

    @FXML
    private void floorUp(MouseEvent e) throws IOException, SQLException {
        switch (currentFloor) {
            case "L2":
                imageView.setImage(mapDisplayController.getMap("L1"));
                currentFloor = "L1";
                currentFloorNum.setText(currentFloor);
                break;
            case "L1":
                imageView.setImage(mapDisplayController.getMap("G"));
                currentFloor = "G";
                currentFloorNum.setText(currentFloor);
                break;
            case "G":
                imageView.setImage(mapDisplayController.getMap("1"));
                currentFloor = "1";
                currentFloorNum.setText(currentFloor);
                break;
            case "1":
                imageView.setImage(mapDisplayController.getMap("2"));
                currentFloor = "2";
                currentFloorNum.setText(currentFloor);
                break;
            case "2":
                imageView.setImage(mapDisplayController.getMap("3"));
                currentFloor = "3";
                currentFloorNum.setText(currentFloor);
                break;
        }
        clearCanvas();
        drawPath();
        drawCurrentNode();
    }

    @FXML
    private void clickOnMap(MouseEvent m) {
        //snapToNode(m);
    }

    ////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////

    /* Scene Switching */

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