package Request;

import Database.*;
import DatabaseSetup.DatabaseGargoyle;
import Entity.*;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class GenericRequestControllerTest {

    @Test
    public void deleteRequestTest(){
        DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
        AdminLogManager adminLogManager = new AdminLogManager(databaseGargoyle);
        NodeManager nodeManager = new NodeManager(databaseGargoyle, adminLogManager);
        UserManager userManager = new UserManager(databaseGargoyle, adminLogManager);
        CleanUpManager cleanUpManager = new CleanUpManager(databaseGargoyle, nodeManager, userManager);
        InterpreterManager interpreterManager = new InterpreterManager(databaseGargoyle, nodeManager, userManager);
        FoodManager foodManager = new FoodManager(databaseGargoyle, nodeManager, userManager);
        databaseGargoyle.attachManager(nodeManager);
        databaseGargoyle.attachManager(userManager);
        databaseGargoyle.attachManager(cleanUpManager);
        databaseGargoyle.attachManager(interpreterManager);
        databaseGargoyle.attachManager(foodManager);
        databaseGargoyle.attachManager(adminLogManager);
        databaseGargoyle.notifyManagers();

        GenericRequestController genericRequestController = new GenericRequestController(cleanUpManager, foodManager, interpreterManager);

        int origCleanSize = cleanUpManager.getRequests().size();
        int origFoodSize = foodManager.getRequests().size();
        int origInterpSize = interpreterManager.getRequests().size();

        //Test deleting a CleanUpRequest
        CleanUpRequest clean = cleanUpManager.getRequestByName("deleteme");
        assertEquals(origCleanSize, cleanUpManager.getRequests().size());
        genericRequestController.deleteRequest(clean);
        assertEquals(origCleanSize - 1, cleanUpManager.getRequests().size());

        //Test deleting a FoodRequest
        FoodRequest food = foodManager.getRequestByName("deleteme");
        assertEquals(origFoodSize, foodManager.getRequests().size());
        genericRequestController.deleteRequest(food);
        assertEquals(origFoodSize - 1, foodManager.getRequests().size());

        //Test deleting a InterpreterRequest
        InterpreterRequest interp = interpreterManager.getRequestByName("deleteme");
        assertEquals(origInterpSize, interpreterManager.getRequests().size());
        genericRequestController.deleteRequest(interp);
        assertEquals(origInterpSize - 1, interpreterManager.getRequests().size());

        //Revert changes
        interpreterManager.addRequest(interp);
        foodManager.addRequest(food);
        cleanUpManager.addRequest(clean);
    }

    @Test
    public void getAllRequestsByUserTest(){
        DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
        AdminLogManager adminLogManager = new AdminLogManager(databaseGargoyle);
        NodeManager nodeManager = new NodeManager(databaseGargoyle, adminLogManager);
        UserManager userManager = new UserManager(databaseGargoyle, adminLogManager);
        CleanUpManager cleanUpManager = new CleanUpManager(databaseGargoyle, nodeManager, userManager);
        InterpreterManager interpreterManager = new InterpreterManager(databaseGargoyle, nodeManager, userManager);
        FoodManager foodManager = new FoodManager(databaseGargoyle, nodeManager, userManager);
        databaseGargoyle.attachManager(nodeManager);
        databaseGargoyle.attachManager(userManager);
        databaseGargoyle.attachManager(cleanUpManager);
        databaseGargoyle.attachManager(interpreterManager);
        databaseGargoyle.attachManager(foodManager);
        databaseGargoyle.attachManager(adminLogManager);
        databaseGargoyle.notifyManagers();

        GenericRequestController genericRequestController = new GenericRequestController(cleanUpManager, foodManager, interpreterManager);
        User admin2 = userManager.getUser("admin2");
        User janitor1 = userManager.getUser("janitor1");

        //Run the method and confirm it has expected results
        List<Request> results = genericRequestController.getAllRequestsByUser(admin2);
        assertEquals(1, results.size());
        results = genericRequestController.getAllRequestsByUser(janitor1);
        assertEquals(1, results.size());
    }

    @Test
    public void getAllRequestsByDepartmentTest(){
        DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
        AdminLogManager adminLogManager = new AdminLogManager(databaseGargoyle);
        NodeManager nodeManager = new NodeManager(databaseGargoyle, adminLogManager);
        UserManager userManager = new UserManager(databaseGargoyle, adminLogManager);
        CleanUpManager cleanUpManager = new CleanUpManager(databaseGargoyle, nodeManager, userManager);
        InterpreterManager interpreterManager = new InterpreterManager(databaseGargoyle, nodeManager, userManager);
        FoodManager foodManager = new FoodManager(databaseGargoyle, nodeManager, userManager);
        databaseGargoyle.attachManager(nodeManager);
        databaseGargoyle.attachManager(userManager);
        databaseGargoyle.attachManager(cleanUpManager);
        databaseGargoyle.attachManager(interpreterManager);
        databaseGargoyle.attachManager(foodManager);
        databaseGargoyle.attachManager(adminLogManager);
        databaseGargoyle.notifyManagers();

        GenericRequestController genericRequestController = new GenericRequestController(cleanUpManager, foodManager, interpreterManager);

        //make sure it works for food requests
        List<Request> food = genericRequestController.getAllRequestsByDepartment("food");
        assertTrue(food.contains(foodManager.getRequestByName("food1")));
        assertTrue(food.contains(foodManager.getRequestByName("deleteme")));

        //make sure it works for Interpreter requests
        List<Request> interpreter = genericRequestController.getAllRequestsByDepartment("interpreter");
        assertTrue(interpreter.contains(interpreterManager.getRequestByName("deleteme")));
        assertTrue(interpreter.contains(interpreterManager.getRequestByName("not completed")));

        //make sure it works for CleanUp requests
        List<Request> clean = genericRequestController.getAllRequestsByDepartment("janitorial");
        assertTrue(clean.contains(cleanUpManager.getRequestByName("completed2")));
        assertTrue(clean.contains(cleanUpManager.getRequestByName("not completed")));

    }
}
