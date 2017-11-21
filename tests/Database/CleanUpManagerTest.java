package Database;

import org.junit.Test;

public class CleanUpManagerTest {

    @Test
    public void testAddAndDelete(){
        NodeManager nodeManager = new NodeManager();
        UserManager userManager = new UserManager();
        CleanUpManager cleanUpManager = new CleanUpManager(nodeManager, userManager);
        //Lucas is working here
    }
}
