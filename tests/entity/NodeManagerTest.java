package entity;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

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
}
