// src/model/TileType.java
package model;

public enum TileType {
    GRASS(true, 0),
    WATER(false, -1),
    MOUNTAIN(false, 1),
    FOREST(true, 1);

    private final boolean accessible;
    private final int modifier;

    TileType(boolean accessible, int modifier) {
        this.accessible = accessible;
        this.modifier = modifier;
    }
    public boolean isAccessible() { return accessible; }
    public int modifier() { return modifier; }
}

