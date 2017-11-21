package boundary.sceneControllers;

import Entity.Node;
import MapNavigation.MapNavigationFacade;
import Pathfinding.PathFindingFacade;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.List;

public class MainSceneController {
    private Canvas canvas;
    private String currentFloor;
    private GraphicsContext gc;
    private Pane mapPane;
    private List currentPath;
    private MapNavigationFacade mapNavigationFacade;
    private PathFindingFacade pathFindingFacade;
    private ImageView imageView;

    public MainSceneController(ImageView i, Pane mapPane, Canvas canvas, MapNavigationFacade m, PathFindingFacade p){
        this.imageView = i;
        this.mapNavigationFacade = m;
        this.pathFindingFacade = p;
        this.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();
        System.out.println(gc);
        this.mapPane = mapPane;
    }

    private Node loc1, loc2, currentLoc;

    public void setLoc1(TextField originField) {
        loc1 = currentLoc;
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
        int x = (int) m.getX();
        int y = (int) m.getY();
        // todo currentLoc = m.getNearestNode(x,y,currentFloor);
        clearCanvas();
        drawCurrentNode();
        drawPath();
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

    public void floorDown() {
//        switch(currentFloor) {
//            case "L2" :
//                return;
//            case "L1" :
//                imageView.setImage(mapDisplayController.getMap("L2"));
//                currentFloor = "L2";
//                currentFloorNum.setText(currentFloor);
//                break;
//            case "G" :
//                imageView.setImage(mapDisplayController.getMap("L1"));
//                currentFloor = "L1";
//                currentFloorNum.setText(currentFloor);
//                break;
//            case "1" :
//                imageView.setImage(mapDisplayController.getMap("G"));
//                currentFloor = "G";
//                currentFloorNum.setText(currentFloor);
//                break;
//            case "2" :
//                imageView.setImage(mapDisplayController.getMap("1"));
//                currentFloor = "1";
//                currentFloorNum.setText(currentFloor);
//                break;
//            case "3" :
//                imageView.setImage(mapDisplayController.getMap("2"));
//                currentFloor = "2";
//                currentFloorNum.setText(currentFloor);
//                break;
//        }
//        clearCanvas();
//        drawPath();
//        drawCurrentNode();
    }

    public void floorUp() {
//        switch (currentFloor) {
//            case "L2":
//                imageView.setImage(mapDisplayController.getMap("L1"));
//                currentFloor = "L1";
//                currentFloorNum.setText(currentFloor);
//                break;
//            case "L1":
//                imageView.setImage(mapDisplayController.getMap("G"));
//                currentFloor = "G";
//                currentFloorNum.setText(currentFloor);
//                break;
//            case "G":
//                imageView.setImage(mapDisplayController.getMap("1"));
//                currentFloor = "1";
//                currentFloorNum.setText(currentFloor);
//                break;
//            case "1":
//                imageView.setImage(mapDisplayController.getMap("2"));
//                currentFloor = "2";
//                currentFloorNum.setText(currentFloor);
//                break;
//            case "2":
//                imageView.setImage(mapDisplayController.getMap("3"));
//                currentFloor = "3";
//                currentFloorNum.setText(currentFloor);
//                break;
//        }
//        clearCanvas();
//        drawPath();
//        drawCurrentNode();
    }

    public void clickOnMap(MouseEvent m) {
        snapToNode(m);
    }
}