package Entity;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;

public class ErrorController {

    public ErrorController(){
    }

    public void showError(String errorMessage){
        Dialog error = new Dialog();
        error.setHeaderText("Error");
        error.setResizable(true);
        error.setContentText(errorMessage);

        DialogPane errorDialogPane = error.getDialogPane();
        errorDialogPane.getStylesheets().add(
                getClass().getResource("/boundary/ErrorDialog.css").toExternalForm());
        errorDialogPane.getStyleClass().add("customError");
        errorDialogPane.getButtonTypes().add(ButtonType.CLOSE);
        //TODO: implement an "ok" button with type CANCEL_CLOSE
        error.show();
        System.out.print("showing error");
    }

}
