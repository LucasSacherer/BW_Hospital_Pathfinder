package boundary.sceneControllers;

import Entity.Node;
import MapNavigation.MapNavigationFacade;
import Pathfinding.PathFindingFacade;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public abstract class AbstractMapController {
    protected Label currentFloorNum;
    protected Canvas canvas;
    protected String currentFloor = "G";
    protected GraphicsContext gc;
    protected Pane mapPane;
    protected List currentPath;
    protected MapNavigationFacade mapNavigationFacade;
    protected PathFindingFacade pathFindingFacade;
    protected ImageView imageView;

    protected Node origin, destination, currentLoc;

    public AbstractMapController(ImageView i, Pane mapPane, Canvas canvas, MapNavigationFacade m, PathFindingFacade p, Label currentFloorNum) {
        this.imageView = i;
        this.mapNavigationFacade = m;
        this.pathFindingFacade = p;
        this.canvas = canvas;
        this.mapPane = mapPane;
        this.currentFloorNum = currentFloorNum;
        currentFloor = "G";
        currentFloorNum.setText(currentFloor);
        // todo set origin:  this.origin = mapNavigationFacade.getDefaultNode(); //todo change the origin when the floor changes
    }

    public void initializeCanvas() {
        this.gc = canvas.getGraphicsContext2D();
    }

    public void clickOnMap(MouseEvent m) {
        if (origin == null ) {//TODO replace this code with real origin
            origin = mapNavigationFacade.getNearestNode((int) m.getX(), (int) m.getY(),currentFloor);
        }
        snapToNode(m);
    }

    public void snapToNode(MouseEvent m) {
        int x = (int) m.getX();
        int y = (int) m.getY();
        currentLoc = mapNavigationFacade.getNearestNode(x,y,currentFloor);
        refreshCanvas();
    }

    public void refreshCanvas() {
        clearCanvas();
        if (origin != null && origin.getFloor().equals(currentFloor)) drawNode(origin, Color.GREEN);
        if (destination != null && destination.getFloor().equals(currentFloor)) drawNode(destination, Color.RED);
        if (currentLoc != null && currentLoc.getFloor().equals(currentFloor)) drawCurrentNode();
        drawPath();
    }

    public void clearCanvas() { gc.clearRect(0,0,canvas.getWidth(),canvas.getHeight()); }

    public void drawNode(Node n, Color c) {
        if (n == null) return;
        gc.setFill(c);
        gc.fillOval(n.getXcoord() - 10, n.getYcoord() - 10, 20, 20);
    }

    public void drawCurrentNode() {
        if(currentLoc == null || !currentLoc.getFloor().equals(currentFloor)){ return; }
        gc.setFill(Color.BLUE);
        gc.fillOval(currentLoc.getXcoord()-5,currentLoc.getYcoord()-5,10,10);
    }

    public void setOrigin(TextField originField) {
        currentPath = null;
        origin = currentLoc;
        System.out.println(origin);
        originField.setText(origin.getNodeID());
        refreshCanvas();
    }

    public void setDestination(TextField destinationField) {
        currentPath = null;
        destination = currentLoc;
        destinationField.setText(destination.getNodeID());
        refreshCanvas();
    }

    public void findPath() {
        currentPath = pathFindingFacade.getPath(origin, destination);
        refreshCanvas();
    }

    public void zoomInMap() {
        if (mapPane.getScaleX() >= 0.8 || mapPane.getScaleY() >= 0.8) return;
        mapPane.setScaleX(mapPane.getScaleX() + 0.1);
        mapPane.setScaleY(mapPane.getScaleY() + 0.1);
    }


    public void zoomOutMap() {
        if (mapPane.getScaleX() <= 0.5 || mapPane.getScaleY() <= 0.5) return;
        mapPane.setScaleX(mapPane.getScaleX() - 0.1);
        mapPane.setScaleY(mapPane.getScaleY() - 0.1);
    }


    public void drawPath() {
        List<Node> pathToDraw = currentPath;
        if(pathToDraw == null || pathToDraw.size() == 0){ return; }

        /** Testing Only **
         ArrayList<Node> pathToDraw = new ArrayList<>(); //TODO this list is for testing
         pathToDraw.add(new Node("a",10, 10, "a","a","a","a","a",true));
         pathToDraw.add(new Node("b",300, 300, "a","a","a","a","a",true));
         pathToDraw.add(new Node("c",2000, 300, "a","a","a","a","a",true));
         /** testing over **/

        for(int i=0;i<pathToDraw.size()-1;i++) {
            if (pathToDraw.get(i).getFloor().equals(currentFloor) && pathToDraw.get(i+1).getFloor().equals(currentFloor)) {
                int x1 = pathToDraw.get(i).getXcoord();
                int y1 = pathToDraw.get(i).getYcoord();
                int x2 = pathToDraw.get(i + 1).getXcoord();
                int y2 = pathToDraw.get(i + 1).getYcoord();
                gc.setLineWidth(3);
                gc.strokeLine(x1, y1, x2, y2);
            }
            else if (pathToDraw.get(i).getFloor().equals(currentFloor)) {

            }
            else if (pathToDraw.get(i+1).equals(currentFloor)) {

            }
        }
    }

    public void floorDown() throws IOException, SQLException {
        switch(currentFloor) {
            case "L2" :
                return;
            case "L1" :
                currentFloor = "L2";
                break;
            case "G" :
                currentFloor = "L1";
                break;
            case "1" :
                currentFloor = "G";
                break;
            case "2" :
                currentFloor = "1";
                break;
            case "3" :
                currentFloor = "2";
                break;
        }
        imageView.setImage(mapNavigationFacade.getFloorMap(currentFloor));
        currentFloorNum.setText(currentFloor);
        refreshCanvas();
    }

    public void floorUp() throws IOException, SQLException {
        switch (currentFloor) {
            case "3":
                return;
            case "L2":
                currentFloor = "L1";
                break;
            case "L1":
                currentFloor = "G";
                break;
            case "G":
                currentFloor = "1";
                break;
            case "1":
                currentFloor = "2";
                break;
            case "2":
                currentFloor = "3";
                break;
        }
        imageView.setImage(mapNavigationFacade.getFloorMap(currentFloor));
        currentFloorNum.setText(currentFloor);
        refreshCanvas();
    }
}