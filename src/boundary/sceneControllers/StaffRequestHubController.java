package boundary.sceneControllers;

import Database.NodeManager;
import Entity.Node;
import Entity.User;
import interpreter.IInterpretNode;
import interpreter.InterpreterFacade;
import interpreter.ServiceException;
import foodRequest.FoodRequest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class StaffRequestHubController {
    private User user;
    private NodeManager nodeManager;

    public StaffRequestHubController(NodeManager nodeManager) {
        this.nodeManager = nodeManager;
    }

    public void serviceHubtoAPITest() {
        InterpreterFacade intf = new InterpreterFacade();
        List<IInterpretNode> converted = new ArrayList<>();
        for (Node n : nodeManager.getAllNodes()){
            converted.add(new IWraper(n));
        }
        intf.setNodes(converted);
        try {
            intf.run(0, 0, 1200, 800, null, null, null);
        } catch (ServiceException se) {
            se.printStackTrace();
        }

//        where nc is your node controller, and getINodes() returns any collection of a list of nodes.
//        The first null is the css, the and the second and third are the node ids. The last null (originNodeID) is the node that is used for the Interpreter request!
    }

    public void serviceHubtoFoodAPI() {
        FoodRequest foodRequest = new FoodRequest();
        try{
            foodRequest.run(0,0,0,0,null,null,null);
        }catch (Exception e){
            System.out.println("Failed to run API");
            e.printStackTrace();
        }
    }

    public void setUser(User user) {
        this.user = user;
        System.out.println(user);
    }

    public User getUser() { return user; }

    private class IWraper implements IInterpretNode{
        private Node node;

        public IWraper(Node node){
            this.node = node;
        }

        @Override
        public String getNodeID() {
            return node.getNodeID();
        }

        @Override
        public String getLongName() {
            return node.getLongName();
        }

        @Override
        public String getShortName() {
            return node.getShortName();
        }

        @Override
        public String getNodeType() {
            return node.getNodeType();
        }
    }
}
