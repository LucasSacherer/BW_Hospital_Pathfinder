package boundary.sceneControllers;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    private JFXTextField staffLogin, staffPassword;

    public void switchToAdmin() {

    }

    public void switchToStaff() {

    }

    public void setStaffLogin(JFXTextField staffLogin) {
        this.staffLogin = staffLogin;
    }

    public void setStaffPassword(JFXTextField staffPassword) {
        this.staffPassword = staffPassword;
    }
}
