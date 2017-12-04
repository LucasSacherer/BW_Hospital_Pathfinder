package boundary.sceneControllers;

import Entity.Node;
import Entity.ErrorController;
import MapNavigation.MapNavigationFacade;
import Pathfinding.PathFindingFacade;
import boundary.GodController;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.scene.paint.Paint;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Transform;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

public abstract class AbstractMapController {
    private ImageButton floorChange;
    protected GodController godController;
    protected Label currentFloorNum;
    protected Canvas canvas;
    protected String currentFloor = "G";
    protected GraphicsContext gc;
    protected Pane mapPane;
    protected List currentPath;
    protected MapNavigationFacade mapNavigationFacade;
    protected PathFindingFacade pathFindingFacade;
    protected ImageView imageView;
    protected Image uparrow = new Image(AbstractMapController.class.getResourceAsStream("/boundary/images/up_arrow.png"));//new Image("./boundary/images/up_arrow.png");
    protected Image downarrow = new Image(AbstractMapController.class.getResourceAsStream("/boundary/images/down_arrow.png"));//new Image("./boundary/images/down_arrow.png");
    protected Image circleoutline = new Image(AbstractMapController.class.getResourceAsStream("/boundary/images/circle-outline.png"));//new Image("./boundary/images/circle-outline.png");

    protected Node origin, destination, currentLoc;
    protected ErrorController errorController = new ErrorController();

    public AbstractMapController(GodController g, ImageView i, Pane mapPane, Canvas canvas, MapNavigationFacade m, PathFindingFacade p, Label currentFloorNum) {
        this.godController = g;
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
        mapPane.getChildren().remove(floorChange);
        if (origin == null) origin = mapNavigationFacade.getDefaultNode();
        clearCanvas();
        drawCurrentNode();
        drawPath();
        gc.setTransform(1, 0, 0, 1, 0, 0);
        drawPathNodes();
        drawOrigin();
        drawDestination();
    }

    private void drawDestination() { //TODO make this the icon of location
        if (destination != null && destination.getFloor().equals(currentFloor)) {
            gc.setFill(Color.WHITE);
            gc.fillOval(destination.getXcoord() - 10, destination.getYcoord() - 10, 20, 20);
            gc.setFill(Color.BLACK);
            gc.fillOval(destination.getXcoord() - 9, destination.getYcoord() - 9, 18, 18);
            gc.setFill(Color.WHITE);
            gc.fillOval(destination.getXcoord() - 6, destination.getYcoord() - 6, 12, 12);
            gc.setFill(Color.BLACK);
            gc.fillOval(destination.getXcoord() - 3, destination.getYcoord() - 3, 6, 6);
        }
    }

    private void drawOrigin() {
        if (origin != null && origin.getFloor().equals(currentFloor)) {
            gc.setFill(Color.LIGHTBLUE);
            gc.fillOval(origin.getXcoord() - 15, origin.getYcoord() - 15, 30, 30);
            gc.setFill(Color.WHITE);
            gc.fillOval(origin.getXcoord() - 12, origin.getYcoord() - 12, 24, 24);
            gc.setFill(Color.BLUE);
            gc.fillOval(origin.getXcoord() - 9, origin.getYcoord() - 9, 18, 18);
        }
        gc.setFill(Color.BLACK);
    }

    public void clearCanvas() { gc.clearRect(0,0,canvas.getWidth(),canvas.getHeight()); }

