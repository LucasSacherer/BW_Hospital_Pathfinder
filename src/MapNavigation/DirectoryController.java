package MapNavigation;

import Database.SettingsManager;
import Entity.Node;
import Database.NodeManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DirectoryController {
    private NodeManager nm;
    private SettingsManager settingsManager;

    public DirectoryController(NodeManager nm, SettingsManager sm) {
        this.nm = nm;
        settingsManager = sm;
    }

    /**
     * Gets all visitable nodes from the database and returns a directory of nodes categorized by nodetype
     * @return A categorized Directory (HashMap)
     */
     HashMap<String, ObservableList<Node>> getDirectory(){
        //Get all visitable nodes from the NodeManager
        nm.updateNodes();
        List<Node> visitableNodes = new ArrayList<Node>();

        for (int i = 0; nm.getAllNodes().size() > i; i++ ){
            if (!nm.getAllNodes().get(i).getNodeType().equals("HALL")){
                visitableNodes.add(nm.getAllNodes().get(i));
            }
        }

        //Return the categorized directory
        return formatNodeList(visitableNodes);
    }

    /**
     * Categorizes a list of nodes based on their nodeType
     * @param visitableNodes All nodes that are visitable (pulled from database in NodeManager)
     * @return The categorized directory
     */
    protected HashMap<String, ObservableList<Node>> formatNodeList(List<Node> visitableNodes) {
        //Initialize the final directory, and the lists that make up the directory
        HashMap<String, ObservableList<Node>> directory = new HashMap<>();
        ObservableList<Node> elev = FXCollections.observableArrayList();
        ObservableList<Node> rest = FXCollections.observableArrayList();
        ObservableList<Node> stai = FXCollections.observableArrayList();
        ObservableList<Node> dept = FXCollections.observableArrayList();
        ObservableList<Node> labs = FXCollections.observableArrayList();
        ObservableList<Node> info = FXCollections.observableArrayList();
        ObservableList<Node> conf = FXCollections.observableArrayList();
        ObservableList<Node> exit = FXCollections.observableArrayList();
        ObservableList<Node> retl = FXCollections.observableArrayList();
        ObservableList<Node> serv = FXCollections.observableArrayList();
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
    public HashMap<String, ObservableList<Node>> formatNodeListTester(List<Node> nodeList) {
        return formatNodeList(nodeList);
    }

    /**
     *
     * Returns the defaultNode which should be the Kiosk Location.
     * @return a Node that is the Default Node.
     */
    //TODO Need to throw exception if Default Node doesn't exist.
    Node getDefaultNode(){
        settingsManager.updateSettings();
        nm.updateNodes();
        String defaultNode = settingsManager.getSetting("Default Node");
        return nm.getNode(defaultNode);
    }
}
