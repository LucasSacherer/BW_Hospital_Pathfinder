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
}
