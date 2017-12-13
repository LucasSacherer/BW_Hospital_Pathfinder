package boundary.sceneControllers;

import Editor.NodeEditController;
import Pathfinding.*;
import boundary.sceneControllers.mapEditing.KioskEditor;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
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
    private JFXToggleButton bfs, dfs, bestButton, djikstraButton, astarButton, beamButton;

    @FXML
    private JFXTextField distanceScale;

    @FXML
    private void setDistanceScale(){
        nodeEditController.setScale(distanceScale);
    }

    @FXML
    private void setMomentoTimeout(){}

    @FXML
    private void initialize() {
       String currentPath = pathFindingFacade.getPathFinder();
       if (currentPath != null) {
           if (currentPath.equals("astar")) astarButton.setSelected(true);
           if (currentPath.equals("beam")) beamButton.setSelected(true);
           if (currentPath.equals("breadth")) bfs.setSelected(true);
           if (currentPath.equals("depth")) dfs.setSelected(true);
           if (currentPath.equals("best")) bestButton.setSelected(true);
           if (currentPath.equals("djikstra")) djikstraButton.setSelected(true);
       }
    }

    @FXML
    private void selectAstar() {
        pathFindingFacade.setPathfinder(astar);
        pathFindingFacade.setPathFinder("astar");
    }

    @FXML
    private void selectBeam() {
        pathFindingFacade.setPathfinder(beam);
        pathFindingFacade.setPathFinder("beam");
    }

    @FXML
    private void selectBreadth() {
        pathFindingFacade.setPathfinder(breadth);
        pathFindingFacade.setPathFinder("breadth");
    }

    @FXML
    private void selectDepth() {
        pathFindingFacade.setPathfinder(depth);
        pathFindingFacade.setPathFinder("depth");
    }

    @FXML
    private void selectBest() {
        pathFindingFacade.setPathfinder(best);
        pathFindingFacade.setPathFinder("best");
    }

    @FXML
    private void selectDijkstras() {
        pathFindingFacade.setPathfinder(dijkstra);
        pathFindingFacade.setPathFinder("djikstra");
    }

    public AdminSettingsPopUpController(NodeEditController nodeEditController, PathFindingFacade pathFindingFacade,
                                        Astar astar, BeamSearch beam, BreadthSearch breadth, DepthSearch depth,
                                        BestFirst best, Dijkstra dijkstra ) {
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
