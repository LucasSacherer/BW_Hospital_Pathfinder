import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main extends Application {

    
    @Override
    public void start(Stage primaryStage) throws Exception{
        //Load DB
        try {
            createDatbase();
        }catch (Exception e){
            System.out.println("Database is already created on this machine");
        }

        //Load UI
        Parent root = FXMLLoader.load(getClass().getResource("boundary/main.fxml"));
        primaryStage.setTitle("B&W Path Finding");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }

    private void createDatbase() throws SQLException {
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

        //Try to create Edge table, yell if already exists
        try {
            stmt.execute("CREATE TABLE edge (\n" +
                    " edgeID VARCHAR(30) PRIMARY KEY,\n" +
                    " startNode varchar(20) NOT NULL,\n" +
                    " endNode varchar(20) NOT NULL\n" +
                    ")");
        } catch (SQLException e){
            System.out.println ("Table already exists");
        }

        //Try to create Node table, yell if already exists
        try {
            stmt.execute("CREATE TABLE node (\n" +
                    " nodeID varchar(20) PRIMARY KEY,\n" +
                    " xcoord INTEGER NOT NULL,\n" +
                    " ycoord INTEGER NOT NULL,\n" +
                    " floor varchar(3) NOT NULL,\n" +
                    " building varchar(20) NOT NULL,\n" +
                    " nodeType varchar(10) NOT NULL,\n" +
                    " longName varchar(30) NOT NULL,\n" +
                    " shortName varchar(20) NOT NULL,\n" +
                    " teamAssigned varchar(10) NOT NULL,\n" +
                    " visitable varchar(5) NOT NULL\n " +
                    ")");
        } catch (SQLException e){
            System.out.println ("Table already exists");
        }

        //Insert in all the Nodes to the table


        stmt.close();
        conn.close();
    }

    public static void main(String[] args) {
        launch(args);
    }
}