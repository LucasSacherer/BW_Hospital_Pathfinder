package Database;

import Editor.NodeEditController;
import Entity.Node;

import java.util.HashMap;

public class SettingsManager {

    private HashMap<String, String> settings;
    private NodeEditController nodeEditControler;

    public SettingsManager(HashMap<String, String> settings){
        this.settings = settings;
    }

    public String getSetting(String string){
        return settings.get(string);
    }

    public void setSetting(String setting, String nodeID){
        settings.put(setting, nodeID);
    }
}
