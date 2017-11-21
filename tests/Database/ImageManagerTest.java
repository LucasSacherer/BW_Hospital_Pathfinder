package Database;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ImageManagerTest {

    @Test
    public void testImageLoading(){
        ImageManager imageManager = new ImageManager();
        imageManager.getImage("G");
        imageManager.getImage("L1");
        imageManager.getImage("L2");
        imageManager.getImage("1");
        imageManager.getImage("2");
        imageManager.getImage("3");
    }
}
