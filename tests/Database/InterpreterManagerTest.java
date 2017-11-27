package Database;

import DatabaseSetup.DatabaseGargoyle;
import Entity.InterpreterRequest;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.junit.Assert.*;

public class InterpreterManagerTest {
    @Test
    public void TestAddandDelete() {
        NodeManager nodeManager = new NodeManager();
        nodeManager.updateNodes();
        UserManager userManager = new UserManager();
        userManager.updateUsers();
        InterpreterManager iManager = new InterpreterManager(nodeManager, userManager);
        LocalDateTime createdDate = LocalDateTime.now();

        InterpreterRequest request = new InterpreterRequest("test", createdDate, createdDate, "type", "description", nodeManager.getNode("GINFO01902"), userManager.getUser("admin1"), "English");
        iManager.addRequest(request);

        //Test to see if the added request is in the database
        DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
        databaseGargoyle.createConnection();
        ResultSet rs = databaseGargoyle.executeQueryOnDatabase("SELECT * FROM INTERPRETERREQUEST WHERE name = 'test' AND TIMECREATED = '" + Timestamp.valueOf(createdDate)+"'", databaseGargoyle.getStatement());
        try {
            while (rs.next()){
                assertTrue(rs.getString("name").equals("test"));
                assertEquals(createdDate, rs.getTimestamp("timecreated").toLocalDateTime());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //Test to see if after removal, the user is no longer in the database
        iManager.deleteRequest(request);
        rs = databaseGargoyle.executeQueryOnDatabase("SELECT * FROM INTERPRETERREQUEST WHERE name = 'test' AND TIMECREATED = '" +Timestamp.valueOf(createdDate)+"'", databaseGargoyle.getStatement());
        try {
            assertFalse(rs.next());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        databaseGargoyle.destroyConnection();
    }

    @Test
    public void TestUpdateRequest() throws Exception {
        NodeManager nodeManager = new NodeManager();
        nodeManager.updateNodes();
        UserManager userManager = new UserManager();
        userManager.updateUsers();
        InterpreterManager iManager = new InterpreterManager(nodeManager, userManager);
        Timestamp created = Timestamp.valueOf("1960-01-01 23:03:20.00");
        Timestamp completed = Timestamp.valueOf("1961-01-01 23:03:20.00");
        LocalDateTime createdDate = LocalDateTime.now();

        InterpreterRequest request = new InterpreterRequest("test", createdDate, createdDate, "type", "description", nodeManager.getNode("GINFO01902"), userManager.getUser("admin1"), "English");
        iManager.addRequest(request);

        InterpreterRequest updatedRequest = new InterpreterRequest("test", createdDate, createdDate, "NewType", "description", nodeManager.getNode("GINFO01902"), userManager.getUser("admin1"), "English");
        iManager.updateRequest(updatedRequest);
        iManager.updateRequests();

        assertEquals(iManager.getRequests().get(2).getType(), "NewType");

        iManager.updateRequest(request);
        iManager.updateRequests();

        assertEquals(iManager.getRequests().get(2).getType(), "type");

        iManager.deleteRequest(updatedRequest);
    }

    @Test
    public void testCompleteRequest() {
        //Create what is needed to run the tests
        NodeManager nodeManager = new NodeManager();
        nodeManager.updateNodes();
        UserManager userManager = new UserManager();
        userManager.updateUsers();
        InterpreterManager iManager = new InterpreterManager(nodeManager, userManager);
        Timestamp created = Timestamp.valueOf("1960-01-01 23:03:20.00");
        Timestamp completed = Timestamp.valueOf("1961-01-01 23:03:20.00");
        DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
        databaseGargoyle.createConnection();

        //Update the request and make sure it is changed in the database
        InterpreterRequest completedRequest = new InterpreterRequest("not complete", created.toLocalDateTime(),
                completed.toLocalDateTime(),"type1","description1",
                nodeManager.getNode("GLABS015L2"), userManager.getUser("admin1"), "English");
        iManager.addRequest(completedRequest);
        iManager.completeRequest(completedRequest);
        ResultSet rs = databaseGargoyle.executeQueryOnDatabase("SELECT * FROM INTERPRETERREQUEST WHERE name = 'not complete' AND TIMECREATED = '" +created+"'", databaseGargoyle.getStatement());
        try {
            if(rs.next()) {
                assertTrue(rs.getTimestamp("timecompleted").equals(completed));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //Revert changes and confirm they are good now
        databaseGargoyle.executeUpdateOnDatabase("UPDATE INTERPRETERREQUEST SET " +
                "TIMECOMPLETED = '" + created + "' " +
                "WHERE NAME = 'not complete' AND TIMECREATED = '" + created + "'", databaseGargoyle.getStatement());
        ResultSet rs2 = databaseGargoyle.executeQueryOnDatabase("SELECT * FROM INTERPRETERREQUEST WHERE name = 'not complete' AND TIMECREATED = '" +created+"'", databaseGargoyle.getStatement());
        try {
            if(rs2.next()) {
                assertTrue(rs2.getTimestamp("timecompleted").equals(created));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        iManager.deleteRequest(completedRequest);

        databaseGargoyle.destroyConnection();
    }

    @Test
    public void testGetCompleted() {
        NodeManager nodeManager = new NodeManager();
        UserManager userManager = new UserManager();
        InterpreterManager iManager = new InterpreterManager(nodeManager, userManager);

        List<InterpreterRequest> completed = iManager.getCompleted();
        //Test that there is only one item in the list returned
        assertEquals(1, completed.size());
        //Test that the completed request is in there
        System.out.println(completed.get(0).getName());
        assertTrue(completed.get(0).getName().equals("completed"));
    }

    @Test
    public void testGetRequestsBy() {
        NodeManager nodeManager = new NodeManager();
        UserManager userManager = new UserManager();
        InterpreterManager iManager = new InterpreterManager(nodeManager, userManager);

        userManager.updateUsers();
        iManager.updateRequests();


        Timestamp created = Timestamp.valueOf("1960-01-01 23:03:20.00");
        /*
        InterpreterRequest testNotComplete = new InterpreterRequest("not complete", created.toLocalDateTime(),
                created.toLocalDateTime(),"typeTest","descriptionTest",
                nodeManager.getNode("GLABS015L2"), userManager.getUser("staff1"), "English");

        iManager.addRequest(testNotComplete);
        */
        List<InterpreterRequest> requestsByUser = iManager.getRequestsBy(userManager.getUser("admin2"));

        //iManager.deleteRequest(testNotComplete);

        System.out.println(iManager.getRequests());
        System.out.println(requestsByUser);

        assertTrue(requestsByUser.size() == 1);
        assertTrue(requestsByUser.get(0).getUser().getUserID().equals("admin2"));

        requestsByUser = iManager.getRequestsBy(userManager.getUser("staff1"));

        System.out.println(requestsByUser);

        assertTrue(requestsByUser.size() == 0);
    }
}