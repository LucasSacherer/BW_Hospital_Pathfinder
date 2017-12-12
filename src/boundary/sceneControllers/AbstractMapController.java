package boundary.sceneControllers;

import Entity.Node;
import Entity.ErrorController;
import MapNavigation.MapNavigationFacade;
import Pathfinding.PathFindingFacade;
import boundary.GodController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPopup;
import com.jfoenix.controls.JFXRippler;
import com.jfoenix.controls.JFXSlider;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Transform;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractMapController {
    protected ArrayList<ImageButton> buttons = new ArrayList<>();
    protected final double ZOOM = 0.5;
    protected final double MAX_ZOOM = 2.0;
    protected Group group;
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
    protected Image locationImage = new Image(AbstractMapController.class.getResourceAsStream("/boundary/images/end-point.png"));
    protected Image uparrow = new Image(AbstractMapController.class.getResourceAsStream("/boundary/images/up_arrow.png"));//new Image("./boundary/images/up_arrow.png");
    protected Image downarrow = new Image(AbstractMapController.class.getResourceAsStream("/boundary/images/down_arrow.png"));//new Image("./boundary/images/down_arrow.png");
    protected Image circleoutline = new Image(AbstractMapController.class.getResourceAsStream("/boundary/images/circle-outline.png"));//new Image("./boundary/images/circle-outline.png");

    protected Node origin, destination, currentLoc;
    protected ErrorController errorController = new ErrorController();
    protected JFXSlider zoomSlider;
    protected javafx.scene.control.ScrollPane scrollPane;

    public AbstractMapController(GodController g, MapNavigationFacade m, PathFindingFacade p, JFXSlider zoomSlider, javafx.scene.control.ScrollPane scrollPane) {
        this.godController = g;
        this.mapNavigationFacade = m;
        this.pathFindingFacade = p;
        this.zoomSlider = zoomSlider;
        this.scrollPane = scrollPane;
    }

    public void initializeScene() throws IOException {
        group = new Group();
        mapPane = new Pane();
        imageView = new ImageView();
        imageView.setPreserveRatio(true);
        canvas = new Canvas();
        canvas.setWidth(5000);
        canvas.setHeight(3400);
        EventHandler<MouseEvent> handler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                printScroll();
                clickOnMap(event);
            }
        };

        canvas.setOnMouseClicked(handler);

        gc = canvas.getGraphicsContext2D();
        mapPane.getChildren().addAll(imageView, canvas);
        group.getChildren().add(mapPane);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setContent(group);

        origin = mapNavigationFacade.getDefaultNode();
        currentFloor = origin.getFloor();
        imageView.setImage(mapNavigationFacade.getFloorMap(origin.getFloor()));
