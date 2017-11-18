package controller;

import DatabaseSetup.DatabaseSetup;
import Iteration1CodeWeMayNotNeed.MapManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MapUploadController {
    public void deleteEdgesAndNodes(String floor) throws SQLException {
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

        //Delete all nodes from table that contain a node on the selected floor
        stmt.execute("DELETE FROM EDGE WHERE STARTNODE IN (SELECT NODEID FROM NODE WHERE FLOOR = '" + floor + "')");
        stmt.execute("DELETE FROM EDGE WHERE ENDNODE IN (SELECT NODEID FROM NODE WHERE FLOOR = '" + floor + "')");
        stmt.execute("DELETE FROM NODE WHERE FLOOR = '" + floor +"'");

        stmt.close();
        conn.close();
    }

    public void uploadMap(String imagePath, String floor) {
        MapManager mapManager = new MapManager();
        File image = new File(imagePath);
        try {
            mapManager.uploadMapToDB(floor, image);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertNodesAndEdges(String nodePath, String edgePath) throws SQLException {
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

        //Insert the node and edge csv to the database
        DatabaseSetup db = new DatabaseSetup();
        try {
            db.insertCSVToDatabase(nodePath, stmt, "NODE");
            db.insertCSVToDatabase(edgePath, stmt, "EDGE");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
