package Entity;

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

        DialogPane dialogPane = error.getDialogPane();
        dialogPane.getStylesheets().add(
                getClass().getResource("/boundary/ErrorDialog.css").toExternalForm());
        dialogPane.getStyleClass().add("customError");
        //TODO: implement an "ok" button with type CANCEL_CLOSE
        error.show();
        System.out.print("showing error");
    }

}
