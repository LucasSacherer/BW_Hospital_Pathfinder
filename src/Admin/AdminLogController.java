package Admin;

import Database.AdminLogManager;
import Entity.AdminLog;

public class AdminLogController {
    AdminLogManager adminLogManager;

    public AdminLogController(AdminLogManager adminLogManager) {
        this.adminLogManager = adminLogManager;
    }

    public void addLogEntry(AdminLog logEntry){
        adminLogManager.addAdminLog(logEntry);
    }
}
