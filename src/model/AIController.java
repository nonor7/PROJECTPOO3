package model;

public class AIController {
    public static void playTurn(Player ai, Player human, MapGrid map) {
        // PHASE 1: Try to build training camps if we don't have many
        if (ai.buildings().size() < 3 && ai.resources().get("gold") >= 50) {
            tryBuildTrainingCamp(ai, map);
        }
        
        // PHASE 2: Train new units from training camps
        trainUnits(ai, map);
        
        // PHASE 3: Move and attack with existing units
        for (Unit u : ai.units()) {
            if (!u.alive() || u.getMove() <= 0) continue;

            boolean actionTaken = false;

            // Try to attack adjacent human unit
            outerLoop:
            for (int dx = -1; dx <= 1; dx++) {
                for (int dy = -1; dy <= 1; dy++) {
                    if (Math.abs(dx) + Math.abs(dy) != 1) continue; // Only cardinal directions
                    
                    Tile target = map.get(u.tile().x() + dx, u.tile().y() + dy);
                    if (target != null && target.getUnit() != null && target.getUnit().faction() == Faction.RED) {
                        u.attack(target.getUnit());
                        
                        // Counterattack if defender is still alive
                        if (target.getUnit() != null && target.getUnit().alive()) {
                            target.getUnit().attack(u);
                        }
                        
                        u.setMove(u.getMove() - 1);
                        actionTaken = true;
                        break outerLoop; // Break out of both loops
                    }
                }
            }

            // If no attack was made and unit still has moves, try to move toward nearest enemy
            if (!actionTaken && u.getMove() > 0 && u.alive()) {
                Tile nearest = findNearestEnemyTile(u, human, map);
                if (nearest != null) {
                    // Try to move toward the target using best available direction
                    Tile moveTarget = findBestMove(u, nearest, map);
                    
                    if (moveTarget != null && u.canMoveTo(moveTarget)) {
                        u.moveTo(moveTarget);
                        u.setMove(u.getMove() - 1);
                    }
                }
            }
        }
    }

    /**
     * AI tries to build a training camp near its base
     */
    private static void tryBuildTrainingCamp(Player ai, MapGrid map) {
        // Find AI's command center or any building
        Tile baseTile = null;
        for (Building b : ai.buildings()) {
            if (b instanceof CommandCenter) {
                baseTile = findBuildingTile(b, map);
                break;
            }
        }
        
        // If no command center, find any building
        if (baseTile == null && !ai.buildings().isEmpty()) {
            baseTile = findBuildingTile(ai.buildings().get(0), map);
        }
        
        if (baseTile == null) return;
        
        // Try to build near the base (within 3 tiles)
        for (int radius = 1; radius <= 3; radius++) {
            for (int dx = -radius; dx <= radius; dx++) {
                for (int dy = -radius; dy <= radius; dy++) {
                    if (Math.abs(dx) + Math.abs(dy) != radius) continue; // Manhattan distance
                    
                    Tile candidate = map.get(baseTile.x() + dx, baseTile.y() + dy);
                    if (candidate != null && candidate.isFreeAndAccessible()) {
                        boolean success = ai.buildTrainingCamp(candidate);
                        if (success) {
                            System.out.println("AI built training camp at (" + candidate.x() + "," + candidate.y() + ")");
                            return;
                        }
                    }
                }
            }
        }
    }

    /**
     * AI trains units from all available training camps
     */
    private static void trainUnits(Player ai, MapGrid map) {
        for (Building b : ai.buildings()) {
            if (b instanceof TrainingCamp) {
                Tile campTile = findBuildingTile(b, map);
                if (campTile == null) continue;
                
                // Find adjacent empty tile to spawn unit
                Tile spawnTile = findAdjacentSpawnTile(campTile, map);
                if (spawnTile == null) continue;
                
                // Decide what to train based on resources and strategy
                String unitType = chooseUnitToTrain(ai);
                if (unitType != null) {
                    boolean success = ai.trainUnit(unitType, spawnTile);
                    if (success) {
                        System.out.println("AI trained " + unitType + " at (" + spawnTile.x() + "," + spawnTile.y() + ")");
                        // Only train one unit per camp per turn
                        break;
                    }
                }
            }
        }
    }

    /**
     * Choose which unit type to train based on resources and strategy
     */
    private static String chooseUnitToTrain(Player ai) {
        int gold = ai.resources().get("gold");
        int wood = ai.resources().get("wood");
        
        // Count current unit types
        int soldiers = 0, archers = 0, cavalry = 0;
        for (Unit u : ai.units()) {
            if (u instanceof Soldier) soldiers++;
            else if (u instanceof Archer) archers++;
            else if (u instanceof Cavalry) cavalry++;
        }
        
        // Strategy: Balance army composition
        // Priority: Archers if we have few, then soldiers, then cavalry if wealthy
        
        if (gold >= 15 && wood >= 5 && archers < soldiers / 2) {
            return "archer"; // Maintain 1 archer per 2 soldiers
        } else if (gold >= 10 && wood >= 5) {
            return "soldier"; // Default cheap unit
        } else if (gold >= 30 && wood >= 10 && cavalry < 2) {
            return "cavalry"; // Expensive but powerful
        }
        
        return null; // Not enough resources
    }

    /**
     * Find an adjacent empty tile to spawn a unit
     */
    private static Tile findAdjacentSpawnTile(Tile center, MapGrid map) {
        int[][] directions = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}}; // up, down, left, right
        
        for (int[] dir : directions) {
            Tile candidate = map.get(center.x() + dir[0], center.y() + dir[1]);
            if (candidate != null && candidate.getUnit() == null && candidate.getType().isAccessible()) {
                return candidate;
            }
        }
        return null;
    }

    /**
     * Find the best tile to move to in order to get closer to the target.
     * Tries all 4 cardinal directions and picks the one that reduces distance most.
     */
    private static Tile findBestMove(Unit u, Tile target, MapGrid map) {
        int currentDist = Math.abs(u.tile().x() - target.x()) + Math.abs(u.tile().y() - target.y());
        
        Tile bestMove = null;
        int bestDist = currentDist;
        
        // Check all 4 cardinal directions
        int[][] directions = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}}; // up, down, left, right
        
        for (int[] dir : directions) {
            int newX = u.tile().x() + dir[0];
            int newY = u.tile().y() + dir[1];
            
            Tile candidate = map.get(newX, newY);
            
            // Check if this tile is valid to move to
            if (candidate != null && u.canMoveTo(candidate)) {
                int newDist = Math.abs(newX - target.x()) + Math.abs(newY - target.y());
                
                // If this move gets us closer, consider it
                if (newDist < bestDist) {
                    bestDist = newDist;
                    bestMove = candidate;
                }
            }
        }
        
        return bestMove;
    }

    /**
     * Find the tile where a building is located
     */
    private static Tile findBuildingTile(Building b, MapGrid map) {
        for (int y = 0; y < map.height(); y++) {
            for (int x = 0; x < map.width(); x++) {
                Tile t = map.get(x, y);
                if (t != null && t.getBuilding() == b) {
                    return t;
                }
            }
        }
        return null;
    }

    private static Tile findNearestEnemyTile(Unit u, Player human, MapGrid map) {
        Tile best = null;
        int minDist = Integer.MAX_VALUE;
        
        for (Unit hu : human.units()) {
            if (!hu.alive()) continue;
            
            int dist = Math.abs(u.tile().x() - hu.tile().x()) + Math.abs(u.tile().y() - hu.tile().y());
            if (dist < minDist) {
                minDist = dist;
                best = hu.tile();
            }
        }
        
        return best;
    }
}