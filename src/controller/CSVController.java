package controller;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;

public class CSVController {
    public void saveCSVFile(ResultSet rs, String filePath) throws SQLException, IOException {
        File out = new File(filePath);
        FileWriter writer = new FileWriter(out);
        final CSVPrinter printer = CSVFormat.DEFAULT.withHeader(rs).print(writer);
        printer.printRecords(rs);

        writer.close();

    }

    public void saveNodes(String filePath) throws SQLException, IOException{

        String dbURL = "jdbc:derby://localhost:1527/bw_pathfinder_db;create=true;user=granite_gargoyle;password=wong";
        Connection conn = DriverManager.getConnection(dbURL);

        Statement stmt = conn.createStatement();
        ResultSet rsNode = stmt.executeQuery("SELECT * FROM NODE");

        saveCSVFile(rsNode, filePath);

        rsNode.close();
        stmt.close();
        conn.close();
    }

    public void saveEdge(String filePath) throws SQLException, IOException{

        String dbURL = "jdbc:derby://localhost:1527/bw_pathfinder_db;create=true;user=granite_gargoyle;password=wong";
        Connection conn = DriverManager.getConnection(dbURL);

        Statement stmt = conn.createStatement();
        ResultSet rsNode = stmt.executeQuery("SELECT * FROM EDGE");

        saveCSVFile(rsNode, filePath);

        rsNode.close();
        stmt.close();
        conn.close();
    }
}
