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
        NodeManager nodeManager = new NodeManager();
        nodeManager.updateNodes();
        UserManager userManager = new UserManager();
        userManager.updateUsers();
        CleanUpManager cleanUpManager = new CleanUpManager(nodeManager, userManager);
        LocalDateTime createdDate = LocalDateTime.now();

        CleanUpRequest request = new CleanUpRequest("test", createdDate, createdDate, "type", "description", nodeManager.getNode("GINFO01902"), userManager.getUser("admin1"));
        cleanUpManager.addRequest(request);

        //Test to see if the added request is in the database
        DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
        databaseGargoyle.createConnection();
        ResultSet rs = databaseGargoyle.executeQueryOnDatabase("SELECT * FROM CLEANUPREQUEST WHERE name = 'test' AND TIMECREATED = '" +Timestamp.valueOf(createdDate)+"'", databaseGargoyle.getStatement());
        try {
            while (rs.next()){
                assertTrue(rs.getString("name").equals("test"));
                assertEquals(createdDate, rs.getTimestamp("timecreated").toLocalDateTime());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //Test to see if after removal, the user is no longer in the database
        cleanUpManager.deleteRequest(request);
        rs = databaseGargoyle.executeQueryOnDatabase("SELECT * FROM CLEANUPREQUEST WHERE name = 'test' AND TIMECREATED = '" +Timestamp.valueOf(createdDate)+"'", databaseGargoyle.getStatement());
        try {
            assertFalse(rs.next());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        databaseGargoyle.destroyConnection();
    }

    @Test
    public void testUpdateRequests() {
        NodeManager nodeManager = new NodeManager();
        UserManager userManager = new UserManager();
        CleanUpManager cleanUpManager = new CleanUpManager(nodeManager, userManager);
        Timestamp stamp = Timestamp.valueOf("1960-01-01 23:03:20.00");

        //Check if the user is there before update
        assertEquals(null, cleanUpManager.getCleanUpRequest("completed", stamp.toLocalDateTime()));

        //Check if the user is there after update
        cleanUpManager.updateRequests();
        CleanUpRequest req = cleanUpManager.getCleanUpRequest("completed", stamp.toLocalDateTime());
        assertEquals("completed", req.getName());
        assertEquals(stamp.toLocalDateTime(), req.getTimeCreated());
        assertEquals("type1", req.getType());
        assertEquals("description1", req.getDescription());
        assertEquals("GLABS015L2", req.getNode().getNodeID());
        assertEquals("admin1", req.getUser().getUserID());
    }

    @Test
    public void testUpdateRequest() {
        NodeManager nodeManager = new NodeManager();
        nodeManager.updateNodes();
        UserManager userManager = new UserManager();
        userManager.updateUsers();
        CleanUpManager cleanUpManager = new CleanUpManager(nodeManager, userManager);
        Timestamp created = Timestamp.valueOf("1960-01-01 23:03:20.00");
        Timestamp completed = Timestamp.valueOf("1961-01-01 23:03:20.00");

        //Test updating an existing user
        CleanUpRequest updatedRequest = new CleanUpRequest("completed", created.toLocalDateTime(),
                completed.toLocalDateTime(),"type12","description12",
                nodeManager.getNode("WELEV00K01"), userManager.getUser("admin2"));
        cleanUpManager.updateRequest(updatedRequest);
        CleanUpRequest freshRequest = cleanUpManager.getCleanUpRequest("completed", created.toLocalDateTime());
        assertEquals(completed.toLocalDateTime(), freshRequest.getTimeCompleted());
        assertEquals("type12", freshRequest.getType());
        assertEquals("description12", freshRequest.getDescription());
        assertEquals(nodeManager.getNode("WELEV00K01"), freshRequest.getNode());
        assertEquals(userManager.getUser("admin2"), freshRequest.getUser());

        //Reset the changed user and confirm its back to normal
        CleanUpRequest original = new CleanUpRequest("completed", created.toLocalDateTime(),
                completed.toLocalDateTime(),"type1","description1",
                nodeManager.getNode("GLABS015L2"), userManager.getUser("admin1"));
        cleanUpManager.updateRequest(original);
        CleanUpRequest freshRequest2 = cleanUpManager.getCleanUpRequest("completed", created.toLocalDateTime());
        assertEquals(completed.toLocalDateTime(), freshRequest2.getTimeCompleted());
        assertEquals("type1", freshRequest2.getType());
        assertEquals("description1", freshRequest2.getDescription());
        assertEquals(nodeManager.getNode("GLABS015L2"), freshRequest2.getNode());
        assertEquals(userManager.getUser("admin1"), freshRequest2.getUser());
    }

    @Test
    public void testCompleteRequest() {
        //Create what is needed to run the tests
        NodeManager nodeManager = new NodeManager();
        nodeManager.updateNodes();
        UserManager userManager = new UserManager();
        userManager.updateUsers();
        CleanUpManager cleanUpManager = new CleanUpManager(nodeManager, userManager);
        Timestamp created = Timestamp.valueOf("1960-01-01 23:03:20.00");
        Timestamp completed = Timestamp.valueOf("1961-01-01 23:03:20.00");
        DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
        databaseGargoyle.createConnection();

        //Update the request and make sure it is changed in the database
        CleanUpRequest completedRequest = new CleanUpRequest("not completed", created.toLocalDateTime(),
                created.toLocalDateTime(),"type1","description1",
                nodeManager.getNode("GLABS015L2"), userManager.getUser("admin1"));
        cleanUpManager.completeRequest(completedRequest);
        ResultSet rs = databaseGargoyle.executeQueryOnDatabase("SELECT * FROM CLEANUPREQUEST WHERE name = 'not completed' AND TIMECREATED = '" +created+"'", databaseGargoyle.getStatement());
        try {
            if (rs.next()){
                assertFalse(rs.getTimestamp("timecompleted").equals(created));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //Revert changes and confirm they are good now
        databaseGargoyle.executeUpdateOnDatabase("UPDATE CLEANUPREQUEST SET " +
                "TIMECOMPLETED = '" + created + "' " +
                "WHERE NAME = 'not completed' AND TIMECREATED = '" + created + "'", databaseGargoyle.getStatement());
        ResultSet rs2 = databaseGargoyle.executeQueryOnDatabase("SELECT * FROM CLEANUPREQUEST WHERE name = 'not completed' AND TIMECREATED = '" +created+"'", databaseGargoyle.getStatement());
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
        NodeManager nodeManager = new NodeManager();
        UserManager userManager = new UserManager();
        CleanUpManager cleanUpManager = new CleanUpManager(nodeManager, userManager);

        List<CleanUpRequest> completed = cleanUpManager.getCompleted();
        //Test that there is only one item in the list returned
        assertEquals(1, completed.size());
        //Test that the completed request is in there
        assertTrue(completed.get(0).getName().equals("completed"));
    }

    @Test
    public void testGetRequestsBy() {
        NodeManager nodeManager = new NodeManager();
        UserManager userManager = new UserManager();
        CleanUpManager cManager = new CleanUpManager(nodeManager, userManager);

        userManager.updateUsers();
        cManager.updateRequests();

        List<CleanUpRequest> requestsByUser = cManager.getRequestsBy(userManager.getUser("janitor1"));

        System.out.println(cManager.getRequests());
        System.out.println(requestsByUser);

        assertTrue(requestsByUser.size() == 1);
        assertTrue(requestsByUser.get(0).getUser().getUserID().equals("janitor1"));

        requestsByUser = cManager.getRequestsBy(userManager.getUser("staff1"));

        System.out.println(requestsByUser);

        assertTrue(requestsByUser.size() == 0);
    }
}