//        centerMap();
    }

    protected void zoomOut() {
        mapPane.setScaleX(ZOOM);
        mapPane.setScaleY(ZOOM);
        zoomSlider.setValue(0);
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
        for (ImageButton b : buttons) mapPane.getChildren().remove(b);
        buttons.clear();
        if (origin == null) origin = mapNavigationFacade.getDefaultNode();
        clearCanvas();
        drawCurrentNode();
        drawPath();
        gc.setTransform(1, 0, 0, 1, 0, 0);
        drawOrigin();
        drawDestination();
        drawPathNodes();
    }

    private void drawDestination() {
        if (destination != null && destination.getFloor().equals(currentFloor)) {
            gc.setFill(Color.WHITE);
            gc.fillOval(destination.getXcoord() - 10, destination.getYcoord() - 10, 20, 20);
            gc.setFill(Color.BLACK);
            gc.fillOval(destination.getXcoord() - 9, destination.getYcoord() - 9, 18, 18);
            gc.setFill(Color.WHITE);
            gc.fillOval(destination.getXcoord() - 6, destination.getYcoord() - 6, 12, 12);
            gc.setFill(Color.BLACK);
            gc.fillOval(destination.getXcoord() - 3, destination.getYcoord() - 3, 6, 6);
            gc.drawImage(locationImage, destination.getXcoord() - 16, destination.getYcoord() - 46, 32, 46);

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
            errorController.showError("Please select a start and end location.");
            success = false;
        }
        if (success) {
            currentPath = pathFindingFacade.getPath(origin, destination);
            refreshCanvas();
        }
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
                ImageButton floorChange;
                if (currentFloorInt < nextFloorInt) {
                    floorChange = new ImageButton();
                    floorChange.initiate(uparrow, next);
                    floorChange.setLayoutX(current.getXcoord() - 25);
                    floorChange.setLayoutY(current.getYcoord() - 25);
                }
                else {
                    floorChange = new ImageButton();
                    floorChange.initiate(downarrow, next);
                    floorChange.setLayoutX(current.getXcoord() - 25);
                    floorChange.setLayoutY(current.getYcoord() - 25);
                }
                buttons.add(floorChange);
                mapPane.getChildren().add(floorChange);
                floorChange.toFront();
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
//        currentFloorNum.setText(currentFloor);
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
//        currentFloorNum.setText(currentFloor);
        refreshCanvas();
    }

    public void zoom() {
        double sliderLevel = zoomSlider.getValue() / 100;
        double zoomLevel = sliderLevel+ ZOOM;
        Point2D scrollOffset = figureScrollOffset(mapPane, scrollPane);

        double scaleFactor = 1;
        mapPane.setScaleX(zoomLevel);
        mapPane.setScaleY(zoomLevel);

        repositionScroller(mapPane, scrollPane, scaleFactor, scrollOffset);
    }

    protected Point2D figureScrollOffset(javafx.scene.Node scrollContent, javafx.scene.control.ScrollPane scroller) {
        double extraWidth = scrollContent.getLayoutBounds().getWidth() - scroller.getViewportBounds().getWidth();
        double hScrollProportion = (scroller.getHvalue() - scroller.getHmin()) / (scroller.getHmax() - scroller.getHmin());
        double scrollXOffset = hScrollProportion * Math.max(0, extraWidth);
        double extraHeight = scrollContent.getLayoutBounds().getHeight() - scroller.getViewportBounds().getHeight();
        double vScrollProportion = (scroller.getVvalue() - scroller.getVmin()) / (scroller.getVmax() - scroller.getVmin());
        double scrollYOffset = vScrollProportion * Math.max(0, extraHeight);
        return new Point2D(scrollXOffset, scrollYOffset);
    }

    protected void repositionScroller(javafx.scene.Node scrollContent, javafx.scene.control.ScrollPane scroller, double scaleFactor, Point2D scrollOffset) {
        double scrollXOffset = scrollOffset.getX();
        double scrollYOffset = scrollOffset.getY();
        double extraWidth = scrollContent.getLayoutBounds().getWidth() - scroller.getViewportBounds().getWidth();
        if (extraWidth > 0) {
            double halfWidth = scroller.getViewportBounds().getWidth() / 2 ;
            double newScrollXOffset = (scaleFactor - 1) *  halfWidth + scaleFactor * scrollXOffset;
            scroller.setHvalue(scroller.getHmin() + newScrollXOffset * (scroller.getHmax() - scroller.getHmin()) / extraWidth);
        } else {
            scroller.setHvalue(scroller.getHmin());
        }
        double extraHeight = scrollContent.getLayoutBounds().getHeight() - scroller.getViewportBounds().getHeight();
        if (extraHeight > 0) {
            double halfHeight = scroller.getViewportBounds().getHeight() / 2 ;
            double newScrollYOffset = (scaleFactor - 1) * halfHeight + scaleFactor * scrollYOffset;
            scroller.setVvalue(scroller.getVmin() + newScrollYOffset * (scroller.getVmax() - scroller.getVmin()) / extraHeight);
        } else {
            scroller.setHvalue(scroller.getHmin());
        }
    }

    protected void centerMap() {
//        zoomOut();
        goToCorrectFloor();
        if (currentPath == null) {
            Bounds windowSize = scrollPane.getViewportBounds();

            double xCoord = origin.getXcoord();
            double windowWidth = windowSize.getWidth(); // - windowSize.getWidth());
            double q = xCoord - (windowWidth / 2.0);

            if (q < 0) q = 0;
            if (q > 5000) q = 5000;

            scrollPane.setHvalue(q);
            System.out.println(q);
            System.out.println(origin.getXcoord());
        }
    }

    //TODO delete this
    private void printScroll() {
        Bounds windowSize = scrollPane.getViewportBounds();
        System.out.println("Window size is: " + windowSize.getWidth());
        System.out.println("H Value: " + scrollPane.getHvalue());
        System.out.println("Origin x coordinate: " + origin.getXcoord());
        System.out.println(" Ratio = " + origin.getXcoord()/ 5000.0);
        System.out.println("Window ratio" + (windowSize.getWidth()/5000.0));
        System.out.println("\n\n\n");
    }

    private void goToCorrectFloor() {
        currentFloor = origin.getFloor();
        imageView.setImage(mapNavigationFacade.getFloorMap(currentFloor));
        refreshCanvas();
    }

    public class ImageButton extends Button {
        public void initiate(final Image image, final Node next) {
            final ImageView iv = new ImageView(image);
            iv.setPreserveRatio(true);
            iv.setFitHeight(30);
            iv.setFitWidth(30);
            this.getChildren().add(iv);
            this.resize(30,30);

            this.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    currentFloor = next.getFloor();
                    imageView.setImage(mapNavigationFacade.getFloorMap(currentFloor));
//                    currentFloorNum.setText(currentFloor);
                    refreshCanvas();
                }
            });
            iv.setOnMouseClicked(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent evt) {
                    currentFloor = next.getFloor();
                    imageView.setImage(mapNavigationFacade.getFloorMap(currentFloor));
//                    currentFloorNum.setText(currentFloor);
                    refreshCanvas();
                }
            });
            super.setGraphic(iv);
        }
    }

//    public List getCurrentPath(){
//        return currentPath;
//    }
}