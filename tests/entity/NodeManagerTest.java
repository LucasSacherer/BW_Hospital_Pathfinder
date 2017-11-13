package entity;

import org.junit.Test;

import java.util.List;

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
        assertEquals(manager.getNode("GHALL002L2").getNodeID(),"GHALL002L2");
        assertNull(manager.getNode(""));
        assertNull(manager.getNode(null));
    }

    @Test
    public void testGetVisitableNodes(){
        NodeManager manager = new NodeManager();
        manager.updateNodes();
        List<Node> visitable = manager.getVisitableNodes();
        boolean ok = true;
        if (visitable.size() == 0){
            ok = false;
        }
        for (Node node: visitable){
            if (!node.isVisitable()){
                ok = false;
            }
        }
        assertTrue(ok);
    }

    @Test
    public void testAddDeleteNode(){
        NodeManager manager = new NodeManager();
        manager.updateNodes();
        Node test = new Node("1",2,3,"1","building","type","lName","sName",true);
        manager.addNode(test);
        assertEquals(manager.getNode(test.getNodeID()).getNodeID(),test.getNodeID());
        manager.removeNode(test);
        assertNull(manager.getNode(test.getNodeID()));
    }

    @Test
    public void testUpdateNode(){
        NodeManager manager = new NodeManager();
        manager.updateNodes();
        Node test = new Node("1",2,3,"1","building","type","lName","sName",true);
        manager.addNode(test);
        Node testModified = new Node("1",6,6,"1","building","type","lName","sName",true);
        manager.updateNode(testModified);
        assertEquals(manager.getNode(test.getNodeID()).getXcoord(),testModified.getXcoord());
        manager.removeNode(testModified);
    }

    @Test
    public void testNearestNode(){
        NodeManager manager = new NodeManager();
        manager.updateNodes();
        Node test = new Node("1",50,0,"1","building","type","lName","sName",true);
        Node test2 = new Node("2",99, 99,"1","building","type","lName","sName",true);
        Node test3 = new Node("3",2,2,"1","building","type","lName","sName",true);
        Node test4 = new Node("4",5,5,"1","building","type","lName","sName",true);
        manager.addNode(test);
        manager.addNode(test2);
        manager.addNode(test3);
        manager.addNode(test4);
        assertEquals(test3.getNodeID(),manager.nearestNode(0, 0).getNodeID());
        manager.removeNode(test);
        manager.removeNode(test2);
        manager.removeNode(test3);
        manager.removeNode(test4);

    }

    @Test
    public void testNearestLoc(){
        NodeManager manager = new NodeManager();
        manager.updateNodes();
        Node test = new Node("1",1,1,"1","building","type","lName","sName",true);
        Node test2 = new Node("2",2, 2,"1","building","type","lName","sName",true);
        Node test3 = new Node("3",5,5,"1","building","bathroom","lName","sName",true);
        Node test4 = new Node("4",3,3,"1","building","bathroom","lName","sName",true);
        manager.addNode(test);
        manager.addNode(test2);
        manager.addNode(test3);
        manager.addNode(test4);
        assertEquals(manager.nearestLoc(0,0, "bathroom").getNodeID(), test4.getNodeID());
        assertNull(manager.nearestLoc(0, 0, "monkey"));
        manager.removeNode(test);
        manager.removeNode(test2);
        manager.removeNode(test3);
        manager.removeNode(test4);

    }
}