package DatabaseSetup;

import java.sql.*;

public class DatabaseGargoyle {
    private final String URL = "jdbc:derby://localhost:1527/bw_pathfinder_db;create=true;user=granite_gargoyle;password=wong";
    private final String driver = "org.apache.derby.jdbc.ClientDriver";
    private Connection connection;
    private Statement statement;

    /**
     * Loads the driver for the database connection, and then connects to the URL and creates a statement to be executed
     */
    public void createConnection() {
        //Try to load drivers
        try {
            Class.forName(driver).newInstance();
        } catch (Exception ex) {
            System.out.println("Failed to find Embedded JavaDB driver!");
            ex.printStackTrace();
        }

        //Create connection and statement to be run
        try {
            connection = DriverManager.getConnection(URL);
            statement = connection.createStatement();
        } catch (SQLException e) {
            System.out.println("Exception thrown in createConnection()");
            e.printStackTrace();
        }
    }

    /**
     * Destroys the DatabaseGargoyle's statement and connection
     */
    public void destroyConnection() {
        try {
            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Exception thrown in destroyConnection()");
            e.printStackTrace();
        }
    }

    /**
     * Creates all of the tables in the database and says something if a table already exists
     */
    public void createTables() {
        TableCreator tableCreator = new TableCreator(statement);
        tableCreator.createNodeTable();
        tableCreator.createEdgeTable();
        tableCreator.createUserTable();
        tableCreator.createFoodRequestTable();
        tableCreator.createInterpreterRequestTable();
        tableCreator.createCleanUpRequestTable();
    }

    /**
     * Executes a SQL update statement to the database
     * @param sqlStatement
     */
    public void executeUpdateOnDatabase(String sqlStatement){
        try {
            statement.executeUpdate(sqlStatement);
        } catch (SQLException e) {
            System.out.println("The statement " + sqlStatement +  " failed: ");
            e.printStackTrace();
        }
    }

    /**
     * Executes a SQL Query on the database and returns the results
     * @param sqlStatement
     * @return
     */
    public ResultSet executeQueryOnDatabase(String sqlStatement){
        try {
            return statement.executeQuery(sqlStatement);
        } catch (SQLException e) {
            System.out.println("The statement " + sqlStatement +  " failed: ");
            e.printStackTrace();
            return null;
        }
    }
}
