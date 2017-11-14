package entity;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.List;
import java.util.ArrayList;
public class AstarTest {




    @Test
    public void sameNode() throws Exception{
        NodeManager nodeM = new NodeManager();
        EdgeManager edgeM = new EdgeManager(nodeM);

        Astar star = new Astar(edgeM);
        Node n1 = new Node("1",1,1,"1","Shapiro","type","Stairwell","STAI",true);
        ArrayList<Node> actual = new ArrayList<>();
        actual.add(n1);
        ArrayList<Node> answer = star.Astar(n1,n1);
        System.out.println(answer);
        assertEquals(actual,answer);


        nodeM.removeNode(n1);

    }


    @Test
    public void twoConnects() throws Exception{
        NodeManager nodeM = new NodeManager();
        EdgeManager edgeM = new EdgeManager(nodeM);

        Node n1 = new Node("1",1,1,"1","Shapiro","type","Stairwell","STAI",true);
        Node n2 = new Node("2",2,1,"1","Shapiro","type","Stairwell","STAI",true);
        Node n3 = new Node("3",2,2,"1","Shapiro","type","Stairwell","STAI",true);
        nodeM.addNode(n1);
        nodeM.addNode(n2);
        nodeM.addNode(n3);

        Edge e1 = new Edge(n1,n2);
        Edge e3 = new Edge(n1,n3);
        edgeM.addEdge(e1);
        edgeM.addEdge(e3);

        Astar star = new Astar(edgeM);
        ArrayList<String> actual = new ArrayList<>();
        actual.add(n3.getNodeID());
        actual.add(n1.getNodeID());

        ArrayList<Node> answer = star.Astar(n1,n3);
        ArrayList<String> ansID = new ArrayList<>();
        for(int i = 0; i < answer.size(); i++){
            ansID.add(answer.get(i).getNodeID());
        }
        System.out.println(answer);
        assertEquals(actual,ansID);

        edgeM.removeEdge(e1);
        edgeM.removeEdge(e3);
        nodeM.removeNode(n1);
        nodeM.removeNode(n2);
        nodeM.removeNode(n3);
    }

    @Test
    public void realNodes() throws Exception{
        NodeManager nodeM = new NodeManager();
        nodeM.updateNodes();
        EdgeManager edgeM = new EdgeManager(nodeM);
        edgeM.updateEdges();
        Node n1 = nodeM.getNode("GHALL001L2");
        Node n2 = nodeM.getNode("GHALL002L2");
        Astar star = new Astar(edgeM);
        ArrayList<Node> answer = star.Astar(n1,n2);
        System.out.println(answer);

    }
}