package boundary;

import entity.Node;
import entity.Edge;
import controller.*;
import entity.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import java.time.LocalDateTime;

import javafx.scene.paint.Color;

import static java.time.LocalDateTime.now;

public class FXMLController {
    /* managers */
    final private NodeManager nodeManager = new NodeManager();
    final private EdgeManager edgeManager = new EdgeManager(nodeManager);
    final private RequestManager requestManager = new RequestManager(nodeManager);
    final private Astar aStar = new Astar(edgeManager);

    //    /* controllers */
    final private MapManager mapManager = new MapManager();
    final private MapDisplayController mapDisplayController = new MapDisplayController(); //new MapDisplayController(mapManager);
    final private MapEditController mapEditController = new MapEditController(edgeManager, nodeManager, mapManager);
    final private ClickController clickController = new ClickController(nodeManager);
    final private DirectoryController directoryController = new DirectoryController(nodeManager);
    final private PathController pathController = new PathController(aStar);
    final private RequestController requestController = new RequestController(requestManager);
//    final private NearestPOIController nearestPOIController = new NearestPOController(nodeManager);


    private Node loc1;
    private Node loc2;
    private Node currentLoc;
    private Image currentMap; // TODO
    private int time, editX, editY;
    private Node edgeStart = null, edgeEnd = null;
    private HashMap<String, ArrayList<Node>> directory;
    private String currentFloor;
    private List<Node> currentPath;
    private int currentNodeID = 999;
    private Node nodeA, nodeB;

    @FXML
    private Pane mapPane;

    @FXML
    private Label currentFloorNum;

    @FXML
    private TextField originField, destinationField;

    @FXML
    private ImageView imageView;

    @FXML
    private Canvas canvas;

    @FXML
    private TextField newNodeName;

    @FXML
    GraphicsContext gc;

    @FXML
    private ListView elevatorDir, restroomDir, stairsDir, deptDir, labDir, infoDeskDir, conferenceDir, exitDir, shopsDir, nonMedical;

    @FXML
    private ListView requestList;

    @FXML
    private Button deleteButton;

    @FXML
    private Button addButton;

    @FXML
    private HBox addRequestBox;

    @FXML
    private TextField requestName;

    @FXML
    private TextField requestType;

    @FXML
    private TextField requestDescription;



    @FXML
    private ToggleButton nodeTool, edgeTool, selectorTool;

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
        requestManager.updateRequests();
        requestList.setItems(requestController.getRequests());
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
        requestList.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            currentLoc = ((Request)requestList.getItems().get(newValue.intValue())).getNode();
            clearCanvas();
            drawPath();
            drawCurrentNode();
        });
    }

    @FXML
    private void deleteRequestButton(ActionEvent e){
        requestController.deleteRequest((Request)requestList.getSelectionModel().getSelectedItem());
        currentLoc = null;
        clearCanvas();
    }

    @FXML
    private void addRequestButton(ActionEvent e){
        Request a = new Request(requestType.getText(),requestName.getText(),requestDescription.getText(),currentLoc, LocalDateTime.now());
        requestController.addRequest(a);
        requestList.setItems(requestController.getRequests());
        requestDescription.setText("");
        requestName.setText("");
        requestType.setText("");

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

    // finds the path from currentLoc to nearest requested node type
    private void findNearest(ActionEvent e) {

        // TODO
        // low priority
    }


    private void retrieveMapImage(ActionEvent e) {
        // TODO: Add a MapDisplayController object at the top of this class, call find path in there
        //MapDisplayController.getMap(currentFloor);
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

    //finds node nearest to clicked location and sets the nearest node as currentLoc
    // creates a new Node in the Map editor
    @FXML
    private void addNode(ActionEvent e) {
        if (currentLoc == null){
            return;
        }
        String longName = "Hallway" + " New Added Node " + currentNodeID + " Floor " + currentFloor;
        String shortName = "Added Node" + currentNodeID;
        String nodeID = "GHALL" + currentNodeID + currentFloor;
        currentNodeID--;

        Node n = new Node(nodeID, editX, editY, currentFloor, "Shapiro", "Hall", longName, shortName, true);
        mapEditController.addNode(n);
        drawAllNodes();
        drawAllEdges();
    }

    private void snapToNode(MouseEvent m) {
        int x = (int) m.getX();
        int y = (int) m.getY();
        currentLoc = clickController.getNearestNode(x,y,currentFloor);
        clearCanvas();
        drawCurrentNode();
        drawPath();
    }

    private void addNewMap(ActionEvent e) {
        //TODO
        // mapEditController
    }

    private void editAnExistingMap(ActionEvent e) {
    }

    private void sendRequest(ActionEvent e) {

    }

    private void displayRequests(ActionEvent e) {
        // requestController.
    }

    private void login(ActionEvent e) {
        // Empty for now
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

        gc.setLineWidth(5);
        gc.strokeLine(sx,sy,ex,ey);
    }


    @FXML
    private void drawNode(Node n) {
        gc.setFill(Color.BLUE);
        gc.fillOval(n.getXcoord(), n.getYcoord(), 10, 10);
        gc.setFill(Color.BLACK);
    }

    @FXML
    private void drawAllNodes() {
        for (Node n : mapEditController.getAllNodes()) {
            if (n.getFloor().equals(currentFloor)) {
                drawNode(n);
            }
        }
    }

    @FXML
    private void drawAllEdges(){
        for (Edge e : mapEditController.getAllEdges()){
            if(e.getStartNode().getFloor().equals(currentFloor)){
                drawEdge(e);
            }
        }
    }
    @FXML
    private void enterMapEditing() {
        currentLoc = null;
        drawAllNodes();
        drawAllEdges();
    }

    @FXML
    private void clearCanvas(){
        gc.clearRect(0,0,canvas.getWidth(),canvas.getHeight());
    }

    private void drawRequests(ActionEvent e) {

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
        int x = node.getXcoord();
        int y = node.getYcoord();
        Node closest = nodeManager.nearestLoc(x,y,currentFloor,type);
        loc2 = closest;
        destinationField.setText(loc2.getShortName());
        drawNode(closest);
        currentPath = pathController.findPath(loc1,loc2);
        clearCanvas();
        drawPath();
        drawCurrentNode();

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
        // todo make sure that the tool gets un-set at the right times
        // node tool
        if (nodeTool.isSelected()) {
            clearCanvas();
            drawAllNodes();
            drawAllEdges();
            editX = (int) m.getX();
            editY = (int) m.getY();
            // draw node on map
            gc.fillOval(editX, editY, 10, 10);
        }
        // edge controller //TODO
        else if (edgeTool.isSelected()) {
            clearCanvas();
            drawAllNodes();
            drawAllEdges();
            if (edgeStart == null) edgeStart = clickController.getNearestNode((int)m.getX(), (int)m.getY(), currentFloor);
            else {
                edgeEnd = clickController.getNearestNode((int) m.getX(), (int) m.getY(), currentFloor);
                Edge potential = new Edge(edgeStart, edgeEnd);
                drawEdge(potential);
            }
        }
        else {
            snapToNode(m);
        }
    }

    @FXML
    private void addEdge(ActionEvent e) {
        Edge edge = new Edge(edgeStart, edgeEnd);
        mapEditController.addEdge(edge);
        drawAllNodes();
        drawAllEdges();
    }

    @FXML
    private void clearEdge(ActionEvent e) {
        edgeStart = null;
        edgeEnd = null;
        clearCanvas();
        drawAllEdges();
        drawAllNodes();
    }
}