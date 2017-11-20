package boundary.sceneControllers;

import Entity.Node;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.List;

public class MainSceneController {

    public void setLoc1() {
//        loc1 = currentLoc;
//        originField.setText(loc1.getShortName());
    }


    public void setLoc2() {
//        loc2 = currentLoc;
//        destinationField.setText(loc2.getShortName());
    }

    public void findPath() {
//        currentPath = pathController.findPath(loc1,loc2);
//        clearCanvas();
//        drawPath();
//        drawCurrentNode();
    }

    public void zoomInMap() {
//        if (mapPane.getScaleX() >= 0.8 || mapPane.getScaleY() >= 0.8) return;
//        mapPane.setScaleX(mapPane.getScaleX() + 0.1);
//        mapPane.setScaleY(mapPane.getScaleY() + 0.1);
    }


    public void zoomOutMap() {
//        if (mapPane.getScaleX() <= 0.5 || mapPane.getScaleY() <= 0.5) return;
//        mapPane.setScaleX(mapPane.getScaleX() - 0.1);
//        mapPane.setScaleY(mapPane.getScaleY() - 0.1);
    }

    public void snapToNode() {
//        int x = (int) m.getX();
//        int y = (int) m.getY();
//        currentLoc = clickController.getNearestNode(x,y,currentFloor);
//        clearCanvas();
//        drawCurrentNode();
//        drawPath();
    }

    public void drawPath() {
//
//        List<Node> pathToDraw = currentPath;
//
//        if(pathToDraw == null || pathToDraw.size() == 0||!pathToDraw.get(0).getFloor().equals(currentFloor)){
//            return;
//        }
//
//        /** Testing Only **
//         ArrayList<Node> pathToDraw = new ArrayList<>(); //TODO this list is for testing
//         pathToDraw.add(new Node("a",10, 10, "a","a","a","a","a",true));
//         pathToDraw.add(new Node("b",300, 300, "a","a","a","a","a",true));
//         pathToDraw.add(new Node("c",2000, 300, "a","a","a","a","a",true));
//         /** testing over **/
//
//        for(int i=0;i<pathToDraw.size()-1;i++) {
//            int x1 = pathToDraw.get(i).getXcoord();
//            int y1 = pathToDraw.get(i).getYcoord();
//            int x2 = pathToDraw.get(i+1).getXcoord();
//            int y2 = pathToDraw.get(i+1).getYcoord();
//            gc.setLineWidth(5);
//            gc.strokeLine(x1,y1,x2,y2);
//        }
//    }

    }

    public void drawCurrentNode() {
//        Node toDraw = currentLoc;
//        if(toDraw == null || !toDraw.getFloor().equals(currentFloor)){
//            return;
//        }
//        gc.fillOval(toDraw.getXcoord()-10,toDraw.getYcoord()-10,20,20);
    }

    public void drawEdge() {
//        Node startNode = edge.getStartNode();
//        int sx = startNode.getXcoord();
//        int sy = startNode.getYcoord();
//        Node endNode = edge.getEndNode();
//        int ex = endNode.getXcoord();
//        int ey = endNode.getYcoord();
//
//        gc.setLineWidth(3);
//        gc.strokeLine(sx,sy,ex,ey);
    }

    public void drawNode() {
//        gc.setFill(Color.BLUE);
//        gc.fillOval(n.getXcoord() - 10, n.getYcoord() - 10, 20, 20);
//        gc.setFill(Color.BLACK);
    }

    public void clearCanvas() {
//        gc.clearRect(0,0,canvas.getWidth(),canvas.getHeight());
    }


    public void bathroomClicked() {
//        findNearest(currentLoc, "REST");
    }


    public void infoClicked() {
//        findNearest(currentLoc, "INFO");
    }

    public void elevatorClicked() {
//        findNearest(currentLoc, "ELEV");
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

    public void clickOnMap() {
        //snapToNode(m);
    }
}