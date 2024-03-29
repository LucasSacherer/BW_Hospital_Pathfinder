package Admin;

import Database.AdminLogManager;
import Database.UserManager;
import DatabaseSetup.DatabaseGargoyle;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserLoginControllerTest {

    @Test
    public void testAdmin() throws Exception{
        DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
        AdminLogManager adminLogManager = new AdminLogManager(databaseGargoyle);
        UserManager um = new UserManager(databaseGargoyle, adminLogManager);
        databaseGargoyle.attachManager(um);
        databaseGargoyle.attachManager(adminLogManager);
        databaseGargoyle.notifyManagers();

        UserLoginController ulc = new UserLoginController(um);
        boolean answer = ulc.authenticateAdmin("admin1", "admin1");
        assertEquals(true,answer);
    }

    @Test
    public void testAdminonStaff() throws Exception{
        DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
        AdminLogManager adminLogManager = new AdminLogManager(databaseGargoyle);
        UserManager um = new UserManager(databaseGargoyle, adminLogManager);
        databaseGargoyle.attachManager(um);
        databaseGargoyle.attachManager(adminLogManager);
        databaseGargoyle.notifyManagers();

        UserLoginController ulc = new UserLoginController(um);
        boolean answer = ulc.authenticateStaff("admin1", "admin1");
        assertEquals(false,answer);
    }

    @Test
    public void testAdminBadFormat() throws Exception{
        DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
        AdminLogManager adminLogManager = new AdminLogManager(databaseGargoyle);
        UserManager um = new UserManager(databaseGargoyle, adminLogManager);
        databaseGargoyle.attachManager(um);
        databaseGargoyle.attachManager(adminLogManager);
        databaseGargoyle.notifyManagers();

        UserLoginController ulc = new UserLoginController(um);
        boolean answer = ulc.authenticateAdmin("admin,1", "admin1");
        assertEquals(false,answer);
        answer = ulc.authenticateAdmin("adm''in1", "admin1");
        assertEquals(false,answer);
        answer = ulc.authenticateAdmin("adm''in1", "admi.n1");
        assertEquals(false,answer);
        answer = ulc.authenticateAdmin("admin1", "ad.m'in1");
        assertEquals(false,answer);
    }

    @Test
    public void testAdminBadPass() throws Exception{
        DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
        AdminLogManager adminLogManager = new AdminLogManager(databaseGargoyle);
        UserManager um = new UserManager(databaseGargoyle, adminLogManager);
        databaseGargoyle.attachManager(um);
        databaseGargoyle.attachManager(adminLogManager);
        databaseGargoyle.notifyManagers();

        UserLoginController ulc = new UserLoginController(um);
        boolean answer = ulc.authenticateAdmin("admin1", "admin2");
        assertEquals(false,answer);
        answer = ulc.authenticateAdmin("admin2", "admin1");
        assertEquals(false,answer);
    }

    @Test
    public void testStaffBadFormat() throws Exception{
        DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
        AdminLogManager adminLogManager = new AdminLogManager(databaseGargoyle);
        UserManager um = new UserManager(databaseGargoyle, adminLogManager);
        databaseGargoyle.attachManager(um);
        databaseGargoyle.attachManager(adminLogManager);
        databaseGargoyle.notifyManagers();

        UserLoginController ulc = new UserLoginController(um);
        boolean answer = ulc.authenticateStaff("sta.ff1", "staff1");
        assertEquals(false,answer);
        answer = ulc.authenticateAdmin("staff1", "staff.1");
        assertEquals(false,answer);
        answer = ulc.authenticateAdmin("staff'.1", "staff'1");
        assertEquals(false,answer);
        answer = ulc.authenticateAdmin("..staff1", "sta,f.f1");
        assertEquals(false,answer);
    }

    @Test
    public void testStaffBadPass() throws Exception{
        DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
        AdminLogManager adminLogManager = new AdminLogManager(databaseGargoyle);
        UserManager um = new UserManager(databaseGargoyle, adminLogManager);
        databaseGargoyle.attachManager(um);
        databaseGargoyle.attachManager(adminLogManager);
        databaseGargoyle.notifyManagers();

        UserLoginController ulc = new UserLoginController(um);
        boolean answer = ulc.authenticateAdmin("staff1", "staff2");
        assertEquals(false,answer);
    }

    @Test
    public void testGoodLogins() throws Exception{
        DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
        AdminLogManager adminLogManager = new AdminLogManager(databaseGargoyle);
        UserManager um = new UserManager(databaseGargoyle, adminLogManager);
        databaseGargoyle.attachManager(um);
        databaseGargoyle.attachManager(adminLogManager);
        databaseGargoyle.notifyManagers();

        UserLoginController ulc = new UserLoginController(um);
        boolean answer = ulc.authenticateAdmin("admin1", "admin1");
        assertEquals(true,answer);
        answer = ulc.authenticateStaff("staff1", "staff1");
        assertEquals(true,answer);
    }

}