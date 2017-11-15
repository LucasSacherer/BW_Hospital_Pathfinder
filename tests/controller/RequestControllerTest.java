package controller;

import entity.Node;
import entity.NodeManager;
import entity.Request;
import entity.RequestManager;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class RequestControllerTest {

    Node test1 = new Node("54",0,0,"1","building","type","lName","sName",true);
    Node test2 = new Node("96",1,1,"1","building","type","lName","sName",true);

    LocalDateTime rightNow = LocalDateTime.now();
    Request request1 = new Request("TypeA", "NameA", "Description", test1, rightNow);
    Request request2 = new Request("TypeA", "NameB", "Description", test1, rightNow);
    Request request3 = new Request("TypeB", "NameA", "Description", test2, rightNow);
    Request request4 = new Request("TypeA", "NameC", "Description", test2, rightNow);
    Request request5 = new Request("TypeC", "NameD", "Description", test2, rightNow);
    Request request6 = new Request("TypeC", "NameE", "Description", test2, rightNow);
    Request request7 = new Request("TypeD", "NameD", "Description", test2, rightNow);


    @Test
    public void addRequest(){
        NodeManager nodeManager = new NodeManager();
        RequestManager requestManager = new RequestManager(nodeManager);
        RequestController requestController = new RequestController(requestManager);
        nodeManager.updateNodes();
        requestManager.updateRequests();
        //nodeManager.addNode(test1);
        //nodeManager.addNode(test2);

        Request requestA = new Request("TypeA", "NameA", "Description",nodeManager.getNode("GREST01201"), rightNow);



            requestController.addRequest(requestA);
/*      System.out.println(requestManager.getRequests().get(0).getName());
        assertEquals("NameA", requestManager.getRequests().get(0).getName());
        requestController.addRequest(request4);
        assertEquals("NameC", requestManager.getRequests().get(1).getName());
        assertEquals(false, requestController.addRequest(request2));
*/
        //requestManager.deleteRequest(request1);
        //requestManager.deleteRequest(request2);
        //requestManager.deleteRequest(request4);
        //nodeManager.removeNode(test1);
        //nodeManager.removeNode(test2);

    }

    @Test
    public void validateRequest() {
        NodeManager nodeManager = new NodeManager();
        RequestManager requestManager = new RequestManager(nodeManager);
        RequestController requestController = new RequestController(requestManager);
        nodeManager.updateNodes();
        requestManager.updateRequests();
        nodeManager.addNode(test1);
        nodeManager.addNode(test2);

        requestManager.addRequest(request1);
        requestManager.addRequest(request5);

        assertEquals(false, requestController.validateRequest(request2));
        assertEquals(false, requestController.validateRequest(request3));
        assertEquals(true, requestController.validateRequest(request4));
        assertEquals(false, requestController.validateRequest(request6));
        assertEquals(false, requestController.validateRequest(request7));

        requestManager.deleteRequest(request1);
        requestManager.deleteRequest(request5);
        nodeManager.removeNode(test1);
        nodeManager.removeNode(test2);
    }

    @Test
    public void getRequests() {
        NodeManager nodeManager = new NodeManager();
        RequestManager requestManager = new RequestManager(nodeManager);
        RequestController requestController = new RequestController(requestManager);
        nodeManager.addNode(test1);
        nodeManager.updateNodes();
        requestManager.updateRequests();

        requestManager.addRequest(request1);
        requestManager.updateRequests();
        List<Request> testList = new ArrayList<Request>();
        testList.add(request1);
        assertEquals(testList.get(0).getName(), requestManager.getRequests().get(0).getName());


        requestManager.deleteRequest(request1);
        nodeManager.removeNode(test1);
    }

    @Test
    public void deleteRequest() {
        NodeManager nodeManager = new NodeManager();
        RequestManager requestManager = new RequestManager(nodeManager);
        RequestController requestController = new RequestController(requestManager);
        nodeManager.addNode(test1);
        nodeManager.updateNodes();
        requestManager.addRequest(request1);
        requestManager.updateRequests();

        requestController.deleteRequest(request1);

        assertEquals(true, requestManager.getRequests().isEmpty());

        nodeManager.removeNode(test1);
    }
}