package Pathfinding;

import Database.EdgeManager;
import Database.NodeManager;
import DatabaseSetup.DatabaseGargoyle;
import Entity.Node;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class DijkstraTest {

    DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();

    @Test
    public void realNodesSameNode() throws Exception{
        PathFindingFacade pFF = new PathFindingFacade();
        NodeManager nodeM = new NodeManager(databaseGargoyle);
        EdgeManager edgeM = new EdgeManager(databaseGargoyle, nodeM);
        Dijkstra dijkstra = new Dijkstra(edgeM);
        pFF.setPathfinder(dijkstra);
        nodeM.update();
        edgeM.update();
        Node n1 = nodeM.getNode("GHALL01601");
        Node n2 = nodeM.getNode("GHALL01601");
        List<Node> answer = pFF.getPath(n1,n2);
        List<Node> expected = new ArrayList<>();
        System.out.println(answer);

    }
}