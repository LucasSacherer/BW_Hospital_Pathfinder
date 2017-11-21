package boundary.sceneControllers;


        import Entity.Node;
        import javafx.collections.FXCollections;
        import javafx.collections.ObservableList;
        import javafx.fxml.FXML;
        import javafx.scene.control.*;
        import javafx.scene.paint.Color;

public class AdminMapController {



    @FXML
    private Tab nodesTab, edgesTab, addNode, editNode, removeNode, addEdge, removeEdge;

    @FXML
    private TextField xNode, yNode, nodeShortName,nodeLongName ;

    @FXML
    private Button addNodeButton;


    public void drawNode(Node n) {
      //  gc.setFill(Color.BLUE);
      //  gc.fillOval(n.getXcoord() - 10, n.getYcoord() - 10, 20, 20);
      //  gc.setFill(Color.BLACK);
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

}
