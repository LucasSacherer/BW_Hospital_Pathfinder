package DatabaseSetup;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInteraction {
    Statement statement;

    public DatabaseInteraction(Statement statement) {
        this.statement = statement;
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
