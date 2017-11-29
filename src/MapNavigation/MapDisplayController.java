package MapNavigation;

import Database.ImageManager;
import javafx.scene.image.Image;

import java.io.IOException;
import java.sql.SQLException;

public class MapDisplayController {

    private final ImageManager imageManager;

    public MapDisplayController() {
        imageManager = new ImageManager();
    }

    /**
     * Gets the map of the specified floor and turns it into an image
     * @param floor
     * @return An image of the specified floor (from MapsForUI)
     * @throws IOException
     * @throws SQLException
     */
    protected Image getMap(String floor) {
        return imageManager.getImage(floor);
    }
}
