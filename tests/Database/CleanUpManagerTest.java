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
}
