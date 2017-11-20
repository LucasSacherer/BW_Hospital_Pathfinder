package Database;

import DatabaseSetup.DatabaseGargoyle;
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
        UserManager userManager = new UserManager();
        //Test an actual admin
        assertTrue(userManager.authenticateAdmin("tinkywinkyUN", "tinkywinkyPW"));
        //Test a non admin
        assertFalse(userManager.authenticateAdmin("poUN", "poPW"));
        //Test a non existent user
        assertFalse(userManager.authenticateAdmin("nope", "nope2"));
        //Test a null un and pw
        assertFalse(userManager.authenticateAdmin(null, null));
    }

    @Test
    public void testAuthenticateStaff() {
        UserManager userManager = new UserManager();
        //Test an actual admin
        assertFalse(userManager.authenticateStaff("tinkywinkyUN", "tinkywinkyPW"));
        //Test a non admin
        assertTrue(userManager.authenticateStaff("poUN", "poPW"));
        //Test a non existent user
        assertFalse(userManager.authenticateStaff("nope", "nope2"));
        //Test a null un and pw
        assertFalse(userManager.authenticateStaff(null, null));
    }

    @Test
    public void testUpdateUsers() {
        UserManager userManager = new UserManager();
        //Check if the user is there before update
        assertEquals(null, userManager.getUser("po"));

        //Check if the user is there after update
        userManager.updateUsers();
        User po = userManager.getUser("po");
        assertEquals("po", po.getUserID());
        assertEquals("poUN", po.getUsername());
        assertEquals("poPW", po.getPassword());
        assertEquals(false, po.getAdminFlag());
        assertEquals("poD", po.getDepartment());
    }

    @Test
    public void testModifyUser() {
        UserManager userManager = new UserManager();

        //Test updating an existing user
        User modifiedUser = new User("po", "un1", "pw1", true, "dep");
        userManager.modifyUser(modifiedUser);
        User freshUser = userManager.getUser("po");
        assertEquals("po", freshUser.getUserID());
        assertEquals("un1", freshUser.getUsername());
        assertEquals("pw1", freshUser.getPassword());
        assertEquals(true, freshUser.getAdminFlag());
        assertEquals("dep", freshUser.getDepartment());

        //Reset the changed user and confirm its back to normal
        User originalUser = new User("po", "poUN", "poPW", false, "poD");
        userManager.modifyUser(originalUser);
        User freshUser2 = userManager.getUser("po");
        assertEquals("po", freshUser2.getUserID());
        assertEquals("poUN", freshUser2.getUsername());
        assertEquals("poPW", freshUser2.getPassword());
        assertEquals(false, freshUser2.getAdminFlag());
        assertEquals("poD", freshUser2.getDepartment());


    }

    @Test
    public void testAddRemoveUser() {
        UserManager userManager = new UserManager();
        User user = new User ("1", "username", "password", true, "department");
        userManager.addUser(user);

        //Test to see if the added user is in the database
        DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
        databaseGargoyle.createConnection();
        ResultSet rs = databaseGargoyle.executeQueryOnDatabase("SELECT * FROM KIOSKUSER WHERE userID = '1'", databaseGargoyle.getStatement());
        try {
            while (rs.next()){
                assertTrue(rs.getString("USERID").equals("1"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //Test to see if after removal, the user is no longer in the database
        userManager.removeUser(user);
        rs = databaseGargoyle.executeQueryOnDatabase("SELECT * FROM KIOSKUSER WHERE userID = '1'", databaseGargoyle.getStatement());
        try {
            assertFalse(rs.next());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        databaseGargoyle.destroyConnection();
    }
}
