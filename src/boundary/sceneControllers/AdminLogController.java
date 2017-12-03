package boundary.sceneControllers;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import Admin.CSVController;
import Database.AdminLogManager;
import Database.UserManager;
import DatabaseSetup.DatabaseGargoyle;
import Entity.AdminLog;
import Entity.FileSelector;
import Entity.User;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;

import java.io.IOException;
import java.sql.SQLException;

public class AdminLogController {
    final private FileSelector fileSelector = new FileSelector();
    private DatabaseGargoyle databaseGargoyle;
    CSVController csvController;
    private TreeTableView<AdminLog> adminLogs;
    private TreeTableColumn<AdminLog,String> dateLogged;
    private TreeTableColumn<AdminLog,String> adminLogged;
    private TreeTableColumn<AdminLog,String> logContent;
    private AdminLogManager adminLogManager;
    private UserManager userManager;
    private User user;
    TreeItem<AdminLog> logRoot = new TreeItem<>();


    public AdminLogController ( DatabaseGargoyle dbG, TreeTableView adminLogs, TreeTableColumn dateLogged,
                                 TreeTableColumn adminLogged, TreeTableColumn logContent, AdminLogManager adminLogManager,UserManager userManager){
        this.databaseGargoyle = dbG;
        this.adminLogs = adminLogs;
        this.dateLogged = dateLogged;
        this.adminLogged = adminLogged;
        this.logContent = logContent;
        this.adminLogManager = adminLogManager;
        csvController = new CSVController(databaseGargoyle);
    }

    public void initializeScene(User user ){
        this.user = user;
        adminLogManager.update();

        for (AdminLog log: adminLogManager.getAdminLogs()){
            logRoot.getChildren().add(new TreeItem<>(log));
        }
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
        adminLogs.setShowRoot(false);
    }

    public void clearLogButton(){
//        adminLogManager.getAdminLogs().clear();
        DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
        databaseGargoyle.createConnection();
        databaseGargoyle.executeUpdateOnDatabase("DELETE FROM ADMINLOG");
        databaseGargoyle.destroyConnection();
        adminLogManager.update();
        logRoot.getChildren().clear();
    }

    public void exportLogs() {
        String path = fileSelector.selectFile();
        try {
            csvController.saveAdminLogs(path);
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("CSV File Populated");
            alert.setHeaderText(null);
            alert.setContentText("The CSV file " + path + " has been populated with all Admin Logs!");
            alert.showAndWait();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

