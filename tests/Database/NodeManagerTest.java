package Database;

import Database.NodeManager;
import DatabaseSetup.DatabaseGargoyle;
import Entity.AdminLog;
import Entity.Node;
import org.junit.Test;

import static org.junit.Assert.*;

public class NodeManagerTest {

    @Test
    public void checkUpdateNodes(){
        DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
        AdminLogManager adminLogManager = new AdminLogManager(databaseGargoyle);
        NodeManager manager = new NodeManager(databaseGargoyle, adminLogManager);
        assertTrue(manager.getAllNodes().size() == 0);
        manager.update();
        assertFalse(manager.getAllNodes().size() == 0);
    }

    @Test
    public void testGetNode(){
        DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
        AdminLogManager adminLogManager = new AdminLogManager(databaseGargoyle);
        NodeManager manager = new NodeManager(databaseGargoyle, adminLogManager);
        databaseGargoyle.attachManager(manager);
        databaseGargoyle.notifyManagers();

        assertEquals(manager.getNode("GHALL00601").getNodeID(),"GHALL00601");
        assertNull(manager.getNode(""));
        assertNull(manager.getNode(null));
    }

    @Test
    public void testAddDeleteNode(){
        DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
        AdminLogManager adminLogManager = new AdminLogManager(databaseGargoyle);
        NodeManager manager = new NodeManager(databaseGargoyle, adminLogManager);
        databaseGargoyle.attachManager(manager);
        databaseGargoyle.notifyManagers();

        Node test = new Node("1",2,3,"1","building","type","lName","sName");
        manager.addNode(test);
        assertEquals(manager.getNode(test.getNodeID()).getNodeID(),test.getNodeID());
        manager.removeNode(test);
        assertNull(manager.getNode(test.getNodeID()));
    }

    @Test
    public void testUpdateNode(){
        DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
        AdminLogManager adminLogManager = new AdminLogManager(databaseGargoyle);
        NodeManager manager = new NodeManager(databaseGargoyle, adminLogManager);
        databaseGargoyle.attachManager(manager);
        databaseGargoyle.notifyManagers();

        Node test = new Node("1",2,3,"1","building","type","lName","sName");
        manager.addNode(test);
        Node testModified = new Node("1",6,6,"1","building","type","lName","sName");
        manager.updateNode(testModified);
        assertEquals(manager.getNode(test.getNodeID()).getXcoord(),testModified.getXcoord());
        manager.removeNode(testModified);
    }

    @Test
    public void testNearestNode(){
        DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
        AdminLogManager adminLogManager = new AdminLogManager(databaseGargoyle);
        NodeManager manager = new NodeManager(databaseGargoyle, adminLogManager);
        databaseGargoyle.attachManager(manager);
        databaseGargoyle.notifyManagers();

        Node test = new Node("1",50,0,"1","building","type","lName","sName");
        Node test2 = new Node("2",99, 99,"1","building","type","lName","sName");
        Node test3 = new Node("3",2,2,"1","building","type","lName","sName");
        Node test4 = new Node("4",5,5,"1","building","type","lName","sName");
        manager.addNode(test);
        manager.addNode(test2);
        manager.addNode(test3);
        manager.addNode(test4);
        String nodeID = manager.nearestNode(3, 3, "1").getNodeID();

        manager.removeNode(test);
        manager.removeNode(test2);
        manager.removeNode(test3);
        manager.removeNode(test4);

        assertEquals(test3.getNodeID(),nodeID);
    }

    @Test
    public void testNearestLoc(){
        DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
        AdminLogManager adminLogManager = new AdminLogManager(databaseGargoyle);
        NodeManager manager = new NodeManager(databaseGargoyle, adminLogManager);
        databaseGargoyle.attachManager(manager);
        databaseGargoyle.notifyManagers();

        Node test = new Node("1",1,1,"1","building","type","lName","sName");
        Node test2 = new Node("2",2, 2,"1","building","type","lName","sName");
        Node test3 = new Node("3",5,5,"1","building","bathroom","lName","sName");
        Node test4 = new Node("4",3,3,"1","building","bathroom","lName","sName");
        manager.addNode(test);
        manager.addNode(test2);
        manager.addNode(test3);
        manager.addNode(test4);
        String nearestNode = manager.nearestLoc(0,0, "bathroom").getNodeID();
        Node nearestNull = manager.nearestLoc(0, 0,"monkey");

        manager.removeNode(test);
        manager.removeNode(test2);
        manager.removeNode(test3);
        manager.removeNode(test4);

        assertEquals(nearestNode, test4.getNodeID());
        assertNull(nearestNull);
    }
}
