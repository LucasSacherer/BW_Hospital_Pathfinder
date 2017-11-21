package Database;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ImageManagerTest {

    @Test
    public void testImageLoading(){
        ImageManager imageManager = new ImageManager();
        imageManager.getImage("G");
    }
}
