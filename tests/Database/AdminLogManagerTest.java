package Database;

import DatabaseSetup.DatabaseGargoyle;
import Entity.AdminLog;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AdminLogManagerTest {
    @Test
    public void testAddAdminLogAndUpdateAdminLogsAndDeleteAdminLog() {
        UserManager userManager = new UserManager();
        userManager.updateUsers();
        AdminLogManager adminLogManager = new AdminLogManager(userManager);
        Timestamp time = Timestamp.valueOf("1960-01-01 23:03:20.00");
        AdminLog log1 = new AdminLog(userManager.getUser("admin1"), "Test", time.toLocalDateTime());
        DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
        databaseGargoyle.createConnection();

        //Make sure the database is empty at the start
        adminLogManager.updateAdminLogs();
        assertEquals(0, adminLogManager.getAdminLogs().size());

        //Call the addAdminLog function (also calls the UpdateAdminLogs function)
        adminLogManager.addAdminLog(log1);

        //Make sure the log has been added to the database
        ResultSet rs = databaseGargoyle.executeQueryOnDatabase("SELECT * FROM ADMINLOG", databaseGargoyle.getStatement());
        try {
            if (rs.next()){
                assertTrue(rs.getString("action").equals("Test"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //Make sure the log has been added to the java object
        assertEquals(1, adminLogManager.getAdminLogs().size());

        //Remove the log from the database
        adminLogManager.deleteAdminLog(log1);

        //Make sure the log is now gone
        assertEquals(adminLogManager.getAdminLogs().size(), 0);
        databaseGargoyle.destroyConnection();
    }
}
