// src/model/Unit.java
package model;

import combat.Damage;
import game.GameConfig;

public abstract class Unit {
    protected final String name;
    protected final Faction faction;
    protected int hp, atk, def, range, move;
    protected final ResourceBag.Cost cost;
    public Tile tile;


    public  int pixelX, pixelY;
    public   boolean isAnimating = false;


    protected Unit(String name, Faction faction, int hp, int atk, int def, int range, int move, ResourceBag.Cost cost) {
        this.name = name; this.faction = faction; this.hp = hp; this.atk = atk;
        this.def = def; this.range = range; this.move = move; this.cost = cost;
    }
    public String name() { return name; }
    public String shortName() { return name.substring(0, Math.min(2, name.length())).toUpperCase(); }
    
    public Faction faction() { return faction; }
    public int hp() { return hp; }
    public boolean alive() { return hp > 0; }
    public ResourceBag.Cost cost() { return cost; }




    public int getPixelX() { return pixelX; }
    public int getPixelY() { return pixelY; }
    public boolean isAnimating() { return isAnimating; }

    public void setPixelPosition(int px, int py) {
        this.pixelX = px;
        this.pixelY = py;
    }



    public Tile tile() { return tile; }



//public int move;

    public int getMove() { return move; }
    public void setMove(int m) { this.move = m; }


    public void place(Tile t) { 
        this.tile = t;
        t.setUnit(this);
        this.pixelX = t.x() * GameConfig.TILE_SIZE;
        this.pixelY = t.y() * GameConfig.TILE_SIZE;
    }

    public boolean canMoveTo(Tile dest) {
        if (move <= 0 || dest == null || !dest.getType().isAccessible() || dest.getUnit() != null) return false;
        int dx = Math.abs(dest.x() - tile.x());
        int dy = Math.abs(dest.y() - tile.y());
        return dx + dy == 1;
    }

    public void moveTo(Tile dest) {
        if (!canMoveTo(dest)) return;
        tile.setUnit(null);
        dest.setUnit(this);
        tile = dest;
        move--;
    }

    public void attack(Unit target) {
        if (target == null || !alive()) return;
        int dmg = Damage.compute(atk, target.def);
        target.receiveDamage(dmg);
    }

    protected void receiveDamage(int d) {
        hp -= Math.max(0, d);
        if (hp <= 0 && tile != null) tile.setUnit(null);
    }

    public void resetMove(int m) { this.move = m; }
}




