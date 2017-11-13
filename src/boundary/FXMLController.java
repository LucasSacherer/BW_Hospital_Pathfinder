package boundary;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import entity.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javafx.scene.image.Image;

import javax.swing.*;

public class FXMLController {
    private Node loc1;
    private Node loc2;
    private Node currentLoc;
    private Image currentMap; // TODO
    private int time;
    private HashMap<String, ArrayList<Node>> directory;
    private int currentFloor;
    private List<Node> currentPath;

    private void getStartLocation(ActionEvent e) {
        return loc1;
    }

    private void getEndLocation(ActionEvent e) {
        return loc2;
    }

    // finds the path from loc1 to loc2
    private void findPath(ActionEvent e) {
        //TODO draws a path from loc1 to loc2
        // lineTO from sceneBuilder
    }

    // finds the path from
    private void findNearest(ActionEvent e) {
        //TODO calls NearestPOIController
    }

    private void retrieveMapImage(ActionEvent e) {
        //TODO calls MapDisplayController
    }

    private void scaleMap(ActionEvent e) {

    }

    private void placeNode(ActionEvent e) {

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