// src/model/Player.java
/*package model;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private final String name;
    private final Faction faction;
    private final ResourceBag resources;
    private final List<Unit> units = new ArrayList<>();
    private final List<Building> buildings = new ArrayList<>();

    public Player(String name, Faction faction, ResourceBag resources) {
        this.name = name; this.faction = faction; this.resources = resources;
    }
    public String name() { return name; }
    public Faction faction() { return faction; }
    public ResourceBag resources() { return resources; }
    public List<Unit> units() { return units; }
    public List<Building> buildings() { return buildings; }



    public void placeStartingBase(MapGrid map) {
        int startX = faction == Faction.RED ? 0 : map.width() - 1;
        for (int y=0; y<map.height(); y++) {
            Tile t = map.get(startX, y);
            if (t != null && t.getBuilding() == null && t.getType().isAccessible()) {
                CommandCenter cc = new CommandCenter();
                cc.place(t);
                buildings.add(cc);
                break;
            }
        }
        // Add a starter producer
        for (int y=0; y<map.height(); y++) {
            Tile t = map.get(Math.max(0, Math.min(map.width()-1, startX + (faction == Faction.RED ? 1 : -1))), y);
            if (t != null && t.getBuilding() == null && t.getType().isAccessible()) {
                Producer mine = new Producer("stone", 5, new ResourceBag.Cost(50, 20, 0, 0));
                mine.place(t);
                buildings.add(mine);
                break;
            }
        }
    }

    public boolean buildTrainingCamp(Tile t) {
        TrainingCamp camp = new TrainingCamp();
        if (!t.isFreeAndAccessible() || !resources.canAfford(camp.cost())) return false;
        resources.pay(camp.cost());
        camp.place(t);
        buildings.add(camp);
        return true;
    }


    
    

    public boolean trainUnit(String type, Tile t) {
        if (t.getUnit() != null || !t.getType().isAccessible()) return false;
        Unit u = switch (type.toLowerCase()) {
            case "soldier" -> new Soldier(faction);
            case "archer"  -> new Archer(faction);
            case "cavalry" -> new Cavalry(faction);
            default -> null;
        };
        if (u == null) return false;
        if (!resources.canAfford(u.cost())) return false;
        resources.pay(u.cost());
        u.place(t);
        units.add(u);
        return true;
    }

    public void onTurnStart() {
        // reset movement
        for (Unit u : units) {
            if (u instanceof Soldier) u.resetMove(2);
            else if (u instanceof Archer) u.resetMove(2);
            else if (u instanceof Cavalry) u.resetMove(3);
        }
        // building production
        for (Building b : buildings) b.onTurn(this);
    }
 
       /*public Object base() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'base'");*/
    /*     public Building base() {
    for (Building b : buildings) {
        if (b instanceof CommandCenter) {
            return b;
        }
    }
    return null;
    }

    




}*/

// src/model/Player.java
package model;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private final String name;
    private final Faction faction;
    private final ResourceBag resources;
    private final List<Unit> units = new ArrayList<>();
    private final List<Building> buildings = new ArrayList<>();

    public Player(String name, Faction faction, ResourceBag resources) {
        this.name = name; this.faction = faction; this.resources = resources;
    }
    public String name() { return name; }
    public Faction faction() { return faction; }
    public ResourceBag resources() { return resources; }
    public List<Unit> units() { return units; }
    public List<Building> buildings() { return buildings; }



    public void placeStartingBase(MapGrid map) {
        int startX = faction == Faction.RED ? 0 : map.width() - 1;
        
        // Place Command Center
        for (int y=0; y<map.height(); y++) {
            Tile t = map.get(startX, y);
            if (t != null && t.getBuilding() == null && t.getType().isAccessible()) {
                CommandCenter cc = new CommandCenter();
                cc.place(t);
                buildings.add(cc);
                break;
            }
        }
        
        // Add a starter producer
        for (int y=0; y<map.height(); y++) {
            Tile t = map.get(Math.max(0, Math.min(map.width()-1, startX + (faction == Faction.RED ? 1 : -1))), y);
            if (t != null && t.getBuilding() == null && t.getType().isAccessible()) {
                Producer mine = new Producer("stone", 5, new ResourceBag.Cost(50, 20, 0, 0));
                mine.place(t);
                buildings.add(mine);
                break;
            }
        }
        
        // ✅ ADD STARTING UNITS - This is what was missing!
        int unitX = faction == Faction.RED ? 2 : map.width() - 3;
        int unitsPlaced = 0;
        
        // Try to place 2 starting soldiers
        for (int y = 0; y < map.height() && unitsPlaced < 2; y++) {
            Tile t = map.get(unitX, y);
            if (t != null && t.getUnit() == null && t.getType().isAccessible()) {
                Soldier soldier = new Soldier(faction);
                soldier.place(t);
                units.add(soldier);
                unitsPlaced++;
            }
        }
        
        // Try to place 1 starting archer
        unitX = faction == Faction.RED ? 3 : map.width() - 4;
        for (int y = 0; y < map.height() && unitsPlaced < 3; y++) {
            Tile t = map.get(unitX, y);
            if (t != null && t.getUnit() == null && t.getType().isAccessible()) {
                Archer archer = new Archer(faction);
                archer.place(t);
                units.add(archer);
                unitsPlaced++;
            }
        }
    }

    public boolean buildTrainingCamp(Tile t) {
        TrainingCamp camp = new TrainingCamp();
        if (!t.isFreeAndAccessible() || !resources.canAfford(camp.cost())) return false;
        resources.pay(camp.cost());
        camp.place(t);
        buildings.add(camp);
        return true;
    }


    
    

    public boolean trainUnit(String type, Tile t) {
        if (t.getUnit() != null || !t.getType().isAccessible()) return false;
        Unit u = switch (type.toLowerCase()) {
            case "soldier" -> new Soldier(faction);
            case "archer"  -> new Archer(faction);
            case "cavalry" -> new Cavalry(faction);
            default -> null;
        };
        if (u == null) return false;
        if (!resources.canAfford(u.cost())) return false;
        resources.pay(u.cost());
        u.place(t);
        units.add(u);
        return true;
    }

    public void onTurnStart() {
        // reset movement
        for (Unit u : units) {
            if (u instanceof Soldier) u.resetMove(2);
            else if (u instanceof Archer) u.resetMove(2);
            else if (u instanceof Cavalry) u.resetMove(3);
        }
        // building production
        for (Building b : buildings) b.onTurn(this);
    }
 
    public Building base() {
        for (Building b : buildings) {
            if (b instanceof CommandCenter) {
                return b;
            }
        }
        return null;
    }
}