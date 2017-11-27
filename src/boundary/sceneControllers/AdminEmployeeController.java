package boundary.sceneControllers;

import Database.UserManager;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AdminEmployeeController {
    private UserManager userManager;
    private JFXListView employeeList;
    private JFXTextField userID, userName, password;
    private JFXComboBox departmentMenu;
    private ObservableList departmentList;

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
        employeeList.setItems(userManager.getUsers());
        departmentList = FXCollections.observableArrayList("Shapiro", "Non-Shapiro"); //TODO what are the departments?
        departmentMenu.setItems(departmentList);
        userManager.updateUsers();
    }

    public void addEmployeeAE(){}
    public void cancelEmployeeAE(){}
    public void editEmployeeAE(){}
    public void deleteEmployeeAE(){}
}
