// src/model/TrainingCamp.java
package model;

public class TrainingCamp extends Building {
    public TrainingCamp() {
        super("TrainingCamp", new ResourceBag.Cost(80, 40, 0, 0));
    }
    @Override public void onTurn(Player owner) { /* no passive production */ }
}

