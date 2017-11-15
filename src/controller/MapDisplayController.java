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

    /**
     * Gets the map of the specified floor and turns it into an image
     * @param floor
     * @return An image of the specified floor (from MapsForUI)
     * @throws IOException
     * @throws SQLException
     */
    public Image getMap(String floor) throws IOException, SQLException {
        return new Image(new FileInputStream(new File(this.mapManager.getMap(floor))));
    }
}
