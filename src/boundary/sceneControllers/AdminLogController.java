package boundary.sceneControllers;

import boundary.Log;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;

public class AdminLogController {

    private TreeTableView<String> adminLogs;

    private TreeTableColumn<Log,Number> logID;

    private TreeTableColumn<Log,String> dateLogged;

    private TreeTableColumn<Log,String> adminLogged;

    private TreeTableColumn<Log,String> logContent;


    public AdminLogController ( TreeTableView adminLogs, TreeTableColumn logID, TreeTableColumn dateLogged,
                                 TreeTableColumn adminLogged, TreeTableColumn logContent){
        this.logID = logID;
        this.dateLogged = dateLogged;
        this.adminLogged = adminLogged;
        this.logContent = logContent;
    }

    public void printLogButton(){}

    //TODO

    public void sendLogButton(){}

    //TODO

    public void clearLogButton(){}

    //TODO


}

