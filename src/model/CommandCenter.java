// src/model/CommandCenter.java
package model;

public class CommandCenter extends Building {
    public CommandCenter() {
        super("CommandCenter", new ResourceBag.Cost(100, 0, 50, 0));
    }
    @Override public void onTurn(Player owner) { /* optional bonuses */ }
}

