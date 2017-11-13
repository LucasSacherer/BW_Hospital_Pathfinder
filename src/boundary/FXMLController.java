package boundary;

import controller.*;

import controller.PathController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import entity.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import controller.MapDisplayController;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class FXMLController {
    private final NodeManager;

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


    public FXMLController() {
        //TODO all of the managers and controllers will be created here
    }
   final private MapDisplayController newMap = new MapDisplayController();

   @FXML
    private ScrollPane imageScroll;

   @FXML
   private void initialize(){
       Image groundFloor= newMap.getMap("G");
       imageScroll.setContent(new ImageView(groundFloor));
   }

   public FXMLController() {

   }
}

    private void setStartLocation(ActionEvent e) {
        // sets loc1
        // loc1
    }

    private void setEndLocation(ActionEvent e) {
         //sets loc2;
    }

    // finds the path from loc1 to loc2
    private void findPath(ActionEvent e) {
        PathController.findPath(loc1, loc2);
    }

    // finds the path from
    private void findNearest(ActionEvent e) {
       // low priority
    }

    private void retrieveMapImage(ActionEvent e) {
        MapDisplayController.getMap(currentFloor);
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
        String nodeID;
        int xcoord;
        int ycoord;
        String floor;
        String building;
        String nodeType;
        String longName;
        String shortName;
        boolean visitable;
        Node n = new Node(nodeID, xcoord, ycoord, floor, building, nodeType, longName, shortName, visitable);
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

    }

    private void login(ActionEvent e) {
        // Empty for now
    }

    private void drawPath(ActionEvent e) {

    }

    private void drawRequests(ActionEvent e) {

    }
}