package Pathfinding;

import Database.EdgeManager;
import Database.NodeManager;
import Entity.Node;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

public class PathFindingFacadeTest {

    @Test
    public void testFacade() throws Exception{
        PathFindingFacade pFF = new PathFindingFacade();
        NodeManager nodeM = new NodeManager();
        EdgeManager edgeM = new EdgeManager(nodeM);
        Astar astar = new Astar(edgeM);
        pFF.setPathfinder(astar);
        nodeM.updateNodes();
        edgeM.updateEdges();
        Node n1 = nodeM.getNode("GHALL01601");
        Node n2 = nodeM.getNode("GHALL01501");
        List<Node> answer = pFF.getPath(n1,n2);
        ArrayList<Node> expected;
        System.out.println(answer.get(0).getNodeID());
        System.out.println(answer.get(1).getNodeID());
    }

    @Test
    public void realNodesSameNode() throws Exception{
        PathFindingFacade pFF = new PathFindingFacade();
        NodeManager nodeM = new NodeManager();
        EdgeManager edgeM = new EdgeManager(nodeM);
        Astar astar = new Astar(edgeM);
        pFF.setPathfinder(astar);
        nodeM.updateNodes();
        edgeM.updateEdges();
        Node n1 = nodeM.getNode("GHALL01601");
        Node n2 = nodeM.getNode("GHALL01601");
        List<Node> answer = pFF.getPath(n1,n2);
        List<Node> expected = new ArrayList<>();
        System.out.println(answer);

    }

    @Test
    public void realNodesAcrossFloor1() throws Exception{
        PathFindingFacade pFF = new PathFindingFacade();
        NodeManager nodeM = new NodeManager();
        EdgeManager edgeM = new EdgeManager(nodeM);
        Astar astar = new Astar(edgeM);
        pFF.setPathfinder(astar);
        nodeM.updateNodes();
        edgeM.updateEdges();
        Node n1 = nodeM.getNode("GHALL01601");
        Node n2 = nodeM.getNode("GELEV00N01");
        Astar star = new Astar(edgeM);
        List<Node> answer = pFF.getPath(n1,n2);
        ArrayList<Node> expected;
        //System.out.println(answer);
        ArrayList<String> ansID = new ArrayList<>();
        for(int i = 0; i < answer.size(); i++){
            ansID.add(answer.get(i).getNodeID());
        }
        System.out.println(ansID);
    }

    @Test
    public void realNodesAcrossFloor2() throws Exception{
        PathFindingFacade pFF = new PathFindingFacade();
        NodeManager nodeM = new NodeManager();
        EdgeManager edgeM = new EdgeManager(nodeM);
        Astar astar = new Astar(edgeM);
        pFF.setPathfinder(astar);
        nodeM.updateNodes();
        edgeM.updateEdges();
        Node n1 = nodeM.getNode("GHALL01602");
        Node n2 = nodeM.getNode("GHALL01002");
        Astar star = new Astar(edgeM);
        List<Node> answer = pFF.getPath(n1,n2);
        ArrayList<Node> expected;
        //System.out.println(answer);
        ArrayList<String> ansID = new ArrayList<>();
        for(int i = 0; i < answer.size(); i++){
            ansID.add(answer.get(i).getNodeID());
        }
        System.out.println(ansID);
    }

    @Test
    public void realNodesBacktoBack() throws Exception{
        PathFindingFacade pFF = new PathFindingFacade();
        NodeManager nodeM = new NodeManager();
        EdgeManager edgeM = new EdgeManager(nodeM);
        Astar astar = new Astar(edgeM);
        pFF.setPathfinder(astar);
        nodeM.updateNodes();
        edgeM.updateEdges();
        Node n1 = nodeM.getNode("GHALL01602");
        Node n2 = nodeM.getNode("GHALL01002");
        Astar star = new Astar(edgeM);
        List<Node> answer = pFF.getPath(n1,n2);
        List<Node> expected;
        //System.out.println(answer);
        ArrayList<String> ansID = new ArrayList<>();
        for(int i = 0; i < answer.size(); i++){
            ansID.add(answer.get(i).getNodeID());
        }
        System.out.println(ansID);

        n1 = nodeM.getNode("GHALL01601");
        n2 = nodeM.getNode("GELEV00N01");
        star = new Astar(edgeM);
        answer = pFF.getPath(n1,n2);
        ansID.clear();
        for(int i = 0; i < answer.size(); i++){
            ansID.add(answer.get(i).getNodeID());
        }
        System.out.println(ansID);
    }

