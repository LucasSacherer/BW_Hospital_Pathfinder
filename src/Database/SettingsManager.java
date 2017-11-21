package Database;

import Editor.NodeEditController;
import Entity.Node;

import java.util.HashMap;

public class SettingsManager {

    private Node node;
    private HashMap<String, String> settings;
    private NodeEditController nodeEditControler;

    public String getSetting(String string){
        return settings.get(string);
    }

    public void setSetting(String setting){
    //can this take just a string? What's the second value then? Is this the key or the value?
        settings.put(node.getNodeID(), setting);
    }
}
