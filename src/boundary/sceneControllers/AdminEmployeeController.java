package boundary.sceneControllers;

import Database.UserManager;
import Entity.Request;
import Entity.User;
import Request.GenericRequestController;
import Entity.ErrorController;
import com.jfoenix.controls.*;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;

public class AdminEmployeeController {
    private UserManager userManager;
    private GenericRequestController genericRequestController;

    private JFXTreeTableView<User> employeeTable;
    private TreeTableColumn<User, String> userIDColumn;
    private TreeTableColumn<User, String> departmentColumn;
    private TreeTableColumn<User, String> usernameColumn;
    private TreeTableColumn<User, String> passwordColumn;
    private TreeTableColumn<User, String> adminStatusColumn;

    private JFXTextField userID;
    private JFXTextField userName;
    private JFXPasswordField password;
    private JFXComboBox departmentMenu;
    private ObservableList departmentList;
    private boolean isAdmin;
    private User selectedUser;
    private JFXToggleButton adminToggle;
    private TreeItem userRoot = new TreeItem();
    private ErrorController errorController = new ErrorController();
    public AdminEmployeeController(UserManager u, GenericRequestController grc, JFXTextField userID, JFXTextField userName,
                                   JFXPasswordField password, JFXComboBox department, JFXToggleButton adminToggle, JFXTreeTableView<User> employeeTable,
                                   TreeTableColumn<User, String> userIDColumn, TreeTableColumn<User, String> departmentColumn,
                                   TreeTableColumn<User, String> usernameColumn, TreeTableColumn<User, String> passwordColumn,TreeTableColumn adminStatusColumn) {
        this.userManager = u;
        this.genericRequestController = grc;

        this.userID = userID;
        this.userName = userName;
        this.password = password;
        this.departmentMenu = department;
        this.adminToggle = adminToggle;
        this.employeeTable = employeeTable;
        this.userIDColumn = userIDColumn;
        this.departmentColumn = departmentColumn;
        this.usernameColumn = usernameColumn;
        this.passwordColumn = passwordColumn;
        this.adminStatusColumn = adminStatusColumn;
    }

    public void initializeScene() {
        departmentList = FXCollections.observableArrayList("Food", "Interpreter","Janitorial");
        departmentMenu.setItems(departmentList);
        initializeAdminEmployeeTable();
    }
    private void initializeAdminEmployeeTable(){
        userRoot.getChildren().clear();
        userManager.update();

        for (Object user : userManager.getUsers()) {
            userRoot.getChildren().add(new TreeItem<>(user));

        }
        userIDColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<User, String> param) -> new ReadOnlyStringWrapper(param.getValue().getValue().getUserID()));
        departmentColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<User, String> param) -> new ReadOnlyStringWrapper(param.getValue().getValue().getDepartment()));
        usernameColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<User, String> param) -> new ReadOnlyStringWrapper(param.getValue().getValue().getUsername()));
        passwordColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<User, String> param) -> new ReadOnlyStringWrapper(param.getValue().getValue().getPassword()));
        adminStatusColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<User, String> param) -> new ReadOnlyStringWrapper(param.getValue().getValue().getAdminFlag().toString()));


        employeeTable.setRoot(userRoot);
        employeeTable.setShowRoot(false);
        employeeTable.setOnMouseClicked(event -> {
            if (event.getClickCount() > 0) {
                onEditMenu();
            }
        });

    }


    private void onEditMenu() {
        if (employeeTable.getSelectionModel().getSelectedItem() == null) {
            Alert error = new Alert(Alert.AlertType.ERROR, "No selected Item");
            error.show();
        } else {
            TreeItem<User> selectedUserItem = employeeTable.getSelectionModel().getSelectedItem();
            userID.setText(selectedUserItem.getValue().getUserID());
            userName.setText(selectedUserItem.getValue().getUsername());
            password.setText(selectedUserItem.getValue().getPassword());
            departmentMenu.getSelectionModel().select(selectedUserItem.getValue().getDepartment());
            adminToggle.setSelected(selectedUserItem.getValue().getAdminFlag());
        }
    }



    public void addEmployeeAE(){
        if(userID.getText().equals("")||userName.getText().equals("")|| password.getText().equals("")){
            errorController.showError("Please complete all employee information fields.");
        }
        else {
            if(departmentMenu.getSelectionModel().getSelectedItem() == null){
                errorController.showError("Please select an employee department.");
                return;
            }
            User newUser = new User(userID.getText(),userName.getText(),password.getText(),adminToggle.isSelected(),departmentMenu.getSelectionModel().getSelectedItem().toString());
            userManager.addUser(newUser);
//            userRoot.getChildren().add(new TreeItem<>(newUser));
            initializeAdminEmployeeTable();

        }

    }

    //resets scene
    public void cancelEmployeeAE(){
        resetScene();
    }

    public void editEmployeeAE(){
        if (employeeTable.getSelectionModel().getSelectedItem() == null){
            Alert error = new Alert(Alert.AlertType.ERROR,"No selected Item");
            error.show();
        }else {
            TreeItem<User> selectedUserItem = employeeTable.getSelectionModel().getSelectedItem();
            User modifiedUser = new User(userID.getText(),userName.getText(),password.getText(),adminToggle.isSelected(),departmentMenu.getSelectionModel().getSelectedItem().toString());
            userManager.modifyUser(modifiedUser);
//            userRoot.getChildren().remove(selectedUserItem);
//            userRoot.getChildren().add(new TreeItem<>(modifiedUser));
            initializeAdminEmployeeTable();
        }

    }

    public void deleteEmployeeAE(){
        if (employeeTable.getSelectionModel().getSelectedItem() == null){
            Alert error = new Alert(Alert.AlertType.ERROR,"No selected Item");
            error.show();
        }else {
            TreeItem<User> selectedUserItem = employeeTable.getSelectionModel().getSelectedItem();
            userManager.removeUser(selectedUserItem.getValue());
            userRoot.getChildren().remove(selectedUserItem);
            resetScene();

        }
    }

    private void resetScene(){
        userID.setText("");
        //reset departmentList
        userName.setText("");
        password.setText("");
        departmentList = FXCollections.observableArrayList();
        departmentMenu.setItems(departmentList);
        departmentList = FXCollections.observableArrayList("Food", "Interpreter","clean-up");
        departmentMenu.setItems(departmentList);
        adminToggle.setSelected(false);
    }

    public void toggleAdmin(){}
}
