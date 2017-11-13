package entity;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseSetup {
    /**
     * This method creates the NODE and EDGE tables in the database, and inserts the data from the databaseDB/text files
     * @throws SQLException Throws this exception if the database has already been created on the machine
     */
    public void createDatbase() throws SQLException {
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

        //Try to create Edge table, yell if already exists
        try {
            stmt.execute("CREATE TABLE edge (\n" +
                    " edgeID VARCHAR(30) PRIMARY KEY,\n" +
                    " startNode varchar(20) NOT NULL,\n" +
                    " endNode varchar(20) NOT NULL\n" +
                    ")");
        } catch (SQLException e){
            System.out.println ("Table already exists");
        }

        //Try to create Node table, yell if already exists
        try {
            stmt.execute("CREATE TABLE node (\n" +
                    " nodeID varchar(20) PRIMARY KEY,\n" +
                    " xcoord INTEGER NOT NULL,\n" +
                    " ycoord INTEGER NOT NULL,\n" +
                    " floor varchar(3) NOT NULL,\n" +
                    " building varchar(20) NOT NULL,\n" +
                    " nodeType varchar(10) NOT NULL,\n" +
                    " longName varchar(50) NOT NULL,\n" +
                    " shortName varchar(20) NOT NULL,\n" +
                    " teamAssigned varchar(10) NOT NULL,\n" +
                    " visitable varchar(5) NOT NULL\n " +
                    ")");
        } catch (SQLException e){
            System.out.println ("Table already exists");
        }

        //Insert all Nodes to the table
        try {
            insertStatementsFromFile("src/databaseData/nodeInserts.txt", stmt);
        } catch (FileNotFoundException e) {
            System.out.println( "The file src/databaseData/nodeInserts.txt does not exist!");
        }

        //Insert all Edges to the table
        try {
            insertStatementsFromFile("src/databaseData/edgeInserts.txt", stmt);
        } catch (FileNotFoundException e) {
            System.out.println( "The file src/databaseData/edgeInserts.txt does not exist!");
        }

        //Close shit down
        System.out.println("Database Created!");
        stmt.close();
        conn.close();
    }

    /**
     * This method takes a path to a text file of SQL statements and calls them using the stmt param
     * @param path the file path to be accessed
     * @param stmt the stmt to be executed
     */
    private void insertStatementsFromFile(String path, Statement stmt) throws FileNotFoundException {
        File file = new File(path);
        FileReader fileReader;

        //Try to open file
        try {
            fileReader = new FileReader(file);
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        }
        BufferedReader br = new BufferedReader(fileReader);

        //Try to read each line and execute it on the database
        String line;
        int failedRows = 0;
        try {
            while ((line = br.readLine()) != null){
                try {
                    stmt.execute(line);
                } catch (SQLException e){
                    failedRows++;
                }
            }
            if (failedRows != 0){ System.out.println(failedRows + " rows already exist."); }
        } catch (IOException e) {
            System.out.println("Cannot read file!");
        }
    }
}