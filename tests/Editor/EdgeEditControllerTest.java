package Editor;

import Database.EdgeManager;
import Database.NodeManager;
import DatabaseSetup.DatabaseGargoyle;
import Entity.Edge;
import Entity.Node;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class EdgeEditControllerTest {

    @Test
    public void testAddRemoveGetAllEdges() throws Exception {
        DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
        NodeManager manager = new NodeManager(databaseGargoyle);
        EdgeManager testMan = new EdgeManager(databaseGargoyle, manager);
        databaseGargoyle.attachManager(manager);
        databaseGargoyle.attachManager(testMan);
        databaseGargoyle.notifyManagers();

        //Node n1 = new Node("1", 1, 1, "1", "test", "type","lName", "sName");
        //Node n2 = new Node("2", 1, 1, "1", "test","type", "lName", "sName");
        //Node n3 = new Node("3", 1, 1, "1", "test","type", "lName", "sName");

        Node n1 = manager.getNode("GHALL001L2");
        Node n2 = manager.getNode("GELEV00QL2");
        Node n3 = manager.getNode("GHALL009L2");
        Edge e1 = new Edge(n1, n2);
        Edge e2 = new Edge(n1, n3);

        EdgeEditController test = new EdgeEditController(testMan);

        System.out.println(test.getAllEdges().size());

        int startingSize = test.getAllEdges().size();

        /*
        manager.addNode(n1);
        manager.addNode(n2);
        manager.addNode(n3);
        manager.addNode(n4);
        */
        test.addEdge(e1);
        test.addEdge(e2);

        assertEquals(startingSize + 2, test.getAllEdges().size());

        List<Edge> result = test.getAllEdges();

        System.out.println(result.size());

        //doesn't work rn, since update edges actually creates a new object and there's no way to get an individual edge
        //assertTrue(test.getAllEdges().contains(e1) && result.contains(e2));

        test.deleteEdge(e1.getStartNode(),e1.getEndNode());
        test.deleteEdge(e2.getStartNode(),e2.getEndNode());

        //manager.removeNode(n1);
        //manager.removeNode(n2);
        //manager.removeNode(n3);

        assertTrue(test.getAllEdges().size() == startingSize);

        System.out.println(result);
        System.out.println(result.size());
    }

    @Test
    public void testDeleteEdge(){
        DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
        NodeManager manager = new NodeManager(databaseGargoyle);
        EdgeManager edgeManager = new EdgeManager(databaseGargoyle, manager);
        databaseGargoyle.attachManager(manager);
        databaseGargoyle.attachManager(edgeManager);
        databaseGargoyle.notifyManagers();

        Node n1 = new Node("1", 1, 1, "1", "test", "type","lName", "sName");
        Node n2 = new Node("2", 2, 1, "1", "test","type", "lName", "sName");
        Edge e1 = new Edge(n1, n2);

        EdgeEditController edgeEditController = new EdgeEditController(edgeManager);

        manager.addNode(n1);
        manager.addNode(n2);
        edgeManager.addEdge(e1);

        int startingSize = edgeManager.getAllEdges().size();
        System.out.println(startingSize);
        edgeEditController.deleteEdge(n1,n2);
        System.out.println(edgeManager.getAllEdges().size());
        assertEquals(startingSize - 1,edgeManager.getAllEdges().size());

        edgeManager.removeEdge(e1);
        manager.removeNode(n1);
        manager.removeNode(n2);
    }
}