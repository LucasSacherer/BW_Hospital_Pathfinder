package boundary.sceneControllers.mapEditing;

import Editor.EdgeEditController;
import Entity.Node;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class EdgeRemover {
    private EdgeEditController edgeEditController;
    private JFXTextField edgeXStart, edgeYStart, edgeXEnd, edgeYEnd;
    private Node startNode, endNode;
    private GraphicsContext gc;

    public EdgeRemover(EdgeEditController edgeEditController, GraphicsContext gc, JFXTextField edgeXStartRemove,
                       JFXTextField edgeYStartRemove, JFXTextField edgeXEndRemove, JFXTextField edgeYEndRemove) {
        this.edgeEditController = edgeEditController;
        this.edgeXEnd = edgeXEndRemove;
        this.edgeYEnd = edgeYEndRemove;
        this.edgeXStart = edgeXStartRemove;
        this.edgeYStart = edgeYStartRemove;
        this.gc = gc;
    }

    public void remove() { edgeEditController.deleteEdge(startNode, endNode); }

    public void reset() {
        edgeXStart.clear();
        edgeYStart.clear();
        edgeYEnd.clear();
        edgeXEnd.clear();
        startNode = null;
        endNode = null;
    }

    public void clickOnMap(Node currentLoc) {
        if (startNode == null) setStart(currentLoc, gc);
        else if (endNode == null) setEnd(currentLoc, gc);
        refreshCanvas();
    }

    private void setStart(Node currentLoc, GraphicsContext gc) {
        startNode = currentLoc;
        edgeXStart.setText("" + startNode.getXcoord());
        edgeYStart.setText("" + startNode.getYcoord());
    }

    private void setEnd(Node currentLoc, GraphicsContext gc) {
        endNode = currentLoc;
        edgeXEnd.setText("" + endNode.getXcoord());
        edgeYEnd.setText("" + endNode.getYcoord());
    }

    private void refreshCanvas() {
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
            gc.setLineWidth(4);
            gc.strokeLine(sx,sy,ex,ey);

            gc.setStroke(Color.YELLOW);
            gc.setLineWidth(2);
            gc.strokeLine(sx,sy,ex,ey);

            gc.setStroke(Color.BLACK);
        }
    }

}