    public void drawCurrentNode() {
        if(currentLoc == null || !currentLoc.getFloor().equals(currentFloor)){ return; }
        gc.setFill(Color.WHITE);
        gc.fillOval(currentLoc.getXcoord()-10,currentLoc.getYcoord()-10,20,20);
        gc.setFill(Color.BLACK);
        gc.fillOval(currentLoc.getXcoord()-9,currentLoc.getYcoord()-9,18,18);
        gc.setFill(Color.WHITE);
        gc.fillOval(currentLoc.getXcoord()-6,currentLoc.getYcoord()-6,12,12);
        gc.setFill(Color.BLACK);
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

    public void findPath() throws IOException {
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
            int x1 = current.getXcoord();
            int y1 = current.getYcoord();
            int x2 = next.getXcoord();
            int y2 = next.getYcoord();
            if (current.getFloor().equals(currentFloor) && next.getFloor().equals(currentFloor)) {

                gc.setLineWidth(7);
                drawArrow(x1, y1, x2, y2, Color.DARKSLATEGRAY, 10);
            }
        }

        for(int i=0;i<pathToDraw.size()-1;i++) {
            Node next = pathToDraw.get(i);
            Node current = pathToDraw.get(i+1);
            int x1 = current.getXcoord();
            int y1 = current.getYcoord();
            int x2 = next.getXcoord();
            int y2 = next.getYcoord();
            if (current.getFloor().equals(currentFloor) && next.getFloor().equals(currentFloor)) {

                gc.setLineWidth(4);
                drawArrow(x1, y1, x2, y2, Color.ROYALBLUE, 8);
            }
        }
    }

    void drawArrow(int x1, int y1, int x2, int y2, Color c, int triangleNum) {
        gc.setFill(c);
        gc.setStroke(c);

        double dx = x2 - x1, dy = y2 - y1;
        double angle = Math.atan2(dy, dx);
        int len = (int) Math.sqrt(dx * dx + dy * dy);

        Transform transform = Transform.translate(x1, y1);
        transform = transform.createConcatenation(Transform.rotate(Math.toDegrees(angle), 0, 0));
        gc.setTransform(new Affine(transform));

        gc.strokeLine(0, 0, len, 0);
        gc.fillPolygon(new double[]{len, len - triangleNum, len - triangleNum, len}, new double[]{0, -triangleNum, triangleNum, 0},4);
    }

    private void drawPathNodes() {
        List<Node> pathToDraw = currentPath;
        if (pathToDraw == null || pathToDraw.size() == 0) { return; }
        for (int i = 0; i < pathToDraw.size() - 1; i++) {
            Node next = pathToDraw.get(i);
            Node current = pathToDraw.get(i + 1);
            int currentFloorInt = floorStringToInt(current.getFloor());
            int nextFloorInt = floorStringToInt(next.getFloor());
            if (current.getFloor().equals(currentFloor) &&  !next.getFloor().equals(currentFloor)) {
                floorChange = new ImageButton();
                floorChange.setLayoutX(current.getXcoord() - 10);
                floorChange.setLayoutY(current.getYcoord() - 10);
                floorChange.setFloor(next);
                if (currentFloorInt < nextFloorInt) { floorChange.updateImages(uparrow); }
                else { floorChange.updateImages(downarrow); }
                mapPane.getChildren().add(floorChange);
            }
            if (next.getFloor().equals(currentFloor) && !current.getFloor().equals(currentFloor)) {
                gc.setFill(Color.WHITE);
                gc.fillOval(next.getXcoord() - 12, next.getYcoord() - 12, 24, 24);
                gc.drawImage(circleoutline, next.getXcoord() - 12, next.getYcoord() - 12, 24, 24);
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

    private class ImageButton extends JFXButton {
        public ImageButton() {
            this.setButtonType(JFXButton.ButtonType.RAISED);
            this.setPrefSize(20, 20);
        }

        public void setFloor(Node next) {
            this.setOnAction(new javafx.event.EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    currentFloor = next.getFloor();
                    imageView.setImage(mapNavigationFacade.getFloorMap(currentFloor));
                    currentFloorNum.setText(currentFloor);
                    refreshCanvas();
                }
            });
        }

        public void updateImages(final Image image) {
            final ImageView iv = new ImageView(image);
            iv.setFitHeight(30);
            iv.setFitWidth(30);
            this.getChildren().add(iv);
            super.setGraphic(iv);
        }
    }
}