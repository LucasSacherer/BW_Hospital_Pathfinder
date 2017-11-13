package entity;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;

public class MapManager {
    private HashMap<String, Image> maps;

    public MapManager(HashMap<String, Image> maps) {
        this.maps = maps;
    }

    /**
     * Gets the map of a specified floor from the database
     * @param floor The specified floor of desired map
     * @return The map of the specified floor
     */
    public Image getMap(int floor){
        return null;
    }


}
