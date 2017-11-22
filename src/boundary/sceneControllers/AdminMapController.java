package boundary.sceneControllers;


        import Database.EdgeManager;
        import Database.NodeManager;
        import Editor.NodeEditController;
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

    private NodeEditController nodeEditController;
    private Canvas canvas;
    private String currentFloor;
    private GraphicsContext gc;
    private Pane mapPane;
    private ImageView imageView;


    private String longName = "";
    private String shortName = "";
    private String nodeID = "";

    public AdminMapController(ImageView imageView, Pane mapPane, Canvas canvas){
        this.imageView = imageView;
        this.mapPane = mapPane;
        this.canvas = canvas;
        this.nodeEditController = nodeEditController;


    }


    public void initializeScene() {
        this.gc = canvas.getGraphicsContext2D();
    }



    public void clearCanvas() {
        gc.clearRect(0,0,canvas.getWidth(),canvas.getHeight());
    }




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
    public void addNode(Node n){

    }

    public void editNode(Node n){

    }

    public void deleteNode(Node n){

    }
    public void setKioskLocation(Node n){

    }



    }


