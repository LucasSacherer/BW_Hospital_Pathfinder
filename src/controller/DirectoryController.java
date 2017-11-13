package controller;

import entity.Node;
import entity.NodeManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DirectoryController {
    private NodeManager nm;

    public DirectoryController(NodeManager nm) {
        this.nm = nm;
    }

    /**
     * Gets all visitable nodes from the database and returns a directory of nodes categorized by nodetype
     * @return A categorized Directory (HashMap)
     */
    public HashMap<String, ArrayList<Node>> getDirectory(){
        //Get all visitable nodes from the NodeManager
        nm.updateNodes();
        List<Node> visitableNodes = nm.getVisitableNodes();

        //Return the categorized directory
        return formatNodeList(visitableNodes);
    }

    /**
     * Categorizes a list of nodes based on their nodeType
     * @param visitableNodes All nodes that are visitable (pulled from database in NodeManager)
     * @return The categorized directory
     */
    protected HashMap<String, ArrayList<Node>> formatNodeList(List<Node> visitableNodes) {
        //Initialize the final directory, and the lists that make up the directory
        HashMap<String, ArrayList<Node>> directory = new HashMap<>();
        ArrayList<Node> elev = new ArrayList<>();
        ArrayList<Node> rest = new ArrayList<>();
        ArrayList<Node> stai = new ArrayList<>();
        ArrayList<Node> dept = new ArrayList<>();
        ArrayList<Node> labs = new ArrayList<>();
        ArrayList<Node> info = new ArrayList<>();
        ArrayList<Node> conf = new ArrayList<>();
        ArrayList<Node> exit = new ArrayList<>();
        ArrayList<Node> retl = new ArrayList<>();
        ArrayList<Node> serv = new ArrayList<>();
        nm.updateNodes();

        //Go through all the visitable nodes and assign them to the correct list based on nodeType
        for (Node node : visitableNodes){
            switch (node.getNodeType()) {
                case "ELEV": elev.add(node); break;
                case "REST": rest.add(node); break;
                case "STAI": stai.add(node); break;
                case "DEPT": dept.add(node); break;
                case "LABS": labs.add(node); break;
                case "INFO": info.add(node); break;
                case "CONF": conf.add(node); break;
                case "EXIT": exit.add(node); break;
                case "RETL": retl.add(node); break;
                case "SERV": serv.add(node); break;
            }
        }

        //Combine the lists into the final directory and return it
        directory.put("Elevators", elev);
        directory.put("Restrooms", rest);
        directory.put("Stairs", stai);
        directory.put("Departments", dept);
        directory.put("Labs", labs);
        directory.put("Information Desks", info);
        directory.put("Conference Rooms", conf);
        directory.put("Exits/Entrances", exit);
        directory.put("Shops, Food, Phones", retl);
        directory.put("Non-Medical Services", serv);
        return directory;
    }

    /**
     * FOR TESTING ONLY- shortcut for testing a protected method
     * @param nodeList TESTING ONLY
     * @return TESTING ONLY
     */
    public HashMap<String, ArrayList<Node>> formatNodeListTester(List<Node> nodeList) {
        return formatNodeList(nodeList);
    }
}
