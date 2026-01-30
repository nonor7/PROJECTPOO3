// src/model/MapGrid.java
package model;

public class MapGrid {
    private final int width, height;
    private final Tile[][] tiles;

    public MapGrid(int w, int h) {
        this.width = w; this.height = h;
        this.tiles = new Tile[h][w];
    }
    public int width() { return width; }
    public int height() { return height; }

    public void set(int x, int y, Tile t) { tiles[y][x] = t; }
    public Tile get(int x, int y) {
        if (x < 0 || y < 0 || x >= width || y >= height) return null;
        return tiles[y][x];
    }
}

