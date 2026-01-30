

// src/model/Cavalry.java
package model;

public class Cavalry extends Unit {
    public Cavalry(Faction f) {
        super("Cavalry", f, 40, 10, 5, 1, 3, new ResourceBag.Cost(60, 0, 10, 20));
    }
}

