package DatabaseSetup;

import java.io.*;
import java.sql.SQLException;
import java.sql.Statement;

public class TableCreator {
    private Statement statement;
    private String defaultNodesPath = "src/DatabaseSetup/defaultNodes.txt";
    private String defaultEdgesPath = "src/DefaultData/defaultEdges.txt";

    public TableCreator(Statement statement) {
        this.statement = statement;
    }

    /**
     * Creates the NODE table and inserts in all the nodes in defaulNodes.txt
     */
    public void createNodeTable() {
        //Create the table
        try {
            statement.execute("CREATE TABLE node (\n" +
                    " nodeID varchar(20) PRIMARY KEY,\n" +
                    " xcoord INTEGER NOT NULL,\n" +
                    " ycoord INTEGER NOT NULL,\n" +
                    " floor varchar(3) NOT NULL,\n" +
                    " building varchar(250) NOT NULL,\n" +
                    " nodeType varchar(250) NOT NULL,\n" +
                    " longName varchar(250) NOT NULL,\n" +
                    " shortName varchar(250) NOT NULL,\n" +
                    " teamAssigned varchar(10) NOT NULL\n)");
            System.out.println("Node table created!");
        } catch (SQLException e) {
            System.out.println("Node table already exists");
        }

        //Insert all Nodes to the table
        try {
            insertCSVToDatabase(defaultNodesPath, statement, "NODE");
        } catch (FileNotFoundException e) {
            System.out.println("Cannot find path " + defaultNodesPath);
            e.printStackTrace();
        }
    }

    /**
     * Creates the EDGE table
     */
    public void createEdgeTable() {
        //Create the table
        try {
            statement.execute("CREATE TABLE edge (\n" +
                    " edgeID VARCHAR(100) PRIMARY KEY,\n" +
                    " startNode varchar(20) NOT NULL,\n" +
                    " endNode varchar(20) NOT NULL,\n" +
                    " CONSTRAINT startNode_FK FOREIGN KEY (startNode) REFERENCES NODE(nodeID),\n" +
                    " CONSTRAINT endNode_FK FOREIGN KEY (endNode) REFERENCES NODE(nodeID))");
            System.out.println("Edge table created!");
        } catch (SQLException e) {
            System.out.println("Edge table already exists");
        }

        //Insert all Edges to the table
        try {
            insertCSVToDatabase(defaultEdgesPath, statement, "EDGE");
        } catch (FileNotFoundException e) {
            System.out.println("Cannot find path " + defaultEdgesPath);
            e.printStackTrace();
        }
    }

    /**
     * Creates the USER table
     */
    public void createUserTable() {
        //Create the table
        try {
            statement.execute("CREATE TABLE user (\n" +
                    " userID VARCHAR(100) PRIMARY KEY,\n" +
                    " userName varchar(100) NOT NULL,\n" +
                    " password varchar(100) NOT NULL,\n" +
                    " department varchar(100) NOT NULL,\n" +
                    " adminFlag varchar(10) NOT NULL");
            System.out.println("User table created!");
        } catch (SQLException e) {
            System.out.println("User table already exists");
        }
    }

    /**
     * Creates the FOODREQUEST Table
     */
    public void createFoodRequestTable() {
        try {
            statement.execute("CREATE TABLE FoodRequest (\n" +
                    " name VARCHAR(250),\n" +
                    " time TIMESTAMP,\n" +
                    " type VARCHAR(250),\n" +
                    " description VARCHAR (250),\n" +
                    " nodeID VARCHAR(20),\n" +
                    " userID VARCHAR(250), \n" +
                    " CONSTRAINT request_PK PRIMARY KEY (name, time),\n" +
                    " CONSTRAINT userID_FK FOREIGN KEY (userID) REFERENCES USER(userID),\n" +
                    " CONSTRAINT nodeID_FK FOREIGN KEY (nodeID) REFERENCES NODE(nodeID))");
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
                    " name VARCHAR(250),\n" +
                    " time TIMESTAMP,\n" +
                    " type VARCHAR(250),\n" +
                    " description VARCHAR (250),\n" +
                    " nodeID VARCHAR(20),\n" +
                    " userID VARCHAR(250), \n" +
                    " CONSTRAINT request_PK PRIMARY KEY (name, time),\n" +
                    " CONSTRAINT userID_FK FOREIGN KEY (userID) REFERENCES USER(userID),\n" +
                    " CONSTRAINT nodeID_FK FOREIGN KEY (nodeID) REFERENCES NODE(nodeID))");
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
                    " name VARCHAR(250),\n" +
                    " time TIMESTAMP,\n" +
                    " type VARCHAR(250),\n" +
                    " description VARCHAR (250),\n" +
                    " nodeID VARCHAR(20),\n" +
                    " userID VARCHAR(250), \n" +
                    " CONSTRAINT request_PK PRIMARY KEY (name, time),\n" +
                    " CONSTRAINT userID_FK FOREIGN KEY (userID) REFERENCES USER(userID),\n" +
                    " CONSTRAINT nodeID_FK FOREIGN KEY (nodeID) REFERENCES NODE(nodeID))");
            System.out.println("CleanUpRequest table created!");
        } catch (SQLException e) {
            System.out.println("CleanUpRequest table already exists");
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
                        if (table == "NODE"){
                            query = "INSERT INTO NODE VALUES ('"+array[0]+"',"+array[1]+","+array[2]+",'"+array[3]+"','"+array[4]+"','"+array[5]+"','"+array[6]+"','"+array[7]+"','"+array[8]+"')";
                        } else if (table == "EDGE"){
                            query = "INSERT INTO EDGE VALUES ('"+array[0]+"','"+array[1]+"','"+array[2]+"')";
                        }
                        dbGargoyle.executeUpdateOnDatabase(query);
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
