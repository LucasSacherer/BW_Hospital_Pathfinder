package entity;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ErrorScreen {

    public ErrorScreen(){
    }

    public void displayError(String errorMessage){
        Alert error = new Alert(AlertType.ERROR, errorMessage);
        error.show();
    }

}
