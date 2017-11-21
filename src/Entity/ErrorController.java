package Entity;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ErrorController {

    public ErrorController(){
    }

    public void showError(String errorMessage){
        Alert error = new Alert(AlertType.ERROR, errorMessage);
        error.show();
    }

}
