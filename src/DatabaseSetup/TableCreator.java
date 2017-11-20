package DatabaseSetup;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class TableCreator {
    private Statement statement;
    private String defaultNodesPath = "src/DatabaseSetup/defaultNodes.txt";
    private String defaultEdgesPath = "src/DatabaseSetup/defaultEdges.txt";
    private String defaultUsersPath = "src/DatabaseSetup/defaultUsers.txt";

    public TableCreator(Statement statement) {
        this.statement = statement;
    }

    /**
     * Creates the NODE table and inserts in all the nodes in defaulNodes.txt
     */
    public void createNodeTable(Connection connection) {
        //Create the table and add in the default nodes
        try {
            statement.execute("CREATE TABLE node (\n" +
                    " nodeID varchar(20) PRIMARY KEY,\n" +
                    " xcoord INTEGER NOT NULL,\n" +
                    " ycoord INTEGER NOT NULL,\n" +
                    " floor varchar(100) NOT NULL,\n" +
                    " building varchar(250) NOT NULL,\n" +
                    " nodeType varchar(250) NOT NULL,\n" +
                    " longName varchar(250) NOT NULL,\n" +
                    " shortName varchar(250) NOT NULL,\n" +
                    " teamAssigned varchar(10) NOT NULL\n)");
            System.out.println("Node table created!");
            //Insert all Nodes to the table

            try {
                insertCSVToDatabase(defaultNodesPath, connection,"NODE");
            } catch (FileNotFoundException e) {
                System.out.println("Cannot find path " + defaultNodesPath);
                e.printStackTrace();
            }
        } catch (SQLException e) {
            System.out.println("Node table already exists");
        }
    }

    /**
     * Creates the EDGE table
     */
    public void createEdgeTable(Connection connection) {
        //Create the table and insert in all the default edges
        try {
            statement.execute("CREATE TABLE edge (\n" +
                    " edgeID VARCHAR(100) PRIMARY KEY,\n" +
                    " startNode varchar(20) NOT NULL,\n" +
                    " endNode varchar(20) NOT NULL,\n" +
                    " CONSTRAINT startNode_FK FOREIGN KEY (startNode) REFERENCES NODE(nodeID),\n" +
                    " CONSTRAINT endNode_FK FOREIGN KEY (endNode) REFERENCES NODE(nodeID))");
            System.out.println("Edge table created!");
            //Insert all Edges to the table
            try {
                insertCSVToDatabase(defaultEdgesPath, connection,"EDGE");
            } catch (FileNotFoundException e) {
                System.out.println("Cannot find path " + defaultEdgesPath);
                e.printStackTrace();
            }
        } catch (SQLException e) {
            System.out.println("Edge table already exists");
        }
    }

    /**
     * Creates the KIOSKUSER table
     */
    public void createKioskUserTable(Connection connection) {
        //Create the table
        try {
            statement.execute("CREATE TABLE kioskUser (\n" +
                    " userID VARCHAR(100) PRIMARY KEY,\n" +
                    " userName varchar(100) NOT NULL,\n" +
                    " password varchar(100) NOT NULL,\n" +
                    " adminFlag varchar(10) NOT NULL,\n" +
                    " department varchar(100) NOT NULL\n)");
            System.out.println("KioskUser table created!");
            //Insert all Users to the table
            try {
                insertCSVToDatabase(defaultUsersPath, connection,"KIOSKUSER");
            } catch (FileNotFoundException e) {
                System.out.println("Cannot find path " + defaultUsersPath);
                e.printStackTrace();
            }
        } catch (SQLException e) {
            System.out.println("KioskUser table already exists");
        }
    }

    /**
     * Creates the FOODREQUEST Table
     */
    public void createFoodRequestTable() {
        try {
            statement.execute("CREATE TABLE FoodRequest (\n" +
                    " name VARCHAR(250) NOT NULL,\n" +
                    " timeCreated TIMESTAMP NOT NULL,\n" +
                    " timeCompleted TIMESTAMP NOT NULL,\n" +
                    " type VARCHAR(250) NOT NULL,\n" +
                    " description VARCHAR(250) NOT NULL,\n" +
                    " nodeID VARCHAR(20) NOT NULL,\n" +
                    " userID VARCHAR(100) NOT NULL, \n" +
                    " CONSTRAINT foodRequest_PK PRIMARY KEY (name, timeCreated),\n" +
                    " CONSTRAINT foodUserID_FK FOREIGN KEY (userID) REFERENCES KIOSKUSER(userID),\n" +
                    " CONSTRAINT foodNodeID_FK FOREIGN KEY (nodeID) REFERENCES NODE(nodeID))");
            System.out.println("FoodRequest table created!");
        } catch (SQLException e) {
            System.out.println("FoodRequest table already exists");
        }
    }

    /**
     * Creates the INTERPRETERREQUEST Table
     */
    public void createInterpreterRequestTable() {
        try {
            statement.execute("CREATE TABLE InterpreterRequest (\n" +
                    " name VARCHAR(250) NOT NULL,\n" +
                    " timeCreated TIMESTAMP NOT NULL,\n" +
                    " timeCompleted TIMESTAMP NOT NULL,\n" +
                    " type VARCHAR(250) NOT NULL,\n" +
                    " description VARCHAR(250) NOT NULL,\n" +
                    " language VARCHAR(250) NOT NULL,\n" +
                    " nodeID VARCHAR(20) NOT NULL,\n" +
                    " userID VARCHAR(100) NOT NULL, \n" +
                    " CONSTRAINT interpreterRequest_PK PRIMARY KEY (name, timeCreated),\n" +
                    " CONSTRAINT interpreterUserID_FK FOREIGN KEY (userID) REFERENCES KIOSKUSER(userID),\n" +
                    " CONSTRAINT interpreterNodeID_FK FOREIGN KEY (nodeID) REFERENCES NODE(nodeID))");
            System.out.println("InterpreterRequest table created!");
        } catch (SQLException e) {
            System.out.println("InterpreterRequest table already exists");
        }
    }

    /**
     * Creates the CLEANUPREQUEST Table
     */
    public void createCleanUpRequestTable() {
        try {
            statement.execute("CREATE TABLE CleanUpRequest (\n" +
                    " name VARCHAR(250) NOT NULL,\n" +
                    " timeCreated TIMESTAMP NOT NULL,\n" +
                    " timeCompleted TIMESTAMP NOT NULL,\n" +
                    " type VARCHAR(250) NOT NULL,\n" +
                    " description VARCHAR(250) NOT NULL,\n" +
                    " nodeID VARCHAR(20) NOT NULL,\n" +
                    " userID VARCHAR(100) NOT NULL, \n" +
                    " CONSTRAINT cleanUpRequest_PK PRIMARY KEY (name, timeCreated),\n" +
                    " CONSTRAINT cleanUpUserID_FK FOREIGN KEY (userID) REFERENCES KIOSKUSER(userID),\n" +
                    " CONSTRAINT cleanUpNodeID_FK FOREIGN KEY (nodeID) REFERENCES NODE(nodeID))");
            System.out.println("CleanUpRequest table created!");
        } catch (SQLException e) {
            System.out.println("CleanUpRequest table already exists");
        }
    }

    /**
     * Creates the FOODORDER table
     */
    public void createFoodOrdersTable() {
        try {
            statement.execute("CREATE TABLE foodOrder (\n" +
                    " requestName VARCHAR(250) NOT NULL,\n" +
                    " timeCreated TIMESTAMP NOT NULL,\n" +
                    " foodItem VARCHAR(250) NOT NULL,\n" +
                    " quantity INTEGER NOT NULL,\n" +
                    " CONSTRAINT foodOrder_PK PRIMARY KEY(requestName, foodItem),\n" +
                    " CONSTRAINT foodOrder_FK FOREIGN KEY (requestName, timeCreated) REFERENCES FoodRequest(name, timeCreated))");
            System.out.println("FoodOrder table created!");
        } catch (SQLException e) {
            System.out.println("FoodOrder table already exists");
        }
    }

    /**
     * Create SETTINGS table
     */
    public void createSettingsTable() {
        try {
            statement.execute("CREATE TABLE settings (\n" +
                    " string1 VARCHAR(250) PRIMARY KEY,\n" +
                    " string2 VARCHAR(250) NOT NULL\n)");
            System.out.println("Settings table created!");
        } catch (SQLException e) {
            System.out.println("Settings table already exists");
        }
    }

    /**
     * Reads a csv file of type (node or edge) and creates insert statements and executes them to the database
     * @param path
     * @param table
     * @throws FileNotFoundException
     */
    public void insertCSVToDatabase(String path, Connection connection, String table) throws FileNotFoundException {
        File file = new File(path);
        FileReader fileReader = new FileReader(file);
        BufferedReader br = new BufferedReader(fileReader);
        DatabaseGargoyle dbGargoyle = new DatabaseGargoyle();
        String line;
        PreparedStatement query = null;

        try {
            while ((line = br.readLine()) != null){
                try {
                    if (line != null){
                        String[] array = line.split(",");
                        //Execute query to database
                        try {
                            if (table.equals("NODE")){
                                query = connection.prepareStatement("INSERT INTO NODE VALUES (?,?,?,?,?,?,?,?,?)");
                                query.setString(1, array[0]);
                                query.setInt(2, Integer.parseInt(array[1]));
                                query.setInt(3, Integer.parseInt(array[2]));
                                query.setString(4,array[3]);
                                query.setString(5,array[4]);
                                query.setString(6,array[5]);
                                query.setString(7,array[6]);
                                query.setString(8,array[7]);
                                query.setString(9,array[8]);
                            } else if (table.equals("EDGE")){
                                query = connection.prepareStatement("INSERT INTO EDGE VALUES (?,?,?)");
                                query.setString(1, array[0]);
                                query.setString(2,array[1]);
                                query.setString(3,array[2]);
                            } else if (table.equals("KIOSKUSER")){
                                query = connection.prepareStatement("INSERT INTO KIOSKUSER VALUES (?,?,?,?,?)");
                                query.setString(1, array[0]);
                                query.setString(2,array[1]);
                                query.setString(3,array[2]);
                                query.setString(4,array[3]);
                                query.setString(5,array[4]);
                            }
                            query.executeUpdate();
                        } catch (SQLException e){
                            System.out.println("Failed to execute: " + query.toString());
                            e.printStackTrace();
                        }
                    }
                } finally {
                    if (br == null) {
                        try {
                            br.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
