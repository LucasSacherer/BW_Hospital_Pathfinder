package boundary.sceneControllers;

import Database.UserManager;
import Entity.User;
import Entity.ErrorController;
import com.jfoenix.controls.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AdminEmployeeController {
    private UserManager userManager;
    private JFXListView employeeList;
    private JFXTextField userID;
    private JFXTextField userName;
    private JFXPasswordField password;
    private JFXComboBox departmentMenu;
    private ObservableList departmentList;
    private boolean isAdmin;
    private User selectedUser;
    private JFXToggleButton adminToggle;
    private ErrorController errorController = new ErrorController();
    public AdminEmployeeController(UserManager u, JFXListView employeeList, JFXTextField userID, JFXTextField userName,
                                   JFXPasswordField password, JFXComboBox department, JFXToggleButton adminToggle) {
        this.userManager = u;
        this.employeeList = employeeList;
        this.userID = userID;
        this.userName = userName;
        this.password = password;
        this.departmentMenu = department;
        this.adminToggle = adminToggle;
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
            userID.setText(selectedUser.getUserID());
            userName.setText(selectedUser.getUsername());
            password.setText(selectedUser.getPassword());
            adminToggle.setSelected(selectedUser.getAdminFlag());
            departmentMenu.getSelectionModel().select(selectedUser.getDepartment());
        });
    }

    public void addEmployeeAE(){
        if(userID.getText().equals("")||userName.getText().equals("")|| password.getText().equals("")){
            errorController.showError("Please fill out all the employee information");
        }
        else {
            //temp until UI if fixed
            User newUser = new User(userID.getText(), userName.getText(), password.getText(), adminToggle.isSelected(),
                    departmentMenu.getSelectionModel().getSelectedItem().toString());
            userManager.addUser(newUser);
            userManager.updateUsers();
            employeeList.setItems(userManager.getUsers());
            resetScene();
        }

    }

    //resets scene
    public void cancelEmployeeAE(){
        resetScene();
    }

    public void editEmployeeAE(){
        if(userID.getText().equals("")||userName.getText().equals("")|| password.getText().equals("")){
            errorController.showError("Please fill out all the employee information");
        }
        else {
            isAdmin = adminToggle.isSelected();
            User modUser = new User(userID.getText(), userName.getText(), password.getText(), isAdmin,
                    departmentMenu.getSelectionModel().getSelectedItem().toString());
            userManager.modifyUser(modUser);
            userManager.updateUsers();
            employeeList.setItems(userManager.getUsers());
        }
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
