package Admin;

import Database.AdminLogManager;
import Entity.User;

public class AdminLogController {
    AdminLogManager adminLogManager;

    public void addLogEntry(String action, User user){
        adminLogManager.logAction(action, user);
    }
}
