package boundary.sceneControllers.mapEditing;

import Editor.EdgeEditController;
import Entity.Edge;
import Entity.Node;
import Entity.ErrorController;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class EdgeAdder {
    private Node startNode, endNode;
    private EdgeEditController edgeEditController;
    private JFXTextField edgeXStart, edgeYStart, edgeXEnd, edgeYEnd;
    private GraphicsContext gc;
    private ErrorController errorController = new ErrorController();
    public EdgeAdder(EdgeEditController edgeEditController, JFXTextField edgeXStartAdd,
                     JFXTextField edgeYStartAdd, JFXTextField edgeXEndAdd, JFXTextField edgeYEndAdd, GraphicsContext gc) {
        this.edgeEditController = edgeEditController;
        this.edgeXEnd = edgeXEndAdd;
        this.edgeYEnd = edgeYEndAdd;
        this.edgeXStart = edgeXStartAdd;
        this.edgeYStart = edgeYStartAdd;
        this.gc = gc;
    }

    public void clickOnMap(Node currentLoc) {
        if (startNode == null) setStart(currentLoc);
        else if (endNode == null) setEnd(currentLoc);
        refreshCanvas();
    }

    private void refreshCanvas() {
        if (startNode != null) {
            gc.setFill(Color.GREEN);
            gc.fillOval(startNode.getXcoord() - 7, startNode.getYcoord() - 7, 14, 14);
            gc.setFill(Color.GREENYELLOW);
            gc.fillOval(startNode.getXcoord() - 3, startNode.getYcoord() - 3, 6, 6);
        }
        if (endNode != null) {
            gc.setFill(Color.RED);
            gc.fillOval(endNode.getXcoord() - 7, endNode.getYcoord() - 7, 14, 14);
            gc.setFill(Color.ORANGERED);
            gc.fillOval(endNode.getXcoord() - 3, endNode.getYcoord() - 3, 6, 6);


            int sx = startNode.getXcoord();
            int sy = startNode.getYcoord();
            int ex = endNode.getXcoord();
            int ey = endNode.getYcoord();
            gc.setStroke(Color.MEDIUMPURPLE);
            gc.setLineWidth(3);
            gc.strokeLine(sx,sy,ex,ey);
            gc.setStroke(Color.BLACK);
        }
    }
    private boolean checkNodeNull(Node node){
        boolean success = true;
        try {
            node.getXcoord();
            node.getYcoord();
        }
        catch(NullPointerException e){
            errorController.showError("Please complete all node information fields.");
            success = false;
        }
        return success;
    }
    private void setStart(Node currentLoc) {
//        boolean success = checkNodeNull(startNode);
//        if(success) {
            startNode = currentLoc;
            edgeXStart.setText("" + startNode.getXcoord());
            edgeYStart.setText("" + startNode.getYcoord());
//        }
    }

    private void setEnd(Node currentLoc) {
//        boolean success = checkNodeNull(endNode);
//        if(success) {
            endNode = currentLoc;
            edgeXEnd.setText("" + endNode.getXcoord());
            edgeYEnd.setText("" + endNode.getYcoord());
//        }
    }

    public void reset() {
        startNode = null;
        endNode = null;
        edgeYStart.clear();
        edgeXStart.clear();
        edgeYEnd.clear();
        edgeXEnd.clear();
        refreshCanvas();
    }

    public void addEdge() {
        boolean success = true;
        try{
            startNode.equals(endNode);
            endNode.equals(startNode);
        }
        catch(NullPointerException e){
            errorController.showError("Please pick two nodes.");
            success = false;
        }
        if (success) {
            edgeEditController.addEdge(new Edge(startNode, endNode));
        }
        reset();
    }
}
