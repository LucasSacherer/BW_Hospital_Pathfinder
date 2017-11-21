package Editor;

import Database.EdgeManager;
import Database.ImageManager;
import Database.NodeManager;
import Entity.*;
import Iteration1CodeWeMayNotNeed.MapManager;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.List;

public class MapEditController {

    //EdgeManager edgeManager;
    //NodeManager nodeManager;
    MapManager mapManager;
    ImageManager imgManager;

    public MapEditController(MapManager mapManager, ImageManager imgManager) {
        //this.edgeManager = edgeManager;
        //this.nodeManager = nodeManager;
        this.mapManager = mapManager;
    }

    // returns the list of all nodes
    // public List<Node> getAllNodes() {

    // returns the list of all edges
    // public List<Edge> getAllEdges() {

    public void uploadMap(String image, String floor) {
        //get the file from the image path

        //File imagefile =;
        Image imagefile = new ImageIcon(this.getClass().getResource(image)).getImage();
        //imgManager.setImage(floor, imagefile);
    }

    // adds a new node to the lists of all nodes
    // public void addNode(Node node) {

    // update an already existing node
    // public void editNode(Node node) {

    // deletes an already existing node
    // public void deleteNode(Node node) {

    // adds a new Edge
    // public void addEdge(Edge edge) {

    // deletes an already existing edge
    //public void deleteEdge(Edge edge) {
}
