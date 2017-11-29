package Pathfinding;

import Database.EdgeManager;
import Database.NodeManager;
import Entity.Node;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class BeamSearchTest {

    @Test
    public void sameNode() throws Exception{
        NodeManager nodeM = new NodeManager();
        EdgeManager edgeM = new EdgeManager(nodeM);

        BeamSearch b = new BeamSearch(edgeM);
        Node n1 = new Node("1",1,1,"1","Shapiro","type","Stairwell","STAI");
        ArrayList<Node> actual = new ArrayList<>();
        actual.add(n1);
        ArrayList<Node> answer = b.pathFind(n1,n1);
        System.out.println(answer);
        assertEquals(actual,answer);

        nodeM.removeNode(n1);

    }
}