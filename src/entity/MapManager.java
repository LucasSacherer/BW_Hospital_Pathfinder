package entity;

import javafx.scene.image.Image;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;

public class MapManager {
    private HashMap<String, Image> maps = new HashMap<>();

    //creates master map hashmap
    public MapManager() {
        maps = new HashMap<>();

        try {
            File file1 = new File("src/boundary/images/Maps/00_thegroundfloor.png");
            Image imageG = new Image(new FileInputStream(file1));
            File file2 = new File("src/boundary/images/Maps/00_thelowerlevel1.png");
            Image imageL1 = new Image(new FileInputStream(file2));
            /*File file3 = new File("src/boundary/images/Maps/00_thelowerlevel2.png");
            Image imageL2 = new Image(new FileInputStream(file3));
            File file4 = new File("src/boundary/images/Maps/01_thefirstfloor.png");
            Image image1 = new Image(new FileInputStream(file4));
            File file5 = new File("src/boundary/images/Maps/02_thesecondfloor.png");
            Image image2 = new Image(new FileInputStream(file5));
            File file6 = new File("src/boundary/images/Maps/03_thethirdfloor.png");
            Image image3 = new Image(new FileInputStream(file6));*/
            maps.put("G", imageG);
            maps.put("L1", imageL1);
            //maps.put("L2", imageL2);
            //maps.put("1", image1);
            //maps.put("2", image2);
            //maps.put("3", image3);
        }   catch (FileNotFoundException ex) {
            System.out.println("Failed to load floor images!");
            ex.printStackTrace();
        }
    }

    public Image getMap(String floor){
        return maps.get(floor);
    }

    //adds floor to list of maps
    public void updateMaps (String floor, Image newMap){
        maps.put(floor, newMap);
    }

    //uploads new floor map to the database
    public void uploadMapToDB(String floor, Image mapPicture){}
}
