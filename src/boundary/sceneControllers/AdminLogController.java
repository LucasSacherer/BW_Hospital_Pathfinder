package boundary.sceneControllers;

import Database.AdminLogManager;
import Database.UserManager;
import Entity.AdminLog;
import boundary.SceneSwitcher;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.List;

public class AdminLogController {

    private TreeTableView<AdminLog> adminLogs;

    private TreeTableColumn<AdminLog,String> dateLogged;

    private TreeTableColumn<AdminLog,String> adminLogged;

    private TreeTableColumn<AdminLog,String> logContent;

    private AdminLogManager adminLogManager;

//    TreeItem<Log> log1 = new TreeItem<>(new Log("11/27/2017","admin1","added Node"));
//    TreeItem<Log> log2 = new TreeItem<>(new Log("11/27/2017","admin1","logged in"));
//    TreeItem<Log> log3 = new TreeItem<>(new Log("11/27/2017","admin1","added Node"));
//    TreeItem<Log> log4 = new TreeItem<>(new Log("11/27/2017","admin1","added Node"));
//    TreeItem<Log> log5 = new TreeItem<>(new Log("11/27/2017","admin1","added Node"));


    public AdminLogController ( TreeTableView adminLogs, TreeTableColumn dateLogged,
                                 TreeTableColumn adminLogged, TreeTableColumn logContent, AdminLogManager adminLogManager){
        this.adminLogs = adminLogs;
        this.dateLogged = dateLogged;
        this.adminLogged = adminLogged;
        this.logContent = logContent;
        this.adminLogManager = adminLogManager;
        initializeScene();
    }

    private void initializeScene(){
        adminLogManager.updateAdminLogs();
        TreeItem<AdminLog> logRoot = new TreeItem<>();
        for (AdminLog log: adminLogManager.getAdminLogs()){
            logRoot.getChildren().add(new TreeItem<>(log));
        }



//        logs.add(log1);
//        logs.add(log2);
//        logs.add(log3);
//        logs.add(log4);
//        logs.add(log5);
//
//        logRoot.getChildren().setAll(log1, log2, log3, log4, log5);

        dateLogged.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<AdminLog, String> param) -> new ReadOnlyStringWrapper(param.getValue().getValue().getTime().toString()));
        adminLogged.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<AdminLog, String> param) -> new ReadOnlyStringWrapper(param.getValue().getValue().getUser().getUsername()));
        logContent.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<AdminLog, String> param) -> new ReadOnlyStringWrapper(param.getValue().getValue().getAction()));

//        logID.setCellFactory(new Callback<TreeTableColumn<Log, String>, TreeTableCell<Log, String>>() {
//            @Override
//            public TreeTableCell<Log, String> call(TreeTableColumn<Log, String> param) {
//                return new TextFieldTreeTableCell<>();
//            }
//        });
//        logID.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
//        logID.setOnEditCommit(new EventHandler<TreeTableColumn.CellEditEvent<Log, String>>() {
//            @Override
//            public void handle(TreeTableColumn.CellEditEvent<Log, String> event) {
//                TreeItem<Log> currentEditingLog = adminLogs.getTreeItem(event.getTreeTablePosition().getRow());
//                currentEditingLog.getValue().setLogIDProperty(event.getNewValue());
//            }
//        });

//        adminLogs.setEditable(true);
        adminLogs.setRoot(logRoot);
        adminLogs.setShowRoot(true);
    }
    public void printLogButton(){}

    //TODO

    public void sendLogButton(){}

    //TODO

    public void clearLogButton(){
//        logRoot.getChildren().clear();
    }

    //TODO


}

