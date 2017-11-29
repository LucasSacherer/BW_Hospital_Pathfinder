package Admin;

import DatabaseSetup.DatabaseGargoyle;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;

public class CSVController {
    /**
     * Takes a ResultSet and writes it to a CSV file
     * @param rs
     * @param filePath
     * @throws SQLException
     * @throws IOException
     */
    public void saveCSVFile(ResultSet rs, String filePath) throws SQLException, IOException {
        File out = new File(filePath);
        FileWriter writer = new FileWriter(out);
        final CSVPrinter printer = CSVFormat.DEFAULT.withHeader(rs).print(writer);
        printer.printRecords(rs);
        writer.close();
    }

    /**
     * Gets all Nodes and calls saveCSVFile with the result sets
     * @param filePath
     * @throws SQLException
     * @throws IOException
     */
    public void saveNodes(String filePath) throws SQLException, IOException{
        DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
        databaseGargoyle.createConnection();
        ResultSet rsNode = databaseGargoyle.executeQueryOnDatabase("SELECT * FROM NODE", databaseGargoyle.getStatement());
        saveCSVFile(rsNode, filePath);
        rsNode.close();
        databaseGargoyle.destroyConnection();
    }

    /**
     *
     * @param filePath
     * @throws SQLException
     * @throws IOException
     */
    public void saveEdges(String filePath) throws SQLException, IOException{
        DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
        databaseGargoyle.createConnection();
        ResultSet rsEdge = databaseGargoyle.executeQueryOnDatabase("SELECT * FROM EDGE", databaseGargoyle.getStatement());
        saveCSVFile(rsEdge, filePath);
        rsEdge.close();
        databaseGargoyle.destroyConnection();
    }

    public void saveAdminLogs(String filePath) throws SQLException, IOException{
        DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
        databaseGargoyle.createConnection();
        ResultSet rsAdminLog = databaseGargoyle.executeQueryOnDatabase("SELECT * FROM ADMINLOG", databaseGargoyle.getStatement());
        saveCSVFile(rsAdminLog, filePath);
        rsAdminLog.close();
        databaseGargoyle.destroyConnection();
    }
}
