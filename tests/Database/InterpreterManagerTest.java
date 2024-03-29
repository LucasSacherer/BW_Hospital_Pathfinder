package Database;

import DatabaseSetup.DatabaseGargoyle;
import Entity.InterpreterRequest;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;

public class InterpreterManagerTest {

    @Test
    public void TestAddandDelete() {
        DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
        AdminLogManager adminLogManager = new AdminLogManager(databaseGargoyle);
        NodeManager nodeManager = new NodeManager(databaseGargoyle, adminLogManager);
        UserManager userManager = new UserManager(databaseGargoyle, adminLogManager);
        InterpreterManager iManager = new InterpreterManager(databaseGargoyle, nodeManager, userManager);
        databaseGargoyle.attachManager(nodeManager);
        databaseGargoyle.attachManager(userManager);
        databaseGargoyle.attachManager(iManager);
        databaseGargoyle.attachManager(adminLogManager);
        databaseGargoyle.notifyManagers();

        LocalDateTime createdDate = LocalDateTime.now();

        InterpreterRequest request = new InterpreterRequest("test", createdDate, createdDate, "type", "description", nodeManager.getNode("GINFO01902"), userManager.getUser("admin1"), "English");
        iManager.addRequest(request);

        //Test to see if the added request is in the database
        databaseGargoyle.createConnection();
        ResultSet rs = databaseGargoyle.executeQueryOnDatabase("SELECT * FROM INTERPRETERREQUEST WHERE name = 'test' AND TIMECREATED = '" + Timestamp.valueOf(createdDate)+"'");
        try {
            while (rs.next()){
                assertTrue(rs.getString("name").equals("test"));
                assertEquals(createdDate, rs.getTimestamp("timecreated").toLocalDateTime());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        databaseGargoyle.destroyConnection();

        //Test to see if after removal, the user is no longer in the database
        iManager.deleteRequest(request);
        databaseGargoyle.createConnection();
        rs = databaseGargoyle.executeQueryOnDatabase("SELECT * FROM INTERPRETERREQUEST WHERE name = 'test' AND TIMECREATED = '" +Timestamp.valueOf(createdDate)+"'");
        try {
            assertFalse(rs.next());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        databaseGargoyle.destroyConnection();
    }

    @Test
    public void TestUpdateRequest() throws Exception {
        DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
        AdminLogManager adminLogManager = new AdminLogManager(databaseGargoyle);
        NodeManager nodeManager = new NodeManager(databaseGargoyle, adminLogManager);
        UserManager userManager = new UserManager(databaseGargoyle, adminLogManager);
        InterpreterManager iManager = new InterpreterManager(databaseGargoyle, nodeManager, userManager);
        databaseGargoyle.attachManager(nodeManager);
        databaseGargoyle.attachManager(userManager);
        databaseGargoyle.attachManager(iManager);
        databaseGargoyle.attachManager(adminLogManager);
        databaseGargoyle.notifyManagers();

        Timestamp created = Timestamp.valueOf("1960-01-01 23:03:20.00");
        Timestamp completed = Timestamp.valueOf("1961-01-01 23:03:20.00");
        LocalDateTime createdDate = LocalDateTime.now();

        InterpreterRequest request = new InterpreterRequest("test", createdDate, createdDate,
                "type", "description", nodeManager.getNode("GINFO01902"),
                userManager.getUser("admin1"), "English");
        iManager.addRequest(request);

        InterpreterRequest updatedRequest = new InterpreterRequest("test", createdDate, createdDate,
                "NewType", "description", nodeManager.getNode("GINFO01902"),
                userManager.getUser("admin1"), "English");
        iManager.updateRequest(updatedRequest);

        assertEquals(iManager.getRequests().get(2).getType(), "NewType");

        iManager.updateRequest(request);

        assertEquals(iManager.getRequests().get(2).getType(), "type");

        iManager.deleteRequest(updatedRequest);
    }

    @Test
    public void testCompleteRequest() {
        //Create what is needed to run the tests
        DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
        AdminLogManager adminLogManager = new AdminLogManager(databaseGargoyle);
        NodeManager nodeManager = new NodeManager(databaseGargoyle, adminLogManager);
        UserManager userManager = new UserManager(databaseGargoyle, adminLogManager);
        InterpreterManager iManager = new InterpreterManager(databaseGargoyle, nodeManager, userManager);
        databaseGargoyle.attachManager(nodeManager);
        databaseGargoyle.attachManager(userManager);
        databaseGargoyle.attachManager(iManager);
        databaseGargoyle.attachManager(adminLogManager);
        databaseGargoyle.notifyManagers();

        Timestamp created = Timestamp.valueOf("1960-01-01 23:03:20.00");
        Timestamp completed = Timestamp.valueOf("1961-01-01 23:03:20.00");

        //Update the request and make sure it is changed in the database
        InterpreterRequest completedRequest = new InterpreterRequest("not complete", created.toLocalDateTime(),
                completed.toLocalDateTime(),"type1","description1",
                nodeManager.getNode("GLABS015L2"), userManager.getUser("admin1"), "English");
        iManager.addRequest(completedRequest);
        iManager.completeRequest(completedRequest);

        databaseGargoyle.createConnection();
        ResultSet rs = databaseGargoyle.executeQueryOnDatabase("SELECT * FROM INTERPRETERREQUEST WHERE name = 'not complete' AND TIMECREATED = '" +created+"'");
        try {
            if(rs.next()) {
                assertFalse(rs.getTimestamp("timecompleted").equals(completed));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        databaseGargoyle.destroyConnection();

        //Revert changes and confirm they are good now
        databaseGargoyle.createConnection();
        databaseGargoyle.executeUpdateOnDatabase("UPDATE INTERPRETERREQUEST SET " +
                "TIMECOMPLETED = '" + created + "' " +
                "WHERE NAME = 'not complete' AND TIMECREATED = '" + created + "'");
        databaseGargoyle.destroyConnection();

        databaseGargoyle.createConnection();
        ResultSet rs2 = databaseGargoyle.executeQueryOnDatabase("SELECT * FROM INTERPRETERREQUEST WHERE name = 'not complete' AND TIMECREATED = '" +created+"'");
        try {
            if(rs2.next()) {
                assertTrue(rs2.getTimestamp("timecompleted").equals(created));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        databaseGargoyle.destroyConnection();

        iManager.deleteRequest(completedRequest);
    }

    @Test
    public void testGetCompleted() {
        DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
        AdminLogManager adminLogManager = new AdminLogManager(databaseGargoyle);
        NodeManager nodeManager = new NodeManager(databaseGargoyle, adminLogManager);
        UserManager userManager = new UserManager(databaseGargoyle, adminLogManager);
        InterpreterManager iManager = new InterpreterManager(databaseGargoyle, nodeManager, userManager);
        databaseGargoyle.attachManager(nodeManager);
        databaseGargoyle.attachManager(userManager);
        databaseGargoyle.attachManager(iManager);
        databaseGargoyle.attachManager(adminLogManager);
        databaseGargoyle.notifyManagers();

        List<InterpreterRequest> completed = iManager.getCompleted();
        //Test that there is only one item in the list returned
        assertEquals(1, completed.size());
        //Test that the completed request is in there
        System.out.println(completed.get(0).getName());
        assertTrue(completed.get(0).getName().equals("completed"));
    }

    @Test
    public void testGetRequestsBy() {
        DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
        AdminLogManager adminLogManager = new AdminLogManager(databaseGargoyle);
        NodeManager nodeManager = new NodeManager(databaseGargoyle, adminLogManager);
        UserManager userManager = new UserManager(databaseGargoyle, adminLogManager);
        InterpreterManager iManager = new InterpreterManager(databaseGargoyle, nodeManager, userManager);
        databaseGargoyle.attachManager(nodeManager);
        databaseGargoyle.attachManager(userManager);
        databaseGargoyle.attachManager(iManager);
        databaseGargoyle.attachManager(adminLogManager);
        databaseGargoyle.notifyManagers();

        List<InterpreterRequest> requestsByUser = iManager.getRequestsBy(userManager.getUser("admin2"));

        System.out.println(iManager.getRequests());
        System.out.println(requestsByUser);

        assertTrue(requestsByUser.size() == 1);
        assertTrue(requestsByUser.get(0).getUser().getUserID().equals("admin2"));

        requestsByUser = iManager.getRequestsBy(userManager.getUser("staff1"));

        System.out.println(requestsByUser);

        assertTrue(requestsByUser.size() == 0);
    }
}