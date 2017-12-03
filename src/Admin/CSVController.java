package Admin;

import DatabaseSetup.DatabaseGargoyle;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import javax.xml.crypto.Data;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;

public class CSVController {
    DatabaseGargoyle databaseGargoyle;

    public CSVController(DatabaseGargoyle dbG) {
        this.databaseGargoyle = dbG;
    }

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
        databaseGargoyle.createConnection();
        ResultSet rsNode = databaseGargoyle.executeQueryOnDatabase("SELECT * FROM NODE");
        saveCSVFile(rsNode, filePath);
        rsNode.close();
        databaseGargoyle.destroyConnection();
    }

    /**
     * Gets all Edges and calls saveCSVFile with the result set
     * @param filePath
     * @throws SQLException
     * @throws IOException
     */
    public void saveEdges(String filePath) throws SQLException, IOException{
        databaseGargoyle.createConnection();
        ResultSet rsEdge = databaseGargoyle.executeQueryOnDatabase("SELECT * FROM EDGE");
        saveCSVFile(rsEdge, filePath);
        rsEdge.close();
        databaseGargoyle.destroyConnection();
    }

    /**
     * Gets all AdminLogs and calls saveCSVFile with the result set
     * @param filePath
     * @throws SQLException
     * @throws IOException
     */
    public void saveAdminLogs(String filePath) throws SQLException, IOException{
        databaseGargoyle.createConnection();
        ResultSet rsAdminLog = databaseGargoyle.executeQueryOnDatabase("SELECT * FROM ADMINLOG");
        saveCSVFile(rsAdminLog, filePath);
        rsAdminLog.close();
        databaseGargoyle.destroyConnection();
    }
}