    @Test
    public void realNodesbetweenElevators12() throws Exception{
        PathFindingFacade pFF = new PathFindingFacade();
        NodeManager nodeM = new NodeManager();
        EdgeManager edgeM = new EdgeManager(nodeM);
        Astar astar = new Astar(edgeM);
        pFF.setPathfinder(astar);
        nodeM.updateNodes();
        edgeM.updateEdges();
        Node n1 = nodeM.getNode("GELEV00N01");
        Node n2 = nodeM.getNode("GELEV00N02");
        Astar star = new Astar(edgeM);
        List<Node> answer = pFF.getPath(n1,n2);
        List<Node> expected;
        //System.out.println(answer);
        ArrayList<String> ansID = new ArrayList<>();
        for(int i = 0; i < answer.size(); i++){
            ansID.add(answer.get(i).getNodeID());
        }
        System.out.println(ansID);
    }

    @Test
    public void realNodesbetweenFloors12() throws Exception{
        PathFindingFacade pFF = new PathFindingFacade();
        NodeManager nodeM = new NodeManager();
        EdgeManager edgeM = new EdgeManager(nodeM);
        Astar astar = new Astar(edgeM);
        pFF.setPathfinder(astar);
        nodeM.updateNodes();
        edgeM.updateEdges();
        Node n1 = nodeM.getNode("GHALL02401");
        Node n2 = nodeM.getNode("GELEV00N02");
        Astar star = new Astar(edgeM);
        List<Node> answer = pFF.getPath(n1,n2);
        List<Node> expected;
        //System.out.println(answer);
        List<String> ansID = new ArrayList<>();
        for(int i = 0; i < answer.size(); i++){
            ansID.add(answer.get(i).getNodeID());
        }
        System.out.println(ansID);
    }

    @Test
    //tests that the bumps are gone and it really does take the most direct path
    public void realNodesFloors2() throws Exception{
        PathFindingFacade pFF = new PathFindingFacade();
        NodeManager nodeM = new NodeManager();
        EdgeManager edgeM = new EdgeManager(nodeM);
        Astar astar = new Astar(edgeM);
        pFF.setPathfinder(astar);
        nodeM.updateNodes();
        edgeM.updateEdges();
        Node n1 = nodeM.getNode("GHALL01002");
        Node n2 = nodeM.getNode("GELEV00N02");
        Astar star = new Astar(edgeM);
        List<Node> answer = pFF.getPath(n1,n2);
        List<String> expected = new ArrayList<>();
        expected.add("GELEV00N02");
        expected.add("GDEPT02402");
        expected.add("GHALL01702");
        expected.add("GHALL01602");
        expected.add("GHALL01402");
        expected.add("GHALL01202");
        expected.add("GHALL01002");
        //System.out.println(answer);
        ArrayList<String> ansID = new ArrayList<>();
        for(int i = 0; i < answer.size(); i++){
            ansID.add(answer.get(i).getNodeID());
        }
        System.out.println(ansID);
        assertEquals(expected,ansID);
    }

    @Test
    public void realNodesbetweenFloors13() throws Exception{
        PathFindingFacade pFF = new PathFindingFacade();
        NodeManager nodeM = new NodeManager();
        EdgeManager edgeM = new EdgeManager(nodeM);
        Astar astar = new Astar(edgeM);
        pFF.setPathfinder(astar);
        nodeM.updateNodes();
        edgeM.updateEdges();
        Node n1 = nodeM.getNode("GHALL02401");
        Node n2 = nodeM.getNode("GSERV01603");
        Astar star = new Astar(edgeM);
        List<Node> answer = pFF.getPath(n1,n2);
        List<String> expected = new ArrayList<>();
        expected.add("GSERV01603");
        expected.add("GDEPT01403");
        expected.add("GELEV00N03");
        expected.add("GELEV00N01");
        expected.add("GHALL02401");
        ArrayList<String> ansID = new ArrayList<>();
        for(int i = 0; i < answer.size(); i++){
            ansID.add(answer.get(i).getNodeID());
        }
        System.out.println(ansID);
        assertEquals(expected,ansID);
    }

}