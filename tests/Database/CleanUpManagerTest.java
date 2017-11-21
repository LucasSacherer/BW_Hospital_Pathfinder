package Database;

import Entity.CleanUpRequest;
import Entity.User;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CleanUpManagerTest {

    @Test
    public void testAddAndDelete(){
        NodeManager nodeManager = new NodeManager();
        UserManager userManager = new UserManager();
        CleanUpManager cleanUpManager = new CleanUpManager(nodeManager, userManager);

        nodeManager.updateNodes();
        User user1 = new User("test","test","password",true,"dep");
        userManager.addUser(user1);

        CleanUpRequest cReq1 = new CleanUpRequest("TEST1", LocalDateTime.now(),null,"CleanUp",
                "Disc",nodeManager.getNode("AHALL0070G"),user1);
        CleanUpRequest cReq2 = new CleanUpRequest("TEST2", LocalDateTime.now(),LocalDateTime.now(),"CleanUp",
                "Disc test",nodeManager.getNode("AHALL0070G"),user1);

        cleanUpManager.addRequest(cReq1);
        cleanUpManager.addRequest(cReq2);

        List<CleanUpRequest> requests = cleanUpManager.getRequests();
        CleanUpRequest test1 = requests.get(0);
        CleanUpRequest test2 = requests.get(1);

        cleanUpManager.deleteRequest(cReq1);
        cleanUpManager.deleteRequest(cReq2);
        userManager.removeUser(user1);

        assertEquals(cReq1.getName(),test1.getName());
        assertEquals(cReq2.getName(),test2.getName());
    }
}
