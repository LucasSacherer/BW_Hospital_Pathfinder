package DatabaseSetup;

import java.io.*;
import java.sql.SQLException;
import java.sql.Statement;

public class TableCreator {
    private Statement statement;
    private String defaultNodesPath = "src/DatabaseSetup/defaultNodes.txt";
    private String defaultEdgesPath = "src/DatabaseSetup/defaultEdges.txt";

    public TableCreator(Statement statement) {
        this.statement = statement;
    }

    /**
     * Creates the NODE table and inserts in all the nodes in defaulNodes.txt
     */
    public void createNodeTable() {
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
                insertCSVToDatabase(defaultNodesPath, statement, "NODE");
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
    public void createEdgeTable() {
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
                insertCSVToDatabase(defaultEdgesPath, statement, "EDGE");
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
                    " userName varchar(100) NOT NULL,\n" +
                    " password varchar(100) NOT NULL,\n" +
                    " department varchar(100) NOT NULL,\n" +
                    " adminFlag varchar(10) NOT NULL\n)");
            System.out.println("KioskUser table created!");
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
            e.printStackTrace();
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
                    " nodeID VARCHAR(20) NOT NULL,\n" +
                    " userID VARCHAR(100) NOT NULL, \n" +
                    " CONSTRAINT interpreterRequest_PK PRIMARY KEY (name, timeCreated),\n" +
                    " CONSTRAINT interpreterUserID_FK FOREIGN KEY (userID) REFERENCES KIOSKUSER(userID),\n" +
                    " CONSTRAINT interpreterNodeID_FK FOREIGN KEY (nodeID) REFERENCES NODE(nodeID))");
            System.out.println("InterpreterRequest table created!");
        } catch (SQLException e) {
            System.out.println("InterpreterRequest table already exists");
            e.printStackTrace();
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
            e.printStackTrace();
        }
    }

    /**
     * Reads a csv file of type (node or edge) and creates insert statements and executes them to the database
     * @param path
     * @param stmt
     * @param table
     * @throws FileNotFoundException
     */
    public void insertCSVToDatabase(String path, Statement stmt, String table) throws FileNotFoundException {
        File file = new File(path);
        FileReader fileReader = new FileReader(file);
        BufferedReader br = new BufferedReader(fileReader);
        DatabaseGargoyle dbGargoyle = new DatabaseGargoyle();
        String line, query = "default null query, if I am called then something is wrong";

        try {
            while ((line = br.readLine()) != null){
                try {
                    if (line != null){
                        String[] array = line.split(",");
                        //Execute query to database
                        if (table.equals("NODE")){
                            query = "INSERT INTO NODE VALUES ('"+array[0]+"',"+array[1]+","+array[2]+",'"+array[3]+"','"+array[4]+"','"+array[5]+"','"+array[6]+"','"+array[7]+"','"+array[8]+"')";
                        } else if (table.equals("EDGE")){
                            query = "INSERT INTO EDGE VALUES ('"+array[0]+"','"+array[1]+"','"+array[2]+"')";
                        }
                        dbGargoyle.executeUpdateOnDatabase(query, statement);
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
