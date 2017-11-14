package controller;


import entity.MapManager;
import javafx.scene.image.Image;

public class MapDisplayController {

    private final MapManager mapManager;

    public MapDisplayController(MapManager newManager){
        this.mapManager = newManager;
    }

    public Image getMap(String floor){
        return mapManager.getMap(floor);
    }
}
