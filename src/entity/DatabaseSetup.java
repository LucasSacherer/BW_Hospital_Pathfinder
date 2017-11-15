package entity;

import java.io.*;
import java.sql.*;
import java.util.HashMap;

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

        //Try to create Node table, yell if already exists
        try {
            stmt.execute("CREATE TABLE node (\n" +
                    " nodeID varchar(20) PRIMARY KEY,\n" +
                    " xcoord INTEGER NOT NULL,\n" +
                    " ycoord INTEGER NOT NULL,\n" +
                    " floor varchar(3) NOT NULL,\n" +
                    " building varchar(20) NOT NULL,\n" +
                    " nodeType varchar(10) NOT NULL,\n" +
                    " longName varchar(100) NOT NULL,\n" +
                    " shortName varchar(50) NOT NULL,\n" +
                    " teamAssigned varchar(10) NOT NULL,\n" +
                    " visitable varchar(5) NOT NULL\n " +
                    ")");
        } catch (SQLException e){
            System.out.println ("Node table already exists");
        }

        //Try to create Edge table, yell if already exists
        try {
            stmt.execute("CREATE TABLE edge (\n" +
                    " edgeID VARCHAR(30) PRIMARY KEY,\n" +
                    " startNode varchar(20) NOT NULL,\n" +
                    " endNode varchar(20) NOT NULL,\n" +
                    " CONSTRAINT startNode_FK FOREIGN KEY (startNode) REFERENCES NODE(nodeID),\n" +
                    " CONSTRAINT endNode_FK FOREIGN KEY (endNode) REFERENCES NODE(nodeID))");
        } catch (SQLException e){
            System.out.println ("Edge table already exists");
        }

        //Try to create Request table, yell if already exists
        try {
            stmt.execute("CREATE TABLE request (\n" +
                    " name VARCHAR(50),\n" +
                    " time TIMESTAMP,\n" +
                    " type VARCHAR(20),\n" +
                    " description VARCHAR (100),\n" +
                    " nodeID VARCHAR(20),\n" +
                    " CONSTRAINT request_PK PRIMARY KEY (name, time),\n" +
                    " CONSTRAINT nodeID_FK FOREIGN KEY (nodeID) REFERENCES NODE(nodeID))");
        }catch (SQLException e){
            System.out.println("Request table already exists");
        }

        //Try to create DefaultMaps table, yell if already exists
        try {
            stmt.execute("CREATE TABLE map (\n" +
                    " floor varchar(2) PRIMARY KEY,\n" +
                    " image blob NOT NULL\n " +
                    ")");
        } catch (SQLException e){
            System.out.println ("Map table already exists");
        }

        //Insert all Nodes to the table
        try {
            insertCSVToDatabase("src/DefaultData/defaultNodes.txt", stmt, "NODE");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //Insert all Edges to the table
        try {
            insertCSVToDatabase("src/DefaultData/defaultEdges.txt", stmt, "EDGE");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //Insert the default DefaultMaps to the table
        try {
            insertDefaultMapFiles(conn);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //Close shit down
        System.out.println("Database Created!");
        stmt.close();
        conn.close();
    }

    /**
     * Reads a csv file of type (node or edge) and creates insert statements and executes them to the database
     * @param path
     * @param stmt
     * @param table
     * @throws FileNotFoundException
     */
    private void insertCSVToDatabase(String path, Statement stmt, String table) throws FileNotFoundException {
        File file = new File(path);
        FileReader fileReader = new FileReader(file);
        BufferedReader br = new BufferedReader(fileReader);

        String line;
        String query;
        try {
            while ((line = br.readLine()) != null){
                try {
                    if (line != null){
                        String[] array = line.split(",");
                        //Execute query to database
                        if (table == "NODE"){
                            try {
                                query = ("INSERT INTO NODE VALUES ('"+array[0]+"',"+array[1]+","+array[2]+",'"+array[3]+"','"+array[4]+"','"+array[5]+"','"+array[6]+"','"+array[7]+"','"+array[8]+"','"+array[9]+"')");
                                stmt.execute(query);
                            } catch (SQLException e) {
                                //e.printStackTrace();
                            }
                        } else {
                            try {
                                query = ("INSERT INTO EDGE VALUES ('"+array[0]+"','"+array[1]+"','"+array[2]+"')");
                                stmt.execute(query);
                            } catch (SQLException e) {
                                //e.printStackTrace();
                            }
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

    /**
     * Inserts all the default map files into the database
     * @throws FileNotFoundException when the path is does not exist
     */
    private void insertDefaultMapFiles(Connection conn) throws FileNotFoundException{
        //Create a hashmap containing all the paths to the default maps
        HashMap<String, String> defaultImages = new HashMap<>();
        defaultImages.put("L2", "src/boundary/images/DefaultMaps/00_thelowerlevel1.png");
        defaultImages.put("L1", "src/boundary/images/DefaultMaps/00_thelowerlevel2.png");
        defaultImages.put("G", "src/boundary/images/DefaultMaps/00_thegroundfloor.png");
        defaultImages.put("1", "src/boundary/images/DefaultMaps/01_thefirstfloor.png");
        defaultImages.put("2", "src/boundary/images/DefaultMaps/02_thesecondfloor.png");
        defaultImages.put("3", "src/boundary/images/DefaultMaps/03_thethirdfloor.png");

        //Create the initial statements to isnert the images to the database
        FileInputStream fis = null;

        //Go through each path in the hashtable and create an insert statement and execute it
        for (String key: defaultImages.keySet()){
            //Create the statement and get the image from the HashMap
            PreparedStatement psmnt = null;
            File image = new File(defaultImages.get(key));
            //Make a FileInputStream from the image
            try {
                fis = new FileInputStream(image);
            } catch (FileNotFoundException e) {
                System.out.println("The file " + defaultImages.get(key) + " not found");
            }
            //Create an insert statement with the key and FIS
            try {
                psmnt = conn.prepareStatement("INSERT INTO MAP(floor, image) VALUES (?,?)");
                psmnt.setString(1, key);
                psmnt.setBinaryStream(2, fis, (int)(image.length()));
            } catch (SQLException e) {
            }
            //Execute the statement
            try {
                psmnt.executeUpdate();
            } catch (SQLException e) {

            }
            //Close the statement
            try {
                psmnt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
