// src/model/Producer.java
package model;

public class Producer extends Building {
    private final String resourceKey;
    private final int perTurn;

    public Producer(String resourceKey, int perTurn, ResourceBag.Cost cost) {
        super(resourceKey + "Producer", cost);
        this.resourceKey = resourceKey;
        this.perTurn = perTurn;
    }

    @Override public void onTurn(Player owner) {
        owner.resources().add(resourceKey, perTurn);
    }
}

