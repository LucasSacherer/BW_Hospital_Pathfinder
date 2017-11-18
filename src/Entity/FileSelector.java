package Entity;
import javafx.stage.FileChooser;
import java.io.File;

public class FileSelector {
    final private FileChooser fileChooser = new FileChooser();
    public String selectFile(){
        String filename;
        File file = fileChooser.showOpenDialog(null);
        if(file == null){
            filename = "?";
        }else{
            filename = file.getAbsolutePath();
        }
        return filename;
    }
}