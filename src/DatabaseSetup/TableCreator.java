package DatabaseSetup;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class TableCreator {
    private Statement statement;
    private String defaultNodesPath = "/DatabaseSetup/defaultNodes.txt";
    private String defaultEdgesPath = "/DatabaseSetup/defaultEdges.txt";

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
    public void createKioskUserTable() {
        //Create the table
        try {
            statement.execute("CREATE TABLE kioskUser (\n" +
                    " userID VARCHAR(100) PRIMARY KEY,\n" +
                    " userName varchar(100) UNIQUE,\n" +
                    " password varchar(100) NOT NULL,\n" +
                    " adminFlag varchar(10) NOT NULL,\n" +
                    " department varchar(100) NOT NULL\n)");
            System.out.println("KioskUser table created!");
            statement.executeUpdate("INSERT INTO KIOSKUSER VALUES ('admin1', 'admin1', 'admin1', true, 'Interpreter')");
            statement.executeUpdate("INSERT INTO KIOSKUSER VALUES ('admin2', 'admin2', 'admin2', true, 'Food')");
            statement.executeUpdate("INSERT INTO KIOSKUSER VALUES ('janitor1', 'janitor1', 'janitor1', false, 'Janitorial')");
            statement.executeUpdate("INSERT INTO KIOSKUSER VALUES ('staff1', 'staff1', 'staff1', false, 'Food')");
            statement.executeUpdate("INSERT INTO KIOSKUSER VALUES ('badUser', 'badUser', 'badUser', false, 'Janitorial')");
            statement.executeUpdate("INSERT INTO KIOSKUSER VALUES ('staff', 'staff', 'staff', false, 'Interpreter')");
            statement.executeUpdate("INSERT INTO KIOSKUSER VALUES ('admin', 'admin', 'admin', true, 'Janitorial')");
        } catch (SQLException e) {
            System.out.println("KioskUser table already exists");
            //e.printStackTrace();
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
            statement.executeUpdate("INSERT INTO FOODREQUEST VALUES ('food1','1960-01-01 23:03:20','1960-01-01 23:03:20','type1', 'description1','GRETL03501', 'admin1')");
            statement.executeUpdate("INSERT INTO FOODREQUEST VALUES ('food2','1960-01-01 23:03:20','1961-01-01 23:03:20','type2', 'description1','GSTAI00501', 'admin2')");
            statement.executeUpdate("INSERT INTO FOODREQUEST VALUES ('deleteme','1960-01-01 23:03:20','1960-01-01 23:03:20','type3', 'description1','GSTAI00501', 'badUser')");

        } catch (SQLException e) {
            System.out.println("FoodRequest table already exists");
            //e.printStackTrace();
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
            statement.executeUpdate("INSERT INTO INTERPRETERREQUEST VALUES ('completed','1960-01-01 23:03:20','1960-02-01 23:03:20','type1', 'description1','spanish','GCONF02001', 'admin1')");
            statement.executeUpdate("INSERT INTO INTERPRETERREQUEST VALUES ('not completed','1960-01-01 23:03:20','1960-01-01 23:03:20','type2', 'description1','japanese','GDEPT01901', 'admin2')");
            statement.executeUpdate("INSERT INTO INTERPRETERREQUEST VALUES ('deleteme','1960-01-01 23:03:20','1960-01-01 23:03:20','type2', 'description1','japanese','GDEPT01901', 'badUser')");
        } catch (SQLException e) {
            System.out.println("InterpreterRequest table already exists");
            //e.printStackTrace();
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
            statement.executeUpdate("INSERT INTO CLEANUPREQUEST VALUES ('completed','1960-01-01 23:03:20','1960-02-01 23:03:20','type1', 'description1','GLABS015L2', 'admin1')");
            statement.executeUpdate("INSERT INTO CLEANUPREQUEST VALUES ('completed2','1960-01-01 23:03:20','1960-01-01 23:03:20','type1', 'description1','GLABS015L2', 'admin1')");
            statement.executeUpdate("INSERT INTO CLEANUPREQUEST VALUES ('not completed','1960-01-01 23:03:20','1960-01-01 23:03:20','type2', 'description2','GDEPT00403', 'janitor1')");
            statement.executeUpdate("INSERT INTO CLEANUPREQUEST VALUES ('deleteme','1960-01-01 23:03:20','1960-01-01 23:03:20','type2', 'description2','GDEPT00403', 'badUser')");
        } catch (SQLException e) {
            System.out.println("CleanUpRequest table already exists");
            //e.printStackTrace();
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
                    " CONSTRAINT foodOrder_FK FOREIGN KEY (requestName, timeCreated) REFERENCES FoodRequest(name, timeCreated))");
            System.out.println("FoodOrder table created!");
            statement.executeUpdate("INSERT INTO FOODORDER VALUES ('food1','1960-01-01 23:03:20','cheeseburger')");
            statement.executeUpdate("INSERT INTO FOODORDER VALUES ('food1','1960-01-01 23:03:20','cheeseburger')");
            statement.executeUpdate("INSERT INTO FOODORDER VALUES ('food1','1960-01-01 23:03:20','lasagna')");
            statement.executeUpdate("INSERT INTO FOODORDER VALUES ('food2','1960-01-01 23:03:20','milk')");
            statement.executeUpdate("INSERT INTO FOODORDER VALUES ('food2','1960-01-01 23:03:20','cheese')");
        } catch (SQLException e) {
            System.out.println("FoodOrder table already exists");
            //e.printStackTrace();
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
            statement.executeUpdate("INSERT INTO SETTINGS VALUES ('Default Node','GHALL03802')");
            statement.executeUpdate("INSERT INTO SETTINGS VALUES ('Distance Scale','1.76')");
            statement.executeUpdate("INSERT INTO SETTINGS VALUES ('Memento Delay','0.5')");

        } catch (SQLException e) {
            System.out.println("Settings table already exists");
        }
    }

    /**
     * Create ADMINLOG table
     */
    public void createAdminLogTable() {
        try {
            statement.execute("CREATE TABLE adminlog (\n" +
                    "userID VARCHAR(100) NOT NULL, \n" +
                    "action VARCHAR(250) NOT NULL, \n" +
                    "time TIMESTAMP NOT NULL\n)");
            System.out.println("AdminLog table created!");
        } catch (SQLException e) {
            System.out.println("AdminLog table already exists");
            //e.printStackTrace();
        }
    }

    /**
     * Create PATHFINDINGLOG table
     */
    public void createPathfindingLogTable() {
        try{
            statement.execute("CREATE TABLE pathfindinglog (\n" +
                    "nodeID VARCHAR(20) NOT NULL, \n" +
                    "time TIMESTAMP NOT NULL, \n" +
                    "destination VARCHAR(5) NOT NULL, \n" +
                    "pathID VARCHAR(250) NOT NULL\n)");
            System.out.println("PathfindingLog table created!");
        } catch (SQLException e){
            System.out.println("Pathfinding table already exists");
            //e.printStackTrace();
        }
    }

    /**
     * Create GOOGLENODE table
     */
    public void createGoogleNodeTable() {
        try {
            statement.execute("CREATE TABLE googlenode (\n" +
                    "name VARCHAR(250) NOT NULL, \n" +
                    "url VARCHAR(1000) NOT NULL, \n" +
                    "xcoord INTEGER NOT NULL, \n" +
                    "ycoord INTEGER NOT NULL, \n" +
                    "floor VARCHAR(250) NOT NULL\n)");
            System.out.println("GoogleNode table created!");
            statement.executeUpdate("INSERT INTO GOOGLENODE VALUES ('FrancisStreet','https://goo.gl/maps/CB3RET9g6Gq',1451,1616,'1')");
            statement.executeUpdate("INSERT INTO GOOGLENODE VALUES ('BCHSouth','https://goo.gl/maps/o1jTB9vEJW42',1955,33,'1')");
            statement.executeUpdate("INSERT INTO GOOGLENODE VALUES ('Francis Street Intersection','https://goo.gl/maps/Aixmg3zNeD72',4611,1897,'2')");
            statement.executeUpdate("INSERT INTO GOOGLENODE VALUES ('45 Francis Parking','https://goo.gl/maps/8aSETNujgh92',3179,1631,'2')");
            statement.executeUpdate("INSERT INTO GOOGLENODE VALUES ('Pats Place Entrance','https://goo.gl/maps/Ks4NQfE1Nf22',4154,365,'2')");
            statement.executeUpdate("INSERT INTO GOOGLENODE VALUES ('Francis Street Entrance','https://goo.gl/maps/GjULjhS8Tp92',1838,1613,'1')");
            statement.executeUpdate("INSERT INTO GOOGLENODE VALUES ('Francis Street Valet','https://goo.gl/maps/AU4AEfacTdu',1082,1696,'1')");
        } catch (SQLException e) {
            System.out.println("GoogleNode table already exists");
            //e.printStackTrace();
        }
    }

    /**
     * Reads a csv file of type (node or edge) and creates insert statements and executes them to the database
     * @param path
     * @param table
     * @throws FileNotFoundException
     */
    private void insertCSVToDatabase(String path, Connection connection, String table) throws FileNotFoundException {
        /*
        File file = new File(path);
        FileReader fileReader = new FileReader(file);
        BufferedReader br = new BufferedReader(fileReader);
        */
        InputStream file = TableCreator.class.getResourceAsStream(path);
        Scanner br = new Scanner(file);
        String line;
        PreparedStatement query = null;

        while (br.hasNext()){
            line = br.nextLine();
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
                } else if (table.equals("EDGE")) {
                    query = connection.prepareStatement("INSERT INTO EDGE VALUES (?,?,?)");
                    query.setString(1, array[0]);
                    query.setString(2, array[1]);
                    query.setString(3, array[2]);
                }
                query.executeUpdate();
            } catch (SQLException e){
                System.out.println("Failed to execute: " + query.toString());
                e.printStackTrace();
            }
        }


    }
}
