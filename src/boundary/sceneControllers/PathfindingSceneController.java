package boundary.sceneControllers;

import Entity.Node;
import MapNavigation.MapNavigationFacade;
import Pathfinding.PathFindingFacade;
import Pathfinding.TextualDirections;
import boundary.GodController;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class PathfindingSceneController  extends AbstractMapController {
    private TextualDirections textualDirections = new TextualDirections();
    private JFXTextField originField, destinationField;
    private JFXListView textDirectionsList;

    public PathfindingSceneController(GodController g, ImageView i, Pane mapPane, Canvas canvas, MapNavigationFacade m,
                                      PathFindingFacade p, Label currentFloorNum, JFXTextField originField,
                                      JFXTextField destinationField, JFXListView textDirectionsList) {
        super(g, i, mapPane, canvas, m, p, currentFloorNum);
        this.originField = originField;
        this.destinationField = destinationField;
        this.textDirectionsList = textDirectionsList;
    }

    public void floorUp() {
    }

    public void floorDown() {
    }

    public void zoom() {
    }

    public void findPath(Node o, Node d) throws IOException {
        origin = o;
        destination = d;
        if (origin == null || destination == null) return;
        originField.setText(o.toString());
        destinationField.setText(destination.toString());
        currentPath = pathFindingFacade.getPath(origin, destination);
        textDirectionsList.setItems(textualDirections.getTextDirections(currentPath)); //todo
        refreshCanvas();
    }
}
