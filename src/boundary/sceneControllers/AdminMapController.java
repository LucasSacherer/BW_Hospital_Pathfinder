package boundary.sceneControllers;


        import Database.EdgeManager;
        import Database.NodeManager;
        import Entity.Edge;
        import Entity.Node;
        import MapNavigation.MapNavigationFacade;
        import Pathfinding.PathFindingFacade;
        import com.jfoenix.controls.JFXTextField;
        import javafx.collections.FXCollections;
        import javafx.collections.ObservableList;
        import javafx.fxml.FXML;
        import javafx.scene.canvas.Canvas;
        import javafx.scene.canvas.GraphicsContext;
        import javafx.scene.control.*;
        import javafx.scene.image.ImageView;
        import javafx.scene.layout.Pane;
        import javafx.scene.paint.Color;

        import java.util.List;

public class AdminMapController {

    final private NodeManager nodeManager = new NodeManager();
    final private EdgeManager edgeManager = new EdgeManager(nodeManager);
    private Canvas canvas;
    private String currentFloor;
    private GraphicsContext gc;
    private Pane mapPane;
    private ImageView imageView;

    private Node loc1, loc2, currentLoc;
    private Edge startNode, endNode, weight;

    private String longName = "";
    private String shortName = "";
    private String nodeID = "";



    public AdminMapController(ImageView i, Pane mapPane, Canvas canvas){
        this.imageView = i;
        this.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();
        System.out.println(gc);
        this.mapPane = mapPane;
    }

//    public void setLoc1(TextField originField) {
//        loc1 = currentLoc;
//        originField.setText(loc1.getShortName());
//        drawNode();
//    }


    public void setLoc2(TextField destinationField) {
        loc2 = currentLoc;
        destinationField.setText(loc2.getShortName());
    }


    public void clearCanvas() {
        gc.clearRect(0,0,canvas.getWidth(),canvas.getHeight());
    }

    /**
     *
     * Adding nodes
     * Map->Nodes->Add
     *
     */
//    public void setShortName(JFXTextField shortNameAdd) {
//        this.shortNameAdd = staffLogin;
//    }



    public void drawNode(Node n) {
        gc.setFill(Color.BLUE);
        gc.fillOval(n.getXcoord() - 10, n.getYcoord() - 10, 20, 20);
        gc.setFill(Color.BLACK);
    }

//    public void drawEdge() {
//        Node startNode = edge.getStartNode();
//        int sx = startNode.getXcoord();
//        int sy = startNode.getYcoord();
//        Node endNode = edge.getEndNode();
//        int ex = endNode.getXcoord();
//        int ey = endNode.getYcoord();
//
//        gc.setLineWidth(3);
//        gc.strokeLine(sx,sy,ex,ey);
//    }
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

}
