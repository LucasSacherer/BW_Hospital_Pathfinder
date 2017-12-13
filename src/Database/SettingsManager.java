package Database;

import java.util.HashMap;

public class SettingsManager {

    private HashMap<String, String> settings;


    private SettingsManager(){
        settings = new HashMap<>();

        // ***** Default Settings ***** //
        settings.put("Default Node","GHALL03802");
        settings.put("Distance Scale", "1.76");
        settings.put("Memento Delay","0.5");
    }

    public void setSetting(String setting, String nodeID){
        if (settings.keySet().contains(setting)){
            settings.put(setting,nodeID);
        }
    }

    public String getSetting(String string){
        return settings.get(string);
    }

    private static class SingletonHelper{
        private static final SettingsManager INSTANCE = new SettingsManager();
    }

    public static SettingsManager getInstance(){
        return SingletonHelper.INSTANCE;
    }
}
