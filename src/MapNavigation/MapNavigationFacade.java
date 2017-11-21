package MapNavigation;

import Entity.Node;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

public class MapNavigationFacade {
    private ClickController clickController;
    private NearestPOIController nearestPOIController;
    private MapDisplayController mapDisplayController;
    private DirectoryController directoryController;

    public MapNavigationFacade(ClickController clickController, NearestPOIController nearestPOIController, MapDisplayController mapDisplayController, DirectoryController directoryController){
        this.mapDisplayController = mapDisplayController;
        this.clickController = clickController;
        this.nearestPOIController = nearestPOIController;
        this.directoryController = directoryController;
    }

    public Node getNearestNode(int x, int y, String floor){
        return clickController.getNearestNode(x,y,floor);
    }

    public Node getNearestPOI(int x, int y, String type){
        return nearestPOIController.nearestPOI(x,y,type);
    }

    public Image getFloorMap(String floor){
        return mapDisplayController.getMap(floor);
    }

    public HashMap<String,ObservableList<Node>> getDirectory(){
        return directoryController.getDirectory();
    }

    public Node getDefaultNode(){
        return directoryController.getDefaultNode();
    }

}
