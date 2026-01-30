// src/model/Archer.java
package model;

public class Archer extends Unit {
    public Archer(Faction f) {
        super("Archer", f, 24, 7, 3, 2, 2, new ResourceBag.Cost(35, 10, 0, 10));
    }
}

