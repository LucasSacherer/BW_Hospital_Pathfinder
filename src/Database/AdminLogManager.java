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

public class AdminLogManager {

    private final List<AdminLog> adminlogs;
    private final DatabaseGargoyle databaseGargoyle;
    private final UserManager userManager;

    public AdminLogManager(UserManager userManager) {
        this.adminlogs = new ArrayList<>();
        this.databaseGargoyle = new DatabaseGargoyle();
        this.userManager = userManager;
    }

    /**
     * Adds the log entry to the database and updates the AdminLogs
     * @param logEntry
     */
    public void addAdminLog(AdminLog logEntry){
        databaseGargoyle.createConnection();
        //Add the request to the ADMINLOG table
        databaseGargoyle.executeUpdateOnDatabase("INSERT INTO ADMINLOG VALUES (" +
                "'" + logEntry.getUser().getUserID() + "'," +
                "'" + logEntry.getAction() + "'," +
                "'" + Timestamp.valueOf(logEntry.getTime()) + "')", databaseGargoyle.getStatement());
        databaseGargoyle.destroyConnection();
        this.updateAdminLogs();
    }

    /**
     * Updates the list of AdminLogs in AdminLogManager to be up to date with the database
     */
    public void updateAdminLogs(){
        String action;
        LocalDateTime time;
        User user;

        adminlogs.clear();
        userManager.updateUsers();

        databaseGargoyle.createConnection();
        ResultSet rs = databaseGargoyle.executeQueryOnDatabase("SELECT * FROM ADMINLOG", databaseGargoyle.getStatement());
        try {
            while (rs.next()) {
                user = userManager.getUser(rs.getString("USERID"));
                action = rs.getString("ACTION");
                time = rs.getTimestamp("TIME").toLocalDateTime();
                adminlogs.add(new AdminLog(user, action, time));
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
        this.updateAdminLogs();
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
                "'" + badLog.getUser().getUserID() + "' AND ACTION = " +
                "'" + badLog.getAction() + "' AND TIME = " +
                "'" + Timestamp.valueOf(badLog.getTime()) + "'", databaseGargoyle.getStatement());
        databaseGargoyle.destroyConnection();
        this.updateAdminLogs();
    }
}
