package entity;

import java.io.*;
import java.sql.*;
import java.util.HashMap;

public class MapManager {
    private HashMap<String, String> maps;

    public MapManager() {
        //Create the object
        maps = new HashMap<>();

        //Add all paths to the object
        maps.put("L2", "src/boundary/images/MapsForUI/L2.png");
        maps.put("L1", "src/boundary/images/MapsForUI/L1.png");
        maps.put("G", "src/boundary/images/MapsForUI/G.png");
        maps.put("1", "src/boundary/images/MapsForUI/1.png");
        maps.put("2", "src/boundary/images/MapsForUI/2.png");
        maps.put("3", "src/boundary/images/MapsForUI/3.png");
    }

    /**
     * Gets the map of a specified floor from the database
     * @param floor The specified floor of desired map
     * @return The map of the specified floor
     */
    public String getMap(String floor) throws SQLException, IOException {
        return this.maps.get(floor);
    }

    /**
     * Uploads the given picture to the database and also writes it to the correct MapForUI
     * @param floor The key for the picture
     * @param image The picture to be uploaded
     */
    public void uploadMapToDB(String floor, File image) throws SQLException {
        //DB Connection shit
        String dbURL = "jdbc:derby://localhost:1527/bw_pathfinder_db;create=true;user=granite_gargoyle;password=wong";
        final String driver = "org.apache.derby.jdbc.ClientDriver";
        try {
            Class.forName(driver).newInstance();
        }catch (Exception ex){
            System.out.println("Failed to find Embedded JavaDB driver!");
            ex.printStackTrace();
        }
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

        //Add the fresh map to the MapsForUI Map folder
        try {
            addToUIMaps(floor, conn);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Close connection
        conn.close();

    }

    /**
     * HELPER FUNCTION: Takes the specified (by floor) image from the database and stores it in the MapsForUI
     * folder under the correct floor
     * @param floor The specified floor
     */
    private void addToUIMaps(String floor, Connection conn) throws SQLException, IOException {
        //Make the hash table of the floor's image path
        HashMap<String, String> mapPaths = new HashMap<>();
        mapPaths.put("L2", "src/boundary/images/MapsForUI/L2.png");
        mapPaths.put("L1", "src/boundary/images/MapsForUI/L1.png");
        mapPaths.put("G", "src/boundary/images/MapsForUI/G.png");
        mapPaths.put("1", "src/boundary/images/MapsForUI/1.png");
        mapPaths.put("2", "src/boundary/images/MapsForUI/2.png");
        mapPaths.put("3", "src/boundary/images/MapsForUI/3.png");

        //Get the binary code from the database for the specified floor and write it into the
        //correct MapsForUI map.
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT image FROM map WHERE floor = " + floor);
        InputStream in = rs.getBinaryStream(1);
        OutputStream outputFile = null;
        try {
            outputFile = new FileOutputStream(new File(mapPaths.get(floor)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int i;
        while ((i = in.read()) > -1) {
            try {
                outputFile.write(i);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        outputFile.close();
        in.close();
        stmt.close();
    }

}
