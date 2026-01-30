// src/model/MapGenerator.java
package model;

import java.util.concurrent.ThreadLocalRandom;

public final class MapGenerator {
    private MapGenerator(){}

    public static MapGrid generate(int w, int h) {
        MapGrid map = new MapGrid(w, h);
        for (int y=0; y<h; y++) for (int x=0; x<w; x++) {
            map.set(x, y, new Tile(x, y, randomType()));
        }
        return map;
    }

    private static TileType randomType() {
        int r = ThreadLocalRandom.current().nextInt(100);
        if (r < 55) return TileType.GRASS;
        if (r < 80) return TileType.FOREST;
        if (r < 90) return TileType.MOUNTAIN;
        return TileType.WATER;
    }
}

