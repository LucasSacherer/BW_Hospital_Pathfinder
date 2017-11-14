package controller;

import entity.Node;
import entity.Request;
import entity.RequestManager;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class RequestControllerTest {

    Node test1 = new Node("1",0,0,"1","building","type","lName","sName",true);
    Node test2 = new Node("2",1,1,"1","building","type","lName","sName",true);
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
    }

    @Test
    public void validateRequest() {
        RequestManager requestManager = new RequestManager();
        RequestController requestController = new RequestController(requestManager);
        requestManager.updateRequests();
        requestManager.addRequest(request1);
        requestManager.addRequest(request5);

        assertEquals(false, requestController.validateRequest(request2));
        assertEquals(false, requestController.validateRequest(request3));
        assertEquals(true, requestController.validateRequest(request4));
        assertEquals(false, requestController.validateRequest(request6));
        assertEquals(false, requestController.validateRequest(request7));



    }

    @Test
    public void getRequests() {
    }

    @Test
    public void deleteRequest() {
    }

}