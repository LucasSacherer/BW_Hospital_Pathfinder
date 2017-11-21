package Entity;

//Is this the right import for image?
import javafx.scene.image.Image;

public class ImageProxy {
    private final String path;
    private Image image;
    private final String floor;

    public ImageProxy(String path, String floor){
        this.path = path;
        this.floor = floor;
        image = null;
    }

    public Image getImage() {
        if (image == null){
            image = new Image(getClass().getResource(path).toString());
        }
        return  image;
    }

    private String getFloor() {
        return floor;
    }
}
