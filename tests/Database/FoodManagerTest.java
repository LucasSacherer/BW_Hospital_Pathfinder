package Database;

import Entity.FoodRequest;
import Entity.Node;
import Entity.User;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

public class FoodManagerTest {
    //Quinn currently working on
    /*
    @Test
    public void testUpdateRequests() {
        FoodManager foodManager = new FoodManager();
        //Check if the user is there before update
        assertEquals(null, foodManager.getFoodRequest("spill"));

        //Check if the user is there after update
        foodManager.updateRequests();
        LocalDateTime now = LocalDateTime.now();
        Node node = new Node("1", 1, 1, "1", "1", "1", "1", "1");
        User user = new User("po", "un1", "pw1", true, "dep");
        FoodRequest spill = new FoodRequest("spill", now, null, "spill", "spill blood", node, user);

        User po = userManager.getUser("po");
        assertEquals("po", po.getUserID());
        assertEquals("poUN", po.getUsername());
        assertEquals("poPW", po.getPassword());
        assertEquals(false, po.getAdminFlag());
        assertEquals("poD", po.getDepartment());
    }
    */
}
