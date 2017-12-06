package Database;

import DatabaseSetup.DatabaseGargoyle;
import Entity.AdminLog;
import Entity.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AdminLogManager implements EntityManager {

    private final List<AdminLog> adminlogs;
    private final DatabaseGargoyle databaseGargoyle;

    public AdminLogManager(DatabaseGargoyle dbG) {
        this.adminlogs = new ArrayList<>();
        this.databaseGargoyle = dbG;
    }

    /**
     * Adds the log entry to the database and updates the AdminLogs
     * @param logEntry
     */
    public void addAdminLog(AdminLog logEntry){
        //Add the request to the ADMINLOG table
        databaseGargoyle.createConnection();
        databaseGargoyle.executeUpdateOnDatabase("INSERT INTO ADMINLOG VALUES (" +
                "'" + logEntry.getUser() + "'," +
                "'" + logEntry.getAction() + "'," +
                "'" + Timestamp.valueOf(logEntry.getTime()) + "')");
        databaseGargoyle.destroyConnection();
    }

    /**
     * Updates the list of AdminLogs in AdminLogManager to be up to date with the database
     */
    public void update(){
        String action, userID;
        LocalDateTime time;
        adminlogs.clear();

        databaseGargoyle.createConnection();
        ResultSet rs = databaseGargoyle.executeQueryOnDatabase("SELECT * FROM ADMINLOG");
        try {
            while (rs.next()) {
                userID = rs.getString("USERID");
                action = rs.getString("ACTION");
                time = rs.getTimestamp("TIME").toLocalDateTime();
                adminlogs.add(new AdminLog(userID, action, time));
            }
        }catch (SQLException ex){
            System.out.println("Failed to update the list of Admin Logs!");
            ex.printStackTrace();
        }
        databaseGargoyle.destroyConnection();
    }

    /**
     * Get all the admin logs
     * @return the list of log entries
     */
    public List<AdminLog> getAdminLogs(){
        return this.adminlogs;
    }

    /**
     * FOR TESTING ONLY: Removes a log from the database
     * @param badLog
     */
    public void deleteAdminLog(AdminLog badLog){
        databaseGargoyle.createConnection();
        //Add the request to the ADMINLOG table
        databaseGargoyle.executeUpdateOnDatabase("DELETE FROM ADMINLOG WHERE USERID = " +
                "'" + badLog.getUser() + "' AND ACTION = " +
                "'" + badLog.getAction() + "' AND TIME = " +
                "'" + Timestamp.valueOf(badLog.getTime()) + "'");
        databaseGargoyle.destroyConnection();
    }
}
