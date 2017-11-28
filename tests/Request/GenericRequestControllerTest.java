package Request;

import Database.*;
import Entity.*;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GenericRequestControllerTest {
    @Test
    public void deleteRequestTest(){
        NodeManager nodeManager = new NodeManager();
        nodeManager.updateNodes();
        UserManager userManager = new UserManager();
        userManager.updateUsers();
        CleanUpManager cleanUpManager = new CleanUpManager(nodeManager, userManager);
        cleanUpManager.updateRequests();
        InterpreterManager interpreterManager = new InterpreterManager(nodeManager, userManager);
        interpreterManager.updateRequests();
        FoodManager foodManager = new FoodManager(nodeManager, userManager);
        foodManager.updateRequests();
        GenericRequestController genericRequestController = new GenericRequestController(cleanUpManager, foodManager, interpreterManager);

        CleanUpRequest clean = cleanUpManager.getRequestByName("deleteme");
        FoodRequest food = foodManager.getRequestByName("deleteme");
        InterpreterRequest interp = interpreterManager.getRequestByName("deleteme");

        //Test deleting a CleanUpRequest
        assertEquals(2, cleanUpManager.getRequests().size());
        genericRequestController.deleteRequest(clean);
        assertEquals(1, cleanUpManager.getRequests().size());

        //Test deleting a FoodRequest
        assertEquals(1, foodManager.getRequests().size());
        genericRequestController.deleteRequest(food);
        assertEquals(0, foodManager.getRequests().size());

        //Test deleting a InterpreterRequest
        assertEquals(2, interpreterManager.getRequests().size());
        genericRequestController.deleteRequest(interp);
        assertEquals(1, interpreterManager.getRequests().size());

    }

    @Test
    public void getAllRequestsByUserTest(){
        NodeManager nodeManager = new NodeManager();
        nodeManager.updateNodes();
        UserManager userManager = new UserManager();
        userManager.updateUsers();
        CleanUpManager cleanUpManager = new CleanUpManager(nodeManager, userManager);
        InterpreterManager interpreterManager = new InterpreterManager(nodeManager, userManager);
        FoodManager foodManager = new FoodManager(nodeManager, userManager);
        GenericRequestController genericRequestController = new GenericRequestController(cleanUpManager, foodManager, interpreterManager);
        User admin2 = userManager.getUser("admin2");
        User janitor1 = userManager.getUser("janitor1");

        //Run the method and confirm it has expected results
        List<Request> results = genericRequestController.getAllRequestsByUser(admin2);
        assertEquals(1, results.size());
        results = genericRequestController.getAllRequestsByUser(janitor1);
        assertEquals(1, results.size());
    }
}
