package Editor;

import Database.*;
import DatabaseSetup.DatabaseGargoyle;
import Entity.Edge;
import Entity.Node;
import Request.GenericRequestController;
import com.sun.xml.internal.ws.api.config.management.policy.ManagementAssertion;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

public class NodeEditControllerTest {
    DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();

    @Test
    public void AddRemoveEditNode() throws Exception {
        NodeManager nManager = new NodeManager(databaseGargoyle);
        SettingsManager sManager = new SettingsManager(databaseGargoyle);
        EdgeManager eManager = new EdgeManager(databaseGargoyle,  nManager);
        UserManager userManager = new UserManager(databaseGargoyle);
        CleanUpManager cleanUpManager = new CleanUpManager(databaseGargoyle, nManager,userManager);
        FoodManager foodManager = new FoodManager(databaseGargoyle, nManager,userManager);
        InterpreterManager interpreterManager = new InterpreterManager(databaseGargoyle, nManager,userManager);
        GenericRequestController genericRequestController = new GenericRequestController(cleanUpManager, foodManager, interpreterManager);
        NodeEditController editor = new NodeEditController(nManager, sManager,eManager,genericRequestController);

        nManager.updateNodes();
        Node test = new Node("1",1,1,"1","building","type","lName","sName");
        Node test2 = new Node("2",2, 2,"1","building","type","lName","sName");
        Node test3 = new Node("3",5,5,"1","building","bathroom","lName","sName");
        Node test4 = new Node("4",3,3,"1","building","bathroom","lName","sName");
        editor.addNode(test);
        editor.addNode(test2);
        editor.addNode(test3);
        editor.addNode(test4);

        assertEquals(nManager.getNode("1").getNodeID(), test.getNodeID());
        assertEquals(nManager.getNode("2").getNodeID(), test2.getNodeID());
        assertEquals(nManager.getNode("3").getNodeID(), test3.getNodeID());
        assertEquals(nManager.getNode("4").getNodeID(), test4.getNodeID());

        Node testEdit = new Node("1",1,1,"1","monkey","type","lName","sName");


        editor.editNode(testEdit);

        assertEquals(nManager.getNode("1").getBuilding(), "monkey");

        editor.deleteNode(test);
        editor.deleteNode(test2);
        editor.deleteNode(test3);
        editor.deleteNode(test4);
        editor.deleteNode(testEdit);
    }

    @Test
    public void setKioskTest() throws Exception {
        Node test = new Node("1",1,1,"1","building","type","lName","sName");

        NodeManager nManager = new NodeManager(databaseGargoyle);
        nManager.updateNodes();
        SettingsManager sManager = new SettingsManager(databaseGargoyle);
        EdgeManager eManager = new EdgeManager(databaseGargoyle, nManager);
        UserManager um = new UserManager(databaseGargoyle);
        CleanUpManager cleanUpManager = new CleanUpManager(databaseGargoyle, nManager,um);
        FoodManager foodManager = new FoodManager(databaseGargoyle, nManager,um);
        InterpreterManager interpreterManager = new InterpreterManager(databaseGargoyle, nManager,um);
        GenericRequestController genericRequestController = new GenericRequestController(cleanUpManager, foodManager, interpreterManager);
        NodeEditController editor = new NodeEditController(nManager, sManager, eManager, genericRequestController);

        editor.setKioskLocation(test);

        assertEquals(editor.settingsManager.getSetting("Default Node"), test.getNodeID());
        editor.setKioskLocation(nManager.getNode("GHALL03802"));
    }

    @Test
    public void deleteNode() {
        NodeManager nManager = new NodeManager(databaseGargoyle);
        SettingsManager sManager = new SettingsManager(databaseGargoyle);
        EdgeManager eManager = new EdgeManager(databaseGargoyle, nManager);
        UserManager um = new UserManager(databaseGargoyle);
        CleanUpManager cleanUpManager = new CleanUpManager(databaseGargoyle, nManager,um);
        FoodManager foodManager = new FoodManager(databaseGargoyle, nManager,um);
        InterpreterManager interpreterManager = new InterpreterManager(databaseGargoyle, nManager,um);
        GenericRequestController genericRequestController = new GenericRequestController(cleanUpManager, foodManager, interpreterManager);
        NodeEditController editor = new NodeEditController(nManager, sManager, eManager, genericRequestController);

        nManager.updateNodes();
        Node test = new Node("1",1,1,"1","building","type","lName","sName");
        Node test2 = new Node("2",1, 2,"1","building","type","lName","sName");
        Node test3 = new Node("3",2,1,"1","building","bathroom","lName","sName");
        Node test4 = new Node("4",3,1,"1","building","bathroom","lName","sName");
        editor.addNode(test);
        editor.addNode(test2);
        editor.addNode(test3);
        editor.addNode(test4);

        Edge e1 = new Edge(test,test2);
        Edge e2 = new Edge(test,test3);
        Edge e3 = new Edge(test3,test4);
        Edge e4 = new Edge(test4, test);

        eManager.addEdge(e1);
        eManager.addEdge(e2);
        eManager.addEdge(e3);
        eManager.addEdge(e4);


        editor.deleteNode(test);

        System.out.println(nManager.getNode(test.getNodeID()));

        eManager.removeEdge(e1);
        eManager.removeEdge(e2);
        eManager.removeEdge(e3);
        eManager.removeEdge(e4);

        nManager.removeNode(test);
        nManager.removeNode(test2);
        nManager.removeNode(test3);
        nManager.removeNode(test4);

        //assertTrue(nManager.getNode(test.getNodeID()) == null);
    }
}