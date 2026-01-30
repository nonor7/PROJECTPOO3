

package ui;

import java.awt.Image;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import model.Building;

public class BuildingTextureManager {
    private static final Map<String, Image> textures = new HashMap<>();

    static {
        try {
            textures.put("commandcenter", ImageIO.read(BuildingTextureManager.class.getResource("/resources/buildings/CommandCenter.png")));
            textures.put("trainingcamp", ImageIO.read(BuildingTextureManager.class.getResource("/resources/buildings/TrainingCamp.png")));
        } catch (Exception e) {
            System.err.println("Error loading building textures: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static Image get(Building b) {
        String key = b.getClass().getSimpleName().toLowerCase(); // "commandcenter"
        return textures.get(key);
    }
}


