// src/model/Tile.java
package model;

public class Tile {
    private final int x, y;
    private TileType type;
    private Building building;
    private Unit unit;

    public Tile(int x, int y, TileType type) {
        this.x = x; this.y = y; this.type = type;
    }
    public int x() { return x; }
    public int y() { return y; }
    public TileType getType() { return type; }
    public void setType(TileType type) { this.type = type; }

    public Building getBuilding() { return building; }
    public void setBuilding(Building b) { this.building = b; }

    public Unit getUnit() { return unit; }
    public void setUnit(Unit u) { this.unit = u; }

    public boolean isFreeAndAccessible() {
        return type.isAccessible() && unit == null && building == null;
    }
}

