package boundary.sceneControllers;

import Entity.Node;
import MapNavigation.MapNavigationFacade;
import Pathfinding.PathFindingFacade;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class MainSceneController {
    private Label currentFloorNum;
    private Canvas canvas;
    private String currentFloor = "G";
    private GraphicsContext gc;
    private Pane mapPane;
    private List currentPath;
    private MapNavigationFacade mapNavigationFacade;
    private PathFindingFacade pathFindingFacade;
    private ImageView imageView;

    private Node loc1, loc2, currentLoc;

    public MainSceneController(ImageView i, Pane mapPane, Canvas canvas, MapNavigationFacade m, PathFindingFacade p, Label currentFloorNum){
        this.imageView = i;
        this.mapNavigationFacade = m;
        this.pathFindingFacade = p;
        this.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();
        this.mapPane = mapPane;
        this.currentFloorNum = currentFloorNum;
       // todo currentFloorNum.setText(currentFloor);
    }

    public void setLoc1(TextField originField) {
        loc1 = currentLoc;
        System.out.println(loc1);
        originField.setText(loc1.getShortName());
        drawCurrentNode();
    }


    public void setLoc2(TextField destinationField) {
        loc2 = currentLoc;
        destinationField.setText(loc2.getShortName());
    }

    public void findPath() {
        currentPath = pathFindingFacade.getPath(loc1,loc2);
        clearCanvas();
        drawPath();
        drawCurrentNode();
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

    public void snapToNode(MouseEvent m) {
        clearCanvas();
        int x = (int) m.getX();
        int y = (int) m.getY();
        currentLoc = mapNavigationFacade.getNearestNode(x,y,currentFloor);
        drawCurrentNode();
    }

    public void drawPath() {

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

    public void drawCurrentNode() {
        Node toDraw = currentLoc;
        if(toDraw == null || !toDraw.getFloor().equals(currentFloor)){
            return;
        }
        gc.setFill(Color.BLUE);
        gc.fillOval(toDraw.getXcoord()-10,toDraw.getYcoord()-10,20,20);
    }

    public void clearCanvas() {
        gc.clearRect(0,0,canvas.getWidth(),canvas.getHeight());
    }

    public void bathroomClicked() {
        findNearest(currentLoc, "REST");
    }


    public void infoClicked() {
        findNearest(currentLoc, "INFO");
    }

    public void elevatorClicked() {
        findNearest(currentLoc, "ELEV");
    }

    private void findNearest(Node node, String type) {
        //todo loc2 = mapNavigationFacade.getNearestPOI(node.getXcoord(), node.getYcoord(), type);
        drawNode(loc2);
        currentPath = pathFindingFacade.getPath(loc1, loc2);
        clearCanvas();
        drawPath();
        drawCurrentNode();
    }

    private void drawNode(Node n) {
        gc.setFill(Color.BLUE);
        gc.fillOval(n.getXcoord(), n.getYcoord(), 20, 20);
    }

    public void floorDown() throws IOException, SQLException {
        switch(currentFloor) {
            case "L2" :
                return;
            case "L1" :
                imageView.setImage(mapNavigationFacade.getFloorMap("L2"));
                currentFloor = "L2";
                currentFloorNum.setText(currentFloor);
                break;
            case "G" :
                imageView.setImage(mapNavigationFacade.getFloorMap("L1"));
                currentFloor = "L1";
                currentFloorNum.setText(currentFloor);
                break;
            case "1" :
                imageView.setImage(mapNavigationFacade.getFloorMap("G"));
                currentFloor = "G";
                currentFloorNum.setText(currentFloor);
                break;
            case "2" :
                imageView.setImage(mapNavigationFacade.getFloorMap("1"));
                currentFloor = "1";
                currentFloorNum.setText(currentFloor);
                break;
            case "3" :
                imageView.setImage(mapNavigationFacade.getFloorMap("2"));
                currentFloor = "2";
                currentFloorNum.setText(currentFloor);
                break;
        }
        clearCanvas();
        drawPath();
        drawCurrentNode();
    }

    public void floorUp() throws IOException, SQLException {
        switch (currentFloor) {
            case "L2":
                imageView.setImage(mapNavigationFacade.getFloorMap("L1"));
                currentFloor = "L1";
                currentFloorNum.setText(currentFloor);
                break;
            case "L1":
                imageView.setImage(mapNavigationFacade.getFloorMap("G"));
                currentFloor = "G";
                currentFloorNum.setText(currentFloor);
                break;
            case "G":
                imageView.setImage(mapNavigationFacade.getFloorMap("1"));
                currentFloor = "1";
                currentFloorNum.setText(currentFloor);
                break;
            case "1":
                imageView.setImage(mapNavigationFacade.getFloorMap("2"));
                currentFloor = "2";
                currentFloorNum.setText(currentFloor);
                break;
            case "2":
                imageView.setImage(mapNavigationFacade.getFloorMap("3"));
                currentFloor = "3";
                currentFloorNum.setText(currentFloor);
                break;
        }
        clearCanvas();
        drawPath();
        drawCurrentNode();
    }

    public void clickOnMap(MouseEvent m) {
        snapToNode(m);
    }
}