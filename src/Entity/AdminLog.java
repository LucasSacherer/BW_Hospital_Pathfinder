package Entity;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDateTime;

public class AdminLog extends RecursiveTreeObject<AdminLog> {
    private String user;
    private String action;
    private LocalDateTime time;

    public AdminLog(String user, String action, LocalDateTime time) {
        this.user = user;
        this.action = action;
        this.time = time;
    }
//    public AdminLog(LocalDateTime time, String admin ,String content) {
//        this.time = time;
//        this.user = new SimpleStringProperty(admin);
//        this.action = new SimpleStringProperty(content);
//
//    }

    public LocalDateTime getTime() {
        return time;
    }


    public String getUser() {
        return user;
    }


    public String getAction() {
        return action;
    }

}
