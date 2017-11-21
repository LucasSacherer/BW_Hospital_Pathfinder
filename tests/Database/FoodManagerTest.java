package Database;

import Entity.FoodRequest;
import Entity.Node;
import Entity.User;
import org.junit.Test;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class FoodManagerTest {
    @Test
    public void testUpdateRequests() {
        FoodManager foodManager = new FoodManager();

        //Check if the user is there before update
        assertEquals(null, foodManager.getFoodRequest("food1"));

        //Check if the user is there after update
        foodManager.updateRequests();
        FoodRequest req = foodManager.getFoodRequest("food1");
        assertEquals("food1", req.getName());
        assertEquals("type1", req.getType());
        assertEquals("description1", req.getDescription());
        assertEquals("GRETL03501", req.getNode().getNodeID());
        assertEquals("admin1", req.getUser().getUserID());
        assertEquals(3, req.getOrder().size());
        assertTrue(req.getOrder().contains("cheeseburger"));
        assertTrue(req.getOrder().contains("lasagna"));
    }

    @Test
    public void testUpdateRequest() {
        NodeManager nm = new NodeManager();
        nm.updateNodes();
        UserManager um = new UserManager();
        um.updateUsers();
        FoodManager foodManager = new FoodManager();
        Timestamp time = Timestamp.valueOf("1960-01-01 23:03:20.000000000");
        ArrayList<String> originalOrder = new ArrayList<>();
        originalOrder.add("cheeseburger");
        originalOrder.add("cheeseburger");
        originalOrder.add("lasagna");
        ArrayList<String> newOrder = new ArrayList<>();
        newOrder.add("ham");
        newOrder.add("turkey");
        FoodRequest originalReq = new FoodRequest("food1", time.toLocalDateTime(),time.toLocalDateTime(),
                "type1", "description1", nm.getNode("GRETL03501"), um.getUser("admin1"), originalOrder);
        FoodRequest newReq = new FoodRequest("food1", time.toLocalDateTime(),time.toLocalDateTime(),
                "type2", "description2", nm.getNode("WELEV00ML2"), um.getUser("admin2"), newOrder);
        foodManager.updateRequest(newReq);
        FoodRequest testReq = foodManager.getFoodRequest("food1");
        assertEquals("type2", testReq.getType());
        assertEquals("description2", testReq.getDescription());
        assertEquals("WELEV00ML2", testReq.getNode().getNodeID());
        assertEquals("admin2", testReq.getUser().getUserID());
        assertTrue(testReq.getOrder().contains("ham"));

        //Revert update
        foodManager.updateRequest(originalReq);
        FoodRequest testReq2 = foodManager.getFoodRequest("food1");
        assertEquals("type1", testReq2.getType());
        assertEquals("description1", testReq2.getDescription());
        assertEquals("GRETL03501", testReq2.getNode().getNodeID());
        assertEquals("admin1", testReq2.getUser().getUserID());
        assertTrue(testReq2.getOrder().contains("cheeseburger"));
    }

    @Test
    public void testCompleteRequest() {
        FoodManager foodManager = new FoodManager();
        NodeManager nm = new NodeManager();
        nm.updateNodes();
        UserManager um = new UserManager();
        um.updateUsers();
        ArrayList<String> originalOrder = new ArrayList<>();
        originalOrder.add("cheeseburger");
        originalOrder.add("cheeseburger");
        originalOrder.add("lasagna");
        Timestamp time = Timestamp.valueOf("1960-01-01 23:03:20.000000000");

        FoodRequest completedRequest = new FoodRequest("food1", time.toLocalDateTime(),time.toLocalDateTime(),
                "type1", "description1", nm.getNode("GRETL03501"), um.getUser("admin1"), originalOrder);
        foodManager.completeRequest(completedRequest);
        FoodRequest testReq = foodManager.getFoodRequest("food1");
        assertEquals("type1", testReq.getType());
        assertEquals("description1", testReq.getDescription());
        assertEquals("GRETL03501", testReq.getNode().getNodeID());
        assertEquals("admin1", testReq.getUser().getUserID());
        assertEquals(0, testReq.getOrder().size());

        //Revert changes
        FoodRequest original = new FoodRequest("food1", time.toLocalDateTime(),time.toLocalDateTime(),
                "type1", "description1", nm.getNode("GRETL03501"), um.getUser("admin1"), originalOrder);
        foodManager.updateRequest(original);
    }

    @Test
    public void testAddDeleteRequest() {
        FoodManager foodManager = new FoodManager();
        NodeManager nm = new NodeManager();
        nm.updateNodes();
        UserManager um = new UserManager();
        um.updateUsers();
        Timestamp time = Timestamp.valueOf(LocalDateTime.now());
        ArrayList<String> order = new ArrayList<>();
        order.add("food1");
        order.add("food2");
        FoodRequest request = new FoodRequest("name", time.toLocalDateTime(),
                null, "type", "description", nm.getNode("IHALL01303"), um.getUser("admin1"), order);
        foodManager.addRequest(request);
        FoodRequest testReq = foodManager.getFoodRequest("name");
        assertEquals("type", testReq.getType());
        assertEquals("description", testReq.getDescription());
        assertEquals("IHALL01303", testReq.getNode().getNodeID());
        assertEquals("admin1", testReq.getUser().getUserID());
        assertEquals(2, testReq.getOrder().size());
        assertTrue(testReq.getOrder().contains("food2"));

        //Revert changes
        foodManager.deleteRequest(request);
        assertEquals(null, foodManager.getFoodRequest("name"));
    }

    @Test
    public void testGetCompleted() {
        FoodManager foodManager = new FoodManager();
        ArrayList<FoodRequest> completed = foodManager.getCompleted();
        assertTrue(completed.get(0).getName().equals("food2"));
    }
}
