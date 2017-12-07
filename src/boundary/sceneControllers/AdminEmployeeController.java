package boundary.sceneControllers;

import Database.UserManager;
import Entity.Request;
import Entity.User;
import Request.GenericRequestController;
import Entity.ErrorController;
import com.jfoenix.controls.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AdminEmployeeController {
    private UserManager userManager;
    private GenericRequestController genericRequestController;
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
    public AdminEmployeeController(UserManager u, GenericRequestController grc, JFXListView employeeList, JFXTextField userID, JFXTextField userName,
                                   JFXPasswordField password, JFXComboBox department, JFXToggleButton adminToggle) {
        this.userManager = u;
        this.genericRequestController = grc;
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
            errorController.showError("Please complete all employee information fields.");
        }
        else {
            if(departmentMenu.getSelectionModel().getSelectedItem() == null){
                errorController.showError("Please select an employee department.");
                return;
            }
            User newUser = new User(userID.getText(), userName.getText(), password.getText(), adminToggle.isSelected(),
                    departmentMenu.getSelectionModel().getSelectedItem().toString());
            userManager.addUser(newUser);
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
            errorController.showError("Please complete all employee information fields.");
        }
        else {
            isAdmin = adminToggle.isSelected();
            User modUser = new User(userID.getText(), userName.getText(), password.getText(), isAdmin,
                    departmentMenu.getSelectionModel().getSelectedItem().toString());
            userManager.modifyUser(modUser);
            employeeList.setItems(userManager.getUsers());
        }
    }

    public void deleteEmployeeAE(){
        if(selectedUser == null){
            errorController.showError("Please select a user to remove.");
            return;
        }
        for (Request req: genericRequestController.getAllRequestsByUser(selectedUser)){
            genericRequestController.deleteRequest(req);
        }
        userManager.removeUser(selectedUser);
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
