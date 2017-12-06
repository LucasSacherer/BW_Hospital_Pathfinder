package boundary.sceneControllers.mapEditing;

import Editor.EdgeEditController;
import Editor.NodeEditController;
import Entity.Node;
import boundary.sceneControllers.AdminMapController;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Straightener {
    private NodeEditController nodeEditController;
    private JFXTextField edgeXStartStraighten, edgeYStartStraighten, edgeXEndStraighten, edgeYEndStraighten;
    private Node startNode, endNode;
    private ArrayList<Node> nodes;
    private GraphicsContext gc;
    private AdminMapController adminMapController;

    public Straightener(NodeEditController nodeEditController, JFXTextField edgeXStartStraighten, JFXTextField edgeYStartStraighten, JFXTextField edgeXEndStraighten, JFXTextField edgeYEndStraighten, GraphicsContext gc, AdminMapController adminMapController) {
        this.adminMapController = adminMapController;
        this.nodeEditController = nodeEditController;
        this.edgeXStartStraighten = edgeXStartStraighten;
        this.edgeYStartStraighten = edgeYStartStraighten;
        this.edgeXEndStraighten = edgeXEndStraighten;
        this.edgeYEndStraighten = edgeYEndStraighten;
        this.gc = gc;
        nodes = new ArrayList<>();
    }


    public void straighten() {
        nodes.add(0, startNode);
        nodes.add(0, endNode);
        nodeEditController.alignNodes(nodes);
        reset();
        refreshCanvas();
    }

    public void reset() {
        startNode = null;
        endNode = null;
        edgeYEndStraighten.clear();
        edgeYStartStraighten.clear();
        edgeXEndStraighten.clear();
        edgeXStartStraighten.clear();
        nodes.clear();
        refreshCanvas();
    }

    public void clickOnMap(Node currentLoc) {
        if (startNode == null) setStart(currentLoc, gc);
        else if (endNode == null) setEnd(currentLoc, gc);
        else {
            nodes.add(currentLoc);
        }
        refreshCanvas();
    }

    private void setStart(Node currentLoc, GraphicsContext gc) {
        startNode = currentLoc;
        edgeXStartStraighten.setText("" + startNode.getXcoord());
        edgeYStartStraighten.setText("" + startNode.getYcoord());
    }

    private void setEnd(Node currentLoc, GraphicsContext gc) {
        endNode = currentLoc;
        edgeXEndStraighten.setText("" + endNode.getXcoord());
        edgeYEndStraighten.setText("" + endNode.getYcoord());
    }

    private void refreshCanvas() {
        adminMapController.refreshCanvas();
        if (startNode != null) {
            gc.setFill(Color.GREEN);
            gc.fillOval(startNode.getXcoord() - 7, startNode.getYcoord() - 7, 14, 14);
            gc.setFill(Color.GREENYELLOW);
            gc.fillOval(startNode.getXcoord() - 3, startNode.getYcoord() - 3, 6, 6);
        }
        if (endNode != null) {
            gc.setFill(Color.BLUE);
            gc.fillOval(endNode.getXcoord() - 7, endNode.getYcoord() - 7, 14, 14);
            gc.setFill(Color.BLUEVIOLET);
            gc.fillOval(endNode.getXcoord() - 3, endNode.getYcoord() - 3, 6, 6);


            int sx = startNode.getXcoord();
            int sy = startNode.getYcoord();
            int ex = endNode.getXcoord();
            int ey = endNode.getYcoord();

            gc.setStroke(Color.RED);
            gc.setLineWidth(5);
            gc.strokeLine(sx,sy,ex,ey);

            gc.setStroke(Color.YELLOW);
            gc.setLineWidth(1);
            gc.strokeLine(sx,sy,ex,ey);

            gc.setStroke(Color.BLACK);
        }
        for (Node n : nodes) {
            gc.setFill(Color.GREEN);
            gc.fillOval(n.getXcoord() - 7, n.getYcoord() - 7, 14, 14);
            gc.setFill(Color.GREENYELLOW);
            gc.fillOval(n.getXcoord() - 3, n.getYcoord() - 3, 6, 6);
        }
        gc.setFill(Color.BLACK);
    }

}
