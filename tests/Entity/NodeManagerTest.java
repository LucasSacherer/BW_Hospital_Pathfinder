package Entity;

import Database.NodeManager;
import entity.Node;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class NodeManagerTest {

    @Test
    public void checkUpdateNodes(){
        NodeManager manager = new NodeManager();
        manager.updateNodes();
    }

    @Test
    public void testGetNode(){
        NodeManager manager = new NodeManager();
        manager.updateNodes();
        assertEquals(manager.getNode("GHALL00601").getNodeID(),"GHALL00601");
        assertNull(manager.getNode(""));
        assertNull(manager.getNode(null));
    }

    @Test
    public void testGetVisitableNodes(){
        NodeManager manager = new NodeManager();
        manager.updateNodes();
        /*List<Node> visitable = manager.getVisitableNodes();
        boolean ok = true;
        if (visitable.size() == 0){
            ok = false;
        }
        for (Node node: visitable){
            if (!node.isVisitable()){
                ok = false;
            }
        }
        assertTrue(ok);*/
    }

    @Test
    public void testAddDeleteNode(){
        NodeManager manager = new NodeManager();
        manager.updateNodes();
        Node test = new Node("1",2,3,"1","building","type","lName","sName");
        manager.addNode(test);
        assertEquals(manager.getNode(test.getNodeID()).getNodeID(),test.getNodeID());
        manager.removeNode(test);
        assertNull(manager.getNode(test.getNodeID()));
    }

    @Test
    public void testUpdateNode(){
        NodeManager manager = new NodeManager();
        manager.updateNodes();
        Node test = new Node("1",2,3,"1","building","type","lName","sName");
        manager.addNode(test);
        Node testModified = new Node("1",6,6,"1","building","type","lName","sName");
        manager.updateNode(testModified);
        assertEquals(manager.getNode(test.getNodeID()).getXcoord(),testModified.getXcoord());
        manager.removeNode(testModified);
    }

    @Test
    public void testNearestNode(){
        NodeManager manager = new NodeManager();
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
        NodeManager manager = new NodeManager();
        manager.updateNodes();
        Node test = new Node("1",1,1,"1","building","type","lName","sName");
        Node test2 = new Node("2",2, 2,"1","building","type","lName","sName");
        Node test3 = new Node("3",5,5,"1","building","bathroom","lName","sName");
        Node test4 = new Node("4",3,3,"1","building","bathroom","lName","sName");
        manager.addNode(test);
        manager.addNode(test2);
        manager.addNode(test3);
        manager.addNode(test4);
        String nearestNode = manager.nearestLoc(0,0,"1", "bathroom").getNodeID();
        Node nearestNull = manager.nearestLoc(0, 0, "1","monkey");

        manager.removeNode(test);
        manager.removeNode(test2);
        manager.removeNode(test3);
        manager.removeNode(test4);

        assertEquals(nearestNode, test4.getNodeID());
        assertNull(nearestNull);
    }
}
