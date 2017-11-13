package entity;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;

public class MapManager {
    private HashMap<String, Image> maps;

    public MapManager() {
        maps = new HashMap<>();
    }

    /**
     * Gets the map of a specified floor from the database
     * @param floor The specified floor of desired map
     * @return The map of the specified floor
     */
    public Image getMap(int floor){
        return null;
    }

    /**
     * Uploads the given picture to the database
     * @param floor The key for the picture
     * @param img The picture to be uploaded
     */
    public void uploadMapToDB(String floor, File img){

    }

    /**
     * Updates the maps object to contain the latest maps in the database
     */
    public void updateMaps(){

    }


}
