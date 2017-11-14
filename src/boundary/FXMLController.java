package boundary;

import entity.Node;
import controller.*;
import entity.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class FXMLController {
    /* managers */
    final private NodeManager nodeManager = new NodeManager();
    final private EdgeManager edgeManager = new EdgeManager(nodeManager);
    final private RequestManager requestManager = new RequestManager(nodeManager);

//    final private Astar aStar = new Astar(edgeManager);

    //    /* controllers */
    final private MapManager mapManager = new MapManager();
    final private MapDisplayController mapDisplayController = new MapDisplayController(); //new MapDisplayController(mapManager);
    final private MapEditController mapEditController = new MapEditController(edgeManager, nodeManager, mapManager);
    final private ClickController clickController = new ClickController(nodeManager);
    final private DirectoryController directoryController = new DirectoryController(nodeManager);
//    final private PathController pathController = new PathController(aStar);
//    final private RequestController requestController = new RequestController(requestManager, nodeManager);
//    final private NearestPOIController nearestPOIController = new NearestPOController(nodeManager);


    private Node loc1;
    private Node loc2;
    private Node currentLoc;
    private Image currentMap; // TODO
    private int time;
    private HashMap<String, ArrayList<Node>> directory;
    private String currentFloor;
    private List<Node> currentPath;

    @FXML
    private Pane mapPane;

    @FXML
    private Label currentFloorNum;

    @FXML
    private ImageView imageView;

    @FXML
    private Canvas canvas;

    GraphicsContext gc;

    @FXML
    private ListView elevatorDir, restroomDir, stairsDir, deptDir, labDir, infoDeskDir, conferenceDir, exitDir, shopsDir, nonMedical;

    @FXML
    private void initialize(){
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
        initializeDirectory();
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

    @FXML
    private void setLoc1(MouseEvent m) {
        // TODO
    }

    @FXML
    private void setLoc2(MouseEvent m) {
        // TODO
    }

    // finds the path from loc1 to loc2
    @FXML
    private void findPath(ActionEvent e) {
        // TODO: Add a PathController object at the top of this class, call find path in there
        //PathController.findPath(loc1, loc2);
    }

    // finds the path from
    private void findNearest(ActionEvent e) {
        // low priority
    }

    private void retrieveMapImage(ActionEvent e) {
        // TODO: Add a MapDisplayController object at the top of this class, call find path in there
        //MapDisplayController.getMap(currentFloor);
    }

    @FXML
    private void zoomInMap(MouseEvent e) {
        mapPane.setScaleX(mapPane.getScaleX() + 0.1);
        mapPane.setScaleY(mapPane.getScaleY() + 0.1);
    }

    @FXML //TODO fix
    private void zoomOutMap(MouseEvent e) {
        if (mapPane.getScaleX() <= 1 || mapPane.getScaleY() <= 1) return;
        mapPane.setScaleX(mapPane.getScaleX() - 0.1);
        mapPane.setScaleY(mapPane.getScaleY() - 0.1);
    }

    private void placeNode(ActionEvent e) {
        // TODO: get all the node information out of the UI and give the node ot the map edit controller
        // this should be in the pop-up on the Map Editor page
        String nodeID;
        int xcoord;
        int ycoord;
        String floor;
        String building;
        String nodeType;
        String longName;
        String shortName;
        boolean visitable;
        //Node n = new Node(nodeID, xcoord, ycoord, floor, building, nodeType, longName, shortName, visitable);
    }

    private void snapToNode(MouseEvent m) {
        int x = (int) m.getX();
        int y = (int) m.getY();
        loc2 = nodeManager.nearestNode(x,y); //TODO make sure this makes sense, snapToNode setting loc2
    }

    private void addNewMap(ActionEvent e) {
        // mapEditController
    }

    private void editAnExistingMap(ActionEvent e) {

    }

    // map editing mode
    private void addNodes(ActionEvent e) {

    }

    private void sendRequest(ActionEvent e) {

    }

    private void displayRequests(ActionEvent e) {
        // requestController.
    }

    private void login(ActionEvent e) {
        // Empty for now
    }


    @FXML
    private void drawPath(ActionEvent e) {
       // ArrayList<Node> pathToDraw = pathController.getPath(loc1, loc2);

        /** Testing Only **/
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
            gc.setLineWidth(25);
            gc.strokeLine(x1,y1,x2,y2);
        }
    }

    private void drawRequests(ActionEvent e) {

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
        }
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
                return;
        }
    }
}