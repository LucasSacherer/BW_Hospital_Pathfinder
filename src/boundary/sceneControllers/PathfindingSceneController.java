package boundary.sceneControllers;

import Entity.Node;
import MapNavigation.MapNavigationFacade;
import Pathfinding.PathFindingFacade;
import Pathfinding.TextualDirections;
import boundary.GodController;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class PathfindingSceneController  extends AbstractMapController {
    private TextualDirections textualDirections = new TextualDirections();
    private JFXTextField originField, destinationField;
    private JFXListView textDirectionsList;

    public PathfindingSceneController(GodController g, MapNavigationFacade m, PathFindingFacade p,
                                      Label currentFloorNum, JFXTextField originField,
                                      JFXTextField destinationField, JFXListView textDirectionsList,
                                      JFXSlider zoomSlider, ScrollPane scrollPane) {
        super(g, m, p, currentFloorNum, zoomSlider, scrollPane);
        this.originField = originField;
        this.destinationField = destinationField;
        this.textDirectionsList = textDirectionsList;
    }

    public void findPath(Node o, Node d) throws IOException {
        origin = o;
        destination = d;
        if (origin == null || destination == null) return;
        goToCorrectFloor();
        centerMap();
        if (origin.toString().length() < 1) originField.setText("Unknown Name");
        else originField.setText(origin.toString());
        if (destination.toString().length() < 1) destinationField.setText("Unknown Name");
        else destinationField.setText(d.toString());
        currentPath = pathFindingFacade.getPath(origin, destination);
        textDirectionsList.setItems(textualDirections.getTextDirections(currentPath)); //todo
        refreshCanvas();
    }

    private void centerMap() {
        //TODO center the map on the path, and show both the origin and destination nodes as well
    }

    private void goToCorrectFloor() {
        currentFloor = origin.getFloor();
        imageView.setImage(mapNavigationFacade.getFloorMap(currentFloor));
        currentFloorNum.setText(currentFloor);
        refreshCanvas();
    }

    public void reversePath() throws IOException {
        this.findPath(destination, origin);
    }
}
