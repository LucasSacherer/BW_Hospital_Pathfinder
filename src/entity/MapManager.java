package entity;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
     * @param image The picture to be uploaded
     */
    public void uploadMapToDB(String floor, File image) throws SQLException {
        //DB Info
        String dbURL = "jdbc:derby://localhost:1527/bw_pathfinder_db;create=true;user=granite_gargoyle;password=wong";
        final String driver = "org.apache.derby.jdbc.ClientDriver";

        //Try to load drivers
        try {
            Class.forName(driver).newInstance();
        }catch (Exception ex){
            System.out.println("Failed to find Embedded JavaDB driver!");
            ex.printStackTrace();
        }

        //Create connection and statement to be run
        Connection conn = DriverManager.getConnection(dbURL);

        //Create the initial statements to insert the images to the database
        FileInputStream fis = null;
        PreparedStatement psmnt = null;

        //Make a FileInputStream from the image
        try {
            fis = new FileInputStream(image);
        } catch (FileNotFoundException e) {
            System.out.println("The file " + image.toPath() + " not found");
        }

        //Create the update statement
        try {
            psmnt = conn.prepareStatement("UPDATE MAP SET image = ? WHERE floor = ?");
            psmnt.setBinaryStream(1, fis, (int) (image.length()));
            psmnt.setString(2, floor);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //Execute the statement
        try {
            psmnt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(psmnt.toString() + " failed to execute");
        }
        //Close the statement
        try {
            psmnt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Updates the maps object to contain the latest maps in the database
     */
    public void updateMaps(){

    }


}
