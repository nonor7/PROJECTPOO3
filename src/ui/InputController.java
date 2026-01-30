// src/ui/InputController.java
package ui;

import model.*;

public class InputController {
    private final BoardPanel board;
    private final MapGrid map;
    private final Player human;
    private final Player ai;

    private Tile selected;

    public InputController(BoardPanel board, MapGrid map, Player human, Player ai) {
        this.board = board; this.map = map; this.human = human; this.ai = ai;
    }

    public Tile getSelected() { return selected; }

    public void leftClick(int x, int y) {
        Tile t = map.get(x, y);
        if (t == null) return;
        selected = t;
    }

    public void rightClick(int x, int y) {
        Tile target = map.get(x, y);
        if (selected == null || target == null) return;

        Unit u = selected.getUnit();
        if (u == null) { selected = target; return; }
        if (u.faction() != human.faction()) { selected = target; return; }

        // attack adjacent enemy
        if (target.getUnit() != null && target.getUnit().faction() != u.faction()) {
            if (adjacent(selected, target)) {
                u.attack(target.getUnit());
                // simple counterattack if defender alive
                Unit defender = target.getUnit();
                if (defender != null && defender.alive()) defender.attack(u);
            }
            return;
        }
        // move to adjacent accessible tile
        if (u.canMoveTo(target)) {
            board.animateMove(u, target);
            selected = target;
        }
    }

    public boolean buildCamp() {
        if (selected == null) return false;
        return human.buildTrainingCamp(selected);
    }

    public boolean train(String type) {
        if (selected == null) return false;
        boolean ok = human.trainUnit(type, selected);
        return ok;
    }

    private boolean adjacent(Tile a, Tile b) {
        int dx = Math.abs(a.x() - b.x());
        int dy = Math.abs(a.y() - b.y());
        return dx + dy == 1;
    }
}
