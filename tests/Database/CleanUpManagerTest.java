package Database;

import DatabaseSetup.DatabaseGargoyle;
import Entity.CleanUpRequest;
import Entity.User;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CleanUpManagerTest {

    @Test
    public void testAddAndDelete(){
        DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
        AdminLogManager adminLogManager = new AdminLogManager(databaseGargoyle);
        NodeManager nodeManager = new NodeManager(databaseGargoyle, adminLogManager);
        UserManager userManager = new UserManager(databaseGargoyle, adminLogManager);
        CleanUpManager cleanUpManager = new CleanUpManager(databaseGargoyle, nodeManager, userManager);
        databaseGargoyle.attachManager(nodeManager);
        databaseGargoyle.attachManager(userManager);
        databaseGargoyle.attachManager(cleanUpManager);
        databaseGargoyle.attachManager(adminLogManager);
        databaseGargoyle.notifyManagers();

        LocalDateTime createdDate = LocalDateTime.now();

        int originalSize = cleanUpManager.getRequests().size();

        CleanUpRequest request = new CleanUpRequest("test", createdDate, createdDate, "type",
                "description", nodeManager.getNode("GINFO01902"), userManager.getUser("admin1"));
        cleanUpManager.addRequest(request);

        //Test to see if the added request is in the database
        databaseGargoyle.createConnection();
        ResultSet rs = databaseGargoyle.executeQueryOnDatabase("SELECT * FROM CLEANUPREQUEST WHERE name = 'test' AND TIMECREATED = '" +Timestamp.valueOf(createdDate)+"'");
        try {
            while (rs.next()){
                assertTrue(rs.getString("name").equals("test"));
                assertEquals(createdDate, rs.getTimestamp("timecreated").toLocalDateTime());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        databaseGargoyle.destroyConnection();

        //Test to see that it has been added to the Entity list
        cleanUpManager.printRequests();
        assertEquals(originalSize + 1, cleanUpManager.getRequests().size());

        //Test to see if after removal, the user is no longer in the database
        cleanUpManager.deleteRequest(request);
        databaseGargoyle.createConnection();
        rs = databaseGargoyle.executeQueryOnDatabase("SELECT * FROM CLEANUPREQUEST WHERE name = 'test' AND TIMECREATED = '" +Timestamp.valueOf(createdDate)+"'");
        try {
            assertFalse(rs.next());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assertFalse(cleanUpManager.getRequests().contains(request));
        databaseGargoyle.destroyConnection();
    }

    @Test
    public void testUpdateRequests() {
        DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
        AdminLogManager adminLogManager = new AdminLogManager(databaseGargoyle);
        NodeManager nodeManager = new NodeManager(databaseGargoyle, adminLogManager);
        UserManager userManager = new UserManager(databaseGargoyle, adminLogManager);
        CleanUpManager cleanUpManager = new CleanUpManager(databaseGargoyle, nodeManager, userManager);
        databaseGargoyle.attachManager(nodeManager);
        databaseGargoyle.attachManager(userManager);
        databaseGargoyle.attachManager(cleanUpManager);
        nodeManager.update();
        userManager.update();

        Timestamp stamp = Timestamp.valueOf("1960-01-01 23:03:20.00");

        //Check if the user is there before update
        assertEquals(null, cleanUpManager.getCleanUpRequest("not completed", stamp.toLocalDateTime()));

        cleanUpManager.update();

        //Check if the user is there after update
        CleanUpRequest req = cleanUpManager.getCleanUpRequest("not completed", stamp.toLocalDateTime());
        assertEquals("not completed", req.getName());
        assertEquals(stamp.toLocalDateTime(), req.getTimeCreated());
        assertEquals("type2", req.getType());
        assertEquals("description2", req.getDescription());
        assertEquals("GDEPT00403", req.getNode().getNodeID());
        assertEquals("janitor1", req.getUser().getUserID());
    }

    @Test
    public void testUpdateRequest() {
        DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
        AdminLogManager adminLogManager = new AdminLogManager(databaseGargoyle);
        NodeManager nodeManager = new NodeManager(databaseGargoyle, adminLogManager);
        UserManager userManager = new UserManager(databaseGargoyle, adminLogManager);
        CleanUpManager cleanUpManager = new CleanUpManager(databaseGargoyle, nodeManager, userManager);
        databaseGargoyle.attachManager(nodeManager);
        databaseGargoyle.attachManager(userManager);
        databaseGargoyle.attachManager(cleanUpManager);
        databaseGargoyle.attachManager(adminLogManager);
        databaseGargoyle.notifyManagers();

        Timestamp created = Timestamp.valueOf("1960-01-01 23:03:20.00");
        //Timestamp completed = Timestamp.valueOf("1961-01-01 23:03:20.00");

        //Test updating an existing user
        CleanUpRequest updatedRequest = new CleanUpRequest("not completed", created.toLocalDateTime(),
                created.toLocalDateTime(),"type21","description21",
                nodeManager.getNode("GHALL013L2"), userManager.getUser("admin2"));
        cleanUpManager.updateRequest(updatedRequest);
        CleanUpRequest freshRequest = cleanUpManager.getCleanUpRequest("not completed", created.toLocalDateTime());
        assertEquals(created.toLocalDateTime(), freshRequest.getTimeCompleted());
        assertEquals("type21", freshRequest.getType());
        assertEquals("description21", freshRequest.getDescription());
        assertEquals(nodeManager.getNode("GHALL013L2"), freshRequest.getNode());
        assertEquals(userManager.getUser("admin2"), freshRequest.getUser());

        //Reset the changed user and confirm its back to normal
        CleanUpRequest original = new CleanUpRequest("not completed", created.toLocalDateTime(),
                created.toLocalDateTime(),"type2","description2",
                nodeManager.getNode("GDEPT00403"), userManager.getUser("janitor1"));
        cleanUpManager.updateRequest(original);
        CleanUpRequest freshRequest2 = cleanUpManager.getCleanUpRequest("not completed", created.toLocalDateTime());
        assertEquals(created.toLocalDateTime(), freshRequest2.getTimeCompleted());
        assertEquals("type2", freshRequest2.getType());
        assertEquals("description2", freshRequest2.getDescription());
        assertEquals(nodeManager.getNode("GDEPT00403"), freshRequest2.getNode());
        assertEquals(userManager.getUser("janitor1"), freshRequest2.getUser());
    }

    @Test
    public void testCompleteRequest() {
        DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
        AdminLogManager adminLogManager = new AdminLogManager(databaseGargoyle);
        NodeManager nodeManager = new NodeManager(databaseGargoyle, adminLogManager);
        UserManager userManager = new UserManager(databaseGargoyle, adminLogManager);
        CleanUpManager cleanUpManager = new CleanUpManager(databaseGargoyle, nodeManager, userManager);
        databaseGargoyle.attachManager(nodeManager);
        databaseGargoyle.attachManager(userManager);
        databaseGargoyle.attachManager(cleanUpManager);
        databaseGargoyle.attachManager(adminLogManager);
        databaseGargoyle.notifyManagers();

        Timestamp created = Timestamp.valueOf("1960-01-01 23:03:20.00");
        Timestamp completed = Timestamp.valueOf("1961-01-01 23:03:20.00");

        //Update the request and make sure it is changed in the database
        CleanUpRequest completedRequest = new CleanUpRequest("not completed", created.toLocalDateTime(),
                created.toLocalDateTime(),"type1","description1",
                nodeManager.getNode("GLABS015L2"), userManager.getUser("admin1"));
        cleanUpManager.completeRequest(completedRequest);
        databaseGargoyle.createConnection();
        ResultSet rs = databaseGargoyle.executeQueryOnDatabase("SELECT * FROM CLEANUPREQUEST WHERE name = 'not completed' AND TIMECREATED = '" +created+"'");
        try {
            if (rs.next()){
                assertFalse(rs.getTimestamp("timecompleted").equals(created));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        databaseGargoyle.destroyConnection();

        //Revert changes and confirm they are good now
        databaseGargoyle.createConnection();
        databaseGargoyle.executeUpdateOnDatabase("UPDATE CLEANUPREQUEST SET " +
                "TIMECOMPLETED = '" + created + "' " +
                "WHERE NAME = 'not completed' AND TIMECREATED = '" + created + "'");
        databaseGargoyle.destroyConnection();
        databaseGargoyle.createConnection();
        ResultSet rs2 = databaseGargoyle.executeQueryOnDatabase("SELECT * FROM CLEANUPREQUEST WHERE name = " +
                "'not completed' AND TIMECREATED = '" +created+"'");
        try {
            if (rs2.next()){
                assertTrue(rs2.getTimestamp("timecompleted").equals(created));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        databaseGargoyle.destroyConnection();
    }

    @Test
    public void testGetCompleted() {
        DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
        AdminLogManager adminLogManager = new AdminLogManager(databaseGargoyle);
        NodeManager nodeManager = new NodeManager(databaseGargoyle, adminLogManager);
        UserManager userManager = new UserManager(databaseGargoyle, adminLogManager);
        CleanUpManager cleanUpManager = new CleanUpManager(databaseGargoyle, nodeManager, userManager);
        databaseGargoyle.attachManager(nodeManager);
        databaseGargoyle.attachManager(userManager);
        databaseGargoyle.attachManager(cleanUpManager);
        databaseGargoyle.attachManager(adminLogManager);
        databaseGargoyle.notifyManagers();

        List<CleanUpRequest> completed = cleanUpManager.getCompleted();
        //Test that there is only one item in the list returned
        assertEquals(1, completed.size());
        //Test that the completed request is in there
        assertTrue(completed.get(0).getName().equals("completed"));
    }

    @Test
    public void testGetRequestsBy() {
        DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
        AdminLogManager adminLogManager = new AdminLogManager(databaseGargoyle);
        NodeManager nodeManager = new NodeManager(databaseGargoyle, adminLogManager);
        UserManager userManager = new UserManager(databaseGargoyle, adminLogManager);
        CleanUpManager cleanUpManager = new CleanUpManager(databaseGargoyle, nodeManager, userManager);
        databaseGargoyle.attachManager(nodeManager);
        databaseGargoyle.attachManager(userManager);
        databaseGargoyle.attachManager(cleanUpManager);
        databaseGargoyle.attachManager(adminLogManager);
        databaseGargoyle.notifyManagers();

        User admin = userManager.getUser("admin1");
        assertEquals(1, cleanUpManager.getRequestsBy(admin).size());

        List<CleanUpRequest> requestsByUser = cleanUpManager.getRequestsBy(userManager.getUser("janitor1"));

        System.out.println(cleanUpManager.getRequests());
        System.out.println(requestsByUser);

        assertEquals(1, requestsByUser.size());
        assertTrue(requestsByUser.get(0).getUser().getUserID().equals("janitor1"));

        requestsByUser = cleanUpManager.getRequestsBy(userManager.getUser("staff1"));

        System.out.println(requestsByUser);

        assertTrue(requestsByUser.size() == 0);
    }
}
