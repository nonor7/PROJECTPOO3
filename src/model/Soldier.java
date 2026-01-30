// src/model/Soldier.java
package model;

public class Soldier extends Unit {
    public Soldier(Faction f) {
        super("Soldier", f, 30, 8, 4, 1, 2, new ResourceBag.Cost(30, 0, 0, 10));
    }
}

