package boundary;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Log{

    SimpleIntegerProperty logIDProperty;
    SimpleStringProperty dateLoggedProperty;
    SimpleStringProperty adminLoggedProperty;
    SimpleStringProperty logContentProperty;


    public Log (Integer id, String Date, String admin, String content){
        this.logIDProperty = new SimpleIntegerProperty(id);
        this.dateLoggedProperty = new SimpleStringProperty(Date);
        this.adminLoggedProperty = new SimpleStringProperty(admin);
        this.logContentProperty = new SimpleStringProperty(content);

    }

    public SimpleIntegerProperty getLogIDProperty() {
        return logIDProperty;
    }


    public SimpleStringProperty getDateLoggedProperty() {
        return dateLoggedProperty;
    }


    public SimpleStringProperty getAdminLoggedProperty() {
        return adminLoggedProperty;
    }


    public SimpleStringProperty getLogContentProperty() {
        return logContentProperty;
    }
}
