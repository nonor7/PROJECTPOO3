// src/ui/TextureManager.java
package ui;

import model.TileType;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;

public class TextureManager {
    private static final Map<TileType, Image> textures = new EnumMap<>(TileType.class);

    static {
        try {
            textures.put(TileType.GRASS, ImageIO.read(TextureManager.class.getResource("/resources/textures/grass.png")));
            textures.put(TileType.FOREST, ImageIO.read(TextureManager.class.getResource("/resources/textures/forest.png")));
            textures.put(TileType.MOUNTAIN, ImageIO.read(TextureManager.class.getResource("/resources/textures/mountain.png")));
            textures.put(TileType.WATER, ImageIO.read(TextureManager.class.getResource("/resources/textures/water.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Image get(TileType type) {
        return textures.get(type);
    }
}

