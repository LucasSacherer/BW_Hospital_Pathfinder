package entity;

import javax.swing.*;
import javax.swing.plaf.nimbus.State;
import java.awt.*;
import java.io.*;
import java.sql.*;
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
    public Image getMap(String floor) throws SQLException, IOException {
        //Make the hash table of the floor's image path
        HashMap<String, String> mapPaths = new HashMap<>();
        mapPaths.put("L2", "src/boundary/images/Maps/00_thelowerlevel1.png");
        mapPaths.put("L1", "src/boundary/images/Maps/00_thelowerlevel2.png");
        mapPaths.put("G", "src/boundary/images/Maps/00_thegroundfloor.png");
        mapPaths.put("1", "src/boundary/images/Maps/01_thefirstfloor.png");
        mapPaths.put("2", "src/boundary/images/Maps/02_thesecondfloor.png");
        mapPaths.put("3", "src/boundary/images/Maps/03_thethirdfloor.png");

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

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT image FROM map WHERE floor = " + floor);
        InputStream in = rs.getBinaryStream(1);
        OutputStream outputFile = null;
        try {
            outputFile = new FileOutputStream(new File(mapPaths.get(floor)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int c = 0;
        while ((c = in.read()) > -1) {
            try {
                outputFile.write(c);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new ImageIcon(this.getClass().getResource(mapPaths.get(floor))).getImage();
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
}
