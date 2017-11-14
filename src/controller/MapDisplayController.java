package controller;

import entity.MapManager;
import javafx.scene.image.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;

public class MapDisplayController {

    private final MapManager mapManager;

    public MapDisplayController() {
        mapManager = new MapManager();
    }

    public Image getMap(String floor) throws IOException, SQLException {
        return new Image(new FileInputStream(new File(this.mapManager.getMap(floor))));
    }
}
