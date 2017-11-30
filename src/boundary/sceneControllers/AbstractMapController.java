package boundary.sceneControllers;

import Entity.Node;
import Entity.ErrorController;
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

import java.awt.*;
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
    protected ErrorController errorController = new ErrorController();

    public AbstractMapController(ImageView i, Pane mapPane, Canvas canvas, MapNavigationFacade m, PathFindingFacade p, Label currentFloorNum) {
        this.imageView = i;
        this.mapNavigationFacade = m;
        this.pathFindingFacade = p;
        this.canvas = canvas;
        this.mapPane = mapPane;
        this.currentFloorNum = currentFloorNum;
        currentFloor = "G";
    }

    public void initializeScene() {
        imageView.setImage(mapNavigationFacade.getFloorMap("G"));
        this.gc = canvas.getGraphicsContext2D();
        currentFloorNum.setText(currentFloor);
        this.origin = mapNavigationFacade.getDefaultNode(); //TODO doesn't work
        System.out.println(mapNavigationFacade.getDefaultNode());
    }

    public void clickOnMap(MouseEvent m) {
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

    public void setOrigin() {
        currentPath = null;
        origin = currentLoc;
        refreshCanvas();
    }

    public void setDestination() {
        currentPath = null;
        destination = currentLoc;
        //destinationField.setText(destination.getNodeID()); //TODO add a listener here instead
        refreshCanvas();
    }

    public void findPath() {
        boolean success = true;
        try {
            origin.equals("");
            destination.equals("");
        }
        catch(NullPointerException e){
            errorController.showError("Please set a start and end location");
            success = false;
        }
        if (success) {
            currentPath = pathFindingFacade.getPath(origin, destination);
            refreshCanvas();
        }
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
            Node next = pathToDraw.get(i);
            Node current = pathToDraw.get(i+1);
            if (current.getFloor().equals(currentFloor) && next.getFloor().equals(currentFloor)) {
                int x1 = current.getXcoord();
                int y1 = current.getYcoord();
                int x2 = next.getXcoord();
                int y2 = next.getYcoord();
                gc.setLineWidth(3);
                gc.strokeLine(x1, y1, x2, y2);
            }
            else if (current.getFloor().equals(currentFloor)) {
                int currentFloor = floorStringToInt(current.getFloor());
                int nextFloor = floorStringToInt(next.getFloor());
                String direction;
                if (currentFloor < nextFloor) {
                    direction = "UP";
                    //and draw solid dot
                    gc.setFill(Color.BLUE);
                    gc.fillOval(current.getXcoord() - 10, current.getYcoord() - 10, 20, 20);
                }
                else {
                    direction = "DOWN";
                    //and draw outlined dot
                    gc.setStroke(Color.BLUE);
                    gc.strokeOval(current.getXcoord() - 10, current.getYcoord() - 10, 20, 20);
                    gc.setStroke(Color.BLACK);
                }
                gc.setStroke(Color.BLUE);
//              gc.setFont(); //TODO
                gc.strokeText(direction, current.getXcoord(), current.getYcoord() - 10);
                gc.setStroke(Color.BLACK);
            }
        }
    }

    private int floorStringToInt(String floor) {
        if (floor.equals("L2")) return -2;
        if (floor.equals("L1")) return -1;
        if (floor.equals("G")) return 0;
        if (floor.equals("1")) return 1;
        if (floor.equals("2")) return 2;
        return 3;
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