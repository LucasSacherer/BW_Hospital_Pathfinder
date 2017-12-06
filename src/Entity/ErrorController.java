package Entity;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.stage.StageStyle;

public class ErrorController {

    public ErrorController(){
    }

    public void showError(String errorMessage){
        Dialog error = new Dialog();
        error.setHeaderText("Error!");
        error.setResizable(false);
        error.setContentText(errorMessage);
        error.initStyle(StageStyle.UNDECORATED);

        DialogPane errorDialogPane = error.getDialogPane();
        errorDialogPane.getStylesheets().add(
                getClass().getResource("/boundary/ErrorDialog.css").toExternalForm());
        errorDialogPane.getStyleClass().add("customError");
        errorDialogPane.getButtonTypes().add(ButtonType.CLOSE);

        error.show();
    }

}
