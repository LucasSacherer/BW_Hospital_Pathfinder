package boundary;

import controller.*;
import entity.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.control.ScrollPane;

public class FXMLController {
    /* managers */
    final private NodeManager nodeManager = new NodeManager();
    final private EdgeManager edgeManager = new EdgeManager(nodeManager);
    final private MapManager mapManager = new MapManager();
    final private RequestManager requestManager = new RequestManager();

//    final private Astar aStar = new Astar(edgeManager);
//
//    /* controllers */
    final private MapDisplayController mapDisplayController = new MapDisplayController(); //new MapDisplayController(mapManager);
//    final private MapEditController mapEditController = new MapEditController(nodeManager, edgeManager, mapManager);
//    final private ClickController clickController = new ClickController(nodeManager);
//    final private DirectoryController directoryController = new DirectoryController(nodeManager);
//    final private PathController pathController = new PathController(aStar);
//    final private RequestController requestController = new RequestController(requestManager, nodeManager);
//    // final private NearestPOIController nearestPOIController = new NearestPOController(nodeManager);
//

    private Node loc1;
    private Node loc2;
    private Node currentLoc;
    private Image currentMap; // TODO
    private int time;
    private HashMap<String, ArrayList<Node>> directory;
    private int currentFloor;
    private List<Node> currentPath;

    @FXML
    private Button navigate;

    @FXML
    private ImageView mapImageView;

    @FXML
    private ScrollPane imageScroll;

   @FXML
   private void initialize(){
       Image groundFloor = mapDisplayController.getMap("G");
       imageScroll.setContent(new ImageView(groundFloor));
   }

    private void setStartLocation(ActionEvent e) {
        // sets loc1
        // loc1
    }

    private void setEndLocation(ActionEvent e) {
         //sets loc2;
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

    private void zoomInMap(ActionEvent e) {
        mapImageView.setScaleX(mapImageView.getScaleX() + 0.1);
        mapImageView.setScaleY(mapImageView.getScaleY() + 0.1);
    }

    private void zoomOutMap(ActionEvent e) {
        if (mapImageView.getScaleX() <= 1 || mapImageView.getScaleY() <= 1) return;
        mapImageView.setScaleX(mapImageView.getScaleX() - 0.1);
        mapImageView.setScaleY(mapImageView.getScaleY() - 0.1);
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

    private void snapToNode(ActionEvent e) {

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

    private void drawPath(ActionEvent e) {

    }

    private void drawRequests(ActionEvent e) {

    }
}