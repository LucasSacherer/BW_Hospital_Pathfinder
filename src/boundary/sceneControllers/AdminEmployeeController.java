package boundary.sceneControllers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;

public class AdminEmployeeController {
    private JFXListView employeeList;
    private JFXTextField userID, userName, password;
    private JFXComboBox department;

    public AdminEmployeeController(JFXListView employeeList, JFXTextField userID, JFXTextField userName,
                                   JFXTextField password, JFXComboBox department) {
        this.employeeList = employeeList;
        this.userID = userID;
        this.userName = userName;
        this.password = password;
        this.department = department;
    }

    public void initializeAdminEmployeeScene() {
        //department.setItems();
    }

    public void addEmployeeAE(){}
    public void cancelEmployeeAE(){}
    public void editEmployeeAE(){}
    public void deleteEmployeeAE(){}
}
