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
        DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
        AdminLogManager adminLogManager = new AdminLogManager(databaseGargoyle);
        UserManager userManager = new UserManager(databaseGargoyle, adminLogManager);
        databaseGargoyle.attachManager(userManager);
        databaseGargoyle.attachManager(adminLogManager);
        databaseGargoyle.notifyManagers();
        Timestamp time = Timestamp.valueOf("1960-01-01 23:03:20.00");
        AdminLog log1 = new AdminLog("admin1", "Test", time.toLocalDateTime());

        //Make sure the database is empty at the start
        int originalSize = adminLogManager.getAdminLogs().size();

        //Call the addAdminLog function (also calls the UpdateAdminLogs function)
        adminLogManager.addAdminLog(log1);

        //Make sure the log has been added to the java object
        assertEquals(originalSize + 1, adminLogManager.getAdminLogs().size());

        //Remove the log from the database
        adminLogManager.deleteAdminLog(log1);

        //Make sure the log is now gone
        assertEquals(adminLogManager.getAdminLogs().size(), originalSize);
        databaseGargoyle.destroyConnection();
    }
}
