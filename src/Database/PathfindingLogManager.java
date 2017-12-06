package Database;

import DatabaseSetup.DatabaseGargoyle;
import Entity.Node;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class PathfindingLogManager {
    DatabaseGargoyle databaseGargoyle;

    public PathfindingLogManager() {
        this.databaseGargoyle =  new DatabaseGargoyle();
    }

    /**
     * Adds the path to the pathdfinding log table in the database
     * @param path
     */
    public void addPathToLog(List<Node> path){
        int i = 0;
        LocalDateTime time = LocalDateTime.now();
        String pathID = "" + path.get(path.size() - 1) + "_" + path.get(0) + "_" + Timestamp.valueOf(time);
        for (Node node: path){
            String destinationFlag = (i == 0 ? "true" : "false");
            databaseGargoyle.createConnection();
            databaseGargoyle.executeUpdateOnDatabase("INSERT INTO PATHFINDINGLOG VALUES (" +
                    "'" + node.getNodeID() + "'," +
                    "'" + Timestamp.valueOf(time) + "'," +
                    "'" + destinationFlag + "'," +
                    "'" + pathID + "')");
            databaseGargoyle.destroyConnection();
            i++;
        }
    }

    /**
     * Right now this is for testing only, but...
     * Deletes all logs in the pathfinding log database table
     */
    public void clearAllLogsInDatabase(){
        databaseGargoyle.createConnection();
        databaseGargoyle.executeUpdateOnDatabase("DELETE FROM PATHFINDINGLOG");
        databaseGargoyle.destroyConnection();
    }

    /**
     * For Testing only
     * Gets the number of logs currently in the database
     * @return
     */
    public int getPathfindingLogSize() {
        int size = 0;
        databaseGargoyle.createConnection();
        ResultSet rs = databaseGargoyle.executeQueryOnDatabase("SELECT COUNT(*) FROM PATHFINDINGLOG");
        try {
            while (rs.next()){
                size = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        databaseGargoyle.destroyConnection();
        return size;
    }
}
