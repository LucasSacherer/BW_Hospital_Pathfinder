package boundary;

import controller.MapDisplayController;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class FXMLController {
   final private MapDisplayController newMap = new MapDisplayController();

   @FXML
    private ScrollPane imageScroll;

   @FXML
   private void initialize(){
       Image groundFloor= newMap.getMap("G");
       imageScroll.setContent(new ImageView(groundFloor));
   }

   public FXMLController() {

   }
}
