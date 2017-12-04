package boundary.sceneControllers;

import Entity.Node;
import MapNavigation.MapNavigationFacade;
import Pathfinding.PathFindingFacade;
import boundary.GodController;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class PathfindingSceneController  extends AbstractMapController {
    public PathfindingSceneController(GodController g, ImageView i, Pane mapPane, Canvas canvas, MapNavigationFacade m,
                                      PathFindingFacade p, Label currentFloorNum) {
        super(g, i, mapPane, canvas, m, p, currentFloorNum);
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
        currentPath = pathFindingFacade.getPath(origin, destination);
        refreshCanvas();
    }
}
