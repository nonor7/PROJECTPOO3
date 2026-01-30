// src/ui/UnitTextureManager.java
package ui;

import java.awt.Image;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import model.Unit;

public class UnitTextureManager {
    private static final Map<String, Image> textures = new HashMap<>();

    static {
        try {
            textures.put("soldier", ImageIO.read(UnitTextureManager.class.getResource("/resources/units/soldier.png")));
            textures.put("archer", ImageIO.read(UnitTextureManager.class.getResource("/resources/units/archer.png")));
            textures.put("cavalry", ImageIO.read(UnitTextureManager.class.getResource("/resources/units/cavalry.png")));
        } catch (Exception e) {
            System.err.println("Error loading unit textures: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static Image get(Unit u) {
        String key = u.getClass().getSimpleName().toLowerCase(); // e.g. "soldier"
        return textures.get(key);
    }
}

