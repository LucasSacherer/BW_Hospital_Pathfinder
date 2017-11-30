package boundary.sceneControllers;

import Entity.Node;
import MapNavigation.MapNavigationFacade;
import Pathfinding.PathFindingFacade;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Transform;

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
    protected Image uparrow = new Image("./boundary/images/up_arrow.png");
    protected Image downarrow = new Image("./boundary/images/down_arrow.png");
    protected Image circleoutline = new Image("./boundary/images/circle-outline.png");

    protected Node origin, destination, currentLoc;

    public AbstractMapController(ImageView i, Pane mapPane, Canvas canvas, MapNavigationFacade m, PathFindingFacade p, Label currentFloorNum) {
        this.imageView = i;
        this.mapNavigationFacade = m;
        this.pathFindingFacade = p;
        this.canvas = canvas;
        this.mapPane = mapPane;
        this.currentFloorNum = currentFloorNum;
        currentFloor = "G";
        this.origin = mapNavigationFacade.getDefaultNode(); //TODO doesn't work
    }

    public void initializeScene() {
        imageView.setImage(mapNavigationFacade.getFloorMap("G"));
        this.gc = canvas.getGraphicsContext2D();
        currentFloorNum.setText(currentFloor);
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
        drawPathNodes();
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
            Node next = pathToDraw.get(i);
            Node current = pathToDraw.get(i+1);
            int x1 = current.getXcoord();
            int y1 = current.getYcoord();
            int x2 = next.getXcoord();
            int y2 = next.getYcoord();
            if (current.getFloor().equals(currentFloor) && next.getFloor().equals(currentFloor)) {

                gc.setLineWidth(3);
                drawArrow(x1,y1,x2,y2);
                //gc.strokeLine(x1, y1, x2, y2);
            }
        }
    }
    private void drawArrow(int x1, int y1, int x2, int y2) {
        gc.setFill(Color.BLACK);

        double dx = x2 - x1, dy = y2 - y1;
        double angle = Math.atan2(dy, dx);
        int len = (int) Math.sqrt(dx * dx + dy * dy);

        Transform transform = Transform.translate(x1, y1);
        transform = transform.createConcatenation(Transform.rotate(Math.toDegrees(angle), 0, 0));
        gc.setTransform(new Affine(transform));

        gc.strokeLine(0, 0, len, 0);
        gc.fillPolygon(new double[]{len, len - 8, len - 8, len}, new double[]{0, -8, 8, 0},
                4);
    }

    private void drawPathNodes() {
        List<Node> pathToDraw = currentPath;
        if (pathToDraw == null || pathToDraw.size() == 0) {
            return;
        }


        for (int i = 0; i < pathToDraw.size() - 2; i++) {
            Node next = pathToDraw.get(i);
            Node current = pathToDraw.get(i + 1);
            Node previous = pathToDraw.get(i + 2);
            int currentFloorInt = floorStringToInt(current.getFloor());
            int nextFloorInt = floorStringToInt(next.getFloor());
            int previousFloorInt = floorStringToInt(previous.getFloor());
            System.out.println(previous.getFloor());
            System.out.println(next.getFloor());
            System.out.println(current.getFloor());
            if (current.getFloor().equals(currentFloor) && !previous.getFloor().equals(currentFloor) && !next.getFloor().equals(currentFloor)) {
                if (currentFloorInt < nextFloorInt || currentFloorInt > previousFloorInt) {
                    System.out.println("Hello");
                    gc.setFill(Color.WHITE);
                    gc.fillOval(current.getXcoord() - 15, current.getYcoord() - 15, 30, 30);
                    gc.drawImage(uparrow, current.getXcoord() - 15, current.getYcoord() - 15, 30, 30);
                } else {
                    gc.setFill(Color.WHITE);
                    gc.drawImage(uparrow, current.getXcoord() - 15, current.getYcoord() - 15, 30, 30);
                    gc.drawImage(downarrow, current.getXcoord() - 15, current.getYcoord() - 15, 30, 30);
                }
            }
            if (current.getFloor().equals(currentFloor) && !previous.getFloor().equals(currentFloor)) {
                gc.setFill(Color.WHITE);
                gc.fillOval(current.getXcoord() - 12, current.getYcoord() - 12, 24, 24);
                gc.drawImage(circleoutline, current.getXcoord() - 12, current.getYcoord() - 12, 24, 24);
            }
            if (current.getFloor().equals(currentFloor) && !next.getFloor().equals(currentFloor)) {
                if (currentFloorInt < nextFloorInt) {
                    gc.setFill(Color.WHITE);
                    gc.fillOval(current.getXcoord() - 15, current.getYcoord() - 15, 30, 30);
                    gc.drawImage(uparrow, current.getXcoord() - 15, current.getYcoord() - 15, 30, 30);
                } else {
                    gc.setFill(Color.WHITE);
                    gc.drawImage(uparrow, current.getXcoord() - 15, current.getYcoord() - 15, 30, 30);
                    gc.drawImage(downarrow, current.getXcoord() - 15, current.getYcoord() - 15, 30, 30);
                }
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