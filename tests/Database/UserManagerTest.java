package Database;

import DatabaseSetup.DatabaseGargoyle;
import Entity.AdminLog;
import Entity.User;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UserManagerTest {

    @Test
    public void testAuthenticateAdmin() {
        DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
        AdminLogManager adminLogManager = new AdminLogManager(databaseGargoyle);
        UserManager userManager = new UserManager(databaseGargoyle, adminLogManager);
        databaseGargoyle.attachManager(userManager);
        databaseGargoyle.attachManager(adminLogManager);
        databaseGargoyle.notifyManagers();

        //Test an actual admin
        assertTrue(userManager.authenticateAdmin("admin1", "admin1"));
        //Test a non admin
        assertFalse(userManager.authenticateAdmin("janitor1", "janitor1"));
        //Test a non existent user
        assertFalse(userManager.authenticateAdmin("nope", "nope2"));
        //Test a null un and pw
        assertFalse(userManager.authenticateAdmin(null, null));
        //Test a good UN but bad PW
        assertFalse(userManager.authenticateAdmin("admin2", "admin"));
    }

    @Test
    public void testAuthenticateStaff() {
        DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
        AdminLogManager adminLogManager = new AdminLogManager(databaseGargoyle);
        UserManager userManager = new UserManager(databaseGargoyle, adminLogManager);
        databaseGargoyle.attachManager(userManager);
        databaseGargoyle.attachManager(adminLogManager);
        databaseGargoyle.notifyManagers();

        //Test an actual admin
        assertFalse(userManager.authenticateStaff("admin1", "admin1"));
        //Test a non admin
        assertTrue(userManager.authenticateStaff("staff1", "staff1"));
        //Test a non existent user
        assertFalse(userManager.authenticateStaff("nope", "nope2"));
        //Test a null un and pw
        assertFalse(userManager.authenticateStaff(null, null));
    }

    @Test
    public void testUpdateUsers() {
        DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
        AdminLogManager adminLogManager = new AdminLogManager(databaseGargoyle);
        UserManager userManager = new UserManager(databaseGargoyle, adminLogManager);

        //Check if the user is there before update
        assertEquals(null, userManager.getUser("admin1"));

        //Check if the user is there after update
        userManager.update();
        User po = userManager.getUser("admin1");
        assertEquals("admin1", po.getUserID());
        assertEquals("admin1", po.getUsername());
        assertEquals("admin1", po.getPassword());
        assertEquals(true, po.getAdminFlag());
        assertEquals("Interpreter", po.getDepartment());
    }

    @Test
    public void testModifyUser() {
        DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
        AdminLogManager adminLogManager = new AdminLogManager(databaseGargoyle);
        UserManager userManager = new UserManager(databaseGargoyle, adminLogManager);
        databaseGargoyle.attachManager(userManager);
        databaseGargoyle.attachManager(adminLogManager);
        databaseGargoyle.notifyManagers();

        //Test updating an existing user
        User modifiedUser = new User("admin1", "un1", "pw1", false, "dep");
        userManager.modifyUser(modifiedUser);
        User freshUser = userManager.getUser("admin1");
        assertEquals("admin1", freshUser.getUserID());
        assertEquals("un1", freshUser.getUsername());
        assertEquals("pw1", freshUser.getPassword());
        assertEquals(false, freshUser.getAdminFlag());
        assertEquals("dep", freshUser.getDepartment());

        //Reset the changed user and confirm its back to normal
        User originalUser = new User("admin1", "admin1", "admin1", true, "Interpreter");
        userManager.modifyUser(originalUser);
        User freshUser2 = userManager.getUser("admin1");
        assertEquals("admin1", freshUser2.getUserID());
        assertEquals("admin1", freshUser2.getUsername());
        assertEquals("admin1", freshUser2.getPassword());
        assertEquals(true, freshUser2.getAdminFlag());
        assertEquals("Interpreter", freshUser2.getDepartment());
    }

    @Test
    public void testAddRemoveUser() {
        DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
        AdminLogManager adminLogManager = new AdminLogManager(databaseGargoyle);
        UserManager userManager = new UserManager(databaseGargoyle, adminLogManager);
        databaseGargoyle.attachManager(userManager);
        databaseGargoyle.attachManager(adminLogManager);
        databaseGargoyle.notifyManagers();

        User user = new User ("1", "username", "password", true, "department");
        userManager.addUser(user);

        //Test to see if the added user is in the database
        databaseGargoyle.createConnection();
        ResultSet rs = databaseGargoyle.executeQueryOnDatabase("SELECT * FROM KIOSKUSER WHERE userID = '1'");
        try {
            while (rs.next()){
                assertTrue(rs.getString("USERID").equals("1"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        databaseGargoyle.destroyConnection();

        //Test to see if after removal, the user is no longer in the database
        userManager.removeUser(user);
        databaseGargoyle.createConnection();
        rs = databaseGargoyle.executeQueryOnDatabase("SELECT * FROM KIOSKUSER WHERE userID = '1'");
        try {
            assertFalse(rs.next());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        databaseGargoyle.destroyConnection();
    }
}
