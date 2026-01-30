// src/model/Building.java
package model;

public abstract class Building {
    protected final String name;
    protected final ResourceBag.Cost cost;
    protected Tile tile;

    protected Building(String name, ResourceBag.Cost cost) {
        this.name = name; this.cost = cost;
    }
    public String name() { return name; }
    public ResourceBag.Cost cost() { return cost; }
    public void place(Tile t) { this.tile = t; t.setBuilding(this); }
    public abstract void onTurn(Player owner);

    public String shortName() {
        return name.length() <= 2 ? name.toUpperCase() : name.substring(0,2).toUpperCase();
    }
    
}

