package boundary.sceneControllers;

import Database.UserManager;
import Entity.User;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AdminEmployeeController {
    private UserManager userManager;
    private JFXListView employeeList;
    private JFXTextField userID;
    private JFXTextField userName;
    private JFXTextField password;
    private JFXComboBox departmentMenu;
    private ObservableList departmentList;
    private boolean isAdmin;
    private User selectedUser;

    public AdminEmployeeController(UserManager u, JFXListView employeeList, JFXTextField userID, JFXTextField userName,
                                   JFXTextField password, JFXComboBox department) {
        this.userManager = u;
        this.employeeList = employeeList;
        this.userID = userID;
        this.userName = userName;
        this.password = password;
        this.departmentMenu = department;
    }

    public void initializeScene() {
        departmentList = FXCollections.observableArrayList("Food", "Interpreter","Janitorial");
        departmentMenu.setItems(departmentList);
        userManager.updateUsers();
        employeeList.setItems(userManager.getUsers());
        initializeAdminEmployeeListeners();
    }
    private void initializeAdminEmployeeListeners(){
        employeeList.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            selectedUser = (User) employeeList.getItems().get(newValue.intValue());
        });
    }


    public void addEmployeeAE(){
        //temp until UI if fixed
        isAdmin = false;
        User newUser = new User(userID.getText(), userName.getText(), password.getText(), isAdmin,
                departmentMenu.getSelectionModel().getSelectedItem().toString());
        userManager.addUser(newUser);
        userManager.updateUsers();
        employeeList.setItems(userManager.getUsers());
        resetScene();
    }

    //resets scene
    public void cancelEmployeeAE(){
        resetScene();
    }

    public void editEmployeeAE(){

        isAdmin = false;
        User modUser = new User(userID.getText(), userName.getText(), password.getText(), isAdmin,
                departmentMenu.getSelectionModel().getSelectedItem().toString());
        userManager.modifyUser(modUser);
        userManager.updateUsers();
        employeeList.setItems(userManager.getUsers());
    }

    public void deleteEmployeeAE(){
        userManager.removeUser(selectedUser);
        userManager.updateUsers();
        employeeList.setItems(userManager.getUsers());
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
    }

    public void toggleAdmin(){}
}
