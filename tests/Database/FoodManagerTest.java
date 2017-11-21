package Database;

import Entity.FoodRequest;
import Entity.Node;
import Entity.User;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

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
        assertEquals(null, req.getOrder());
    }
}
