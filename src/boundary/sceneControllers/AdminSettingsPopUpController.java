package boundary.sceneControllers;

import Editor.NodeEditController;
import Pathfinding.*;
import boundary.sceneControllers.mapEditing.KioskEditor;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;

public class AdminSettingsPopUpController {
    private PathFindingFacade pathFindingFacade;
    private NodeEditController nodeEditController;
    private Astar astar;
    private BeamSearch beam;
    private BreadthSearch breadth;
    private DepthSearch depth;
    private BestFirst best;
    private Dijkstra dijkstra;
    protected GraphicsContext gc;

    @FXML
    private JFXTextField distanceScale;

    @FXML
    private void setDistanceScale(){
        nodeEditController.setScale(distanceScale);
    }

    @FXML
    private void setMomentoTimeout(){}

    @FXML
    private void selectAstar() { pathFindingFacade.setPathfinder(astar); }

    @FXML
    private void selectBeam() { pathFindingFacade.setPathfinder(beam); }

    @FXML
    private void selectBreadth() { pathFindingFacade.setPathfinder(breadth); }

    @FXML
    private void selectDepth() { pathFindingFacade.setPathfinder(depth); }

    @FXML
    private void selectBest() { pathFindingFacade.setPathfinder(best);}

    @FXML
    private void selectDijkstras() { pathFindingFacade.setPathfinder(dijkstra);}

    @FXML
    private void selectScenicRoute() { }

    @FXML
    private void selectQuickScenicRoute() { }


    public AdminSettingsPopUpController(NodeEditController nodeEditController, PathFindingFacade pathFindingFacade, Astar astar, BeamSearch beam, BreadthSearch breadth, DepthSearch depth, BestFirst best, Dijkstra dijkstra ) {
        this.nodeEditController = nodeEditController;
        this.pathFindingFacade = pathFindingFacade;
        this.astar = astar;
        this.beam = beam;
        this.breadth = breadth;
        this.depth = depth;
        this.best = best;
        this.dijkstra = dijkstra;
    }
}
