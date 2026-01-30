// src/Main.java
import game.GameConfig;
import model.*;
import ui.GameFrame;

public class Main {
    public static void main(String[] args) {
        MapGrid map = MapGenerator.generate(GameConfig.MAP_WIDTH, GameConfig.MAP_HEIGHT);

        Player human = new Player("Human", Faction.RED, ResourceBag.starting());
        Player ai    = new Player("AI",    Faction.BLUE, ResourceBag.starting());

        human.placeStartingBase(map);
        ai.placeStartingBase(map);
        
        
        
        
        
        new GameFrame(map, human, ai).showUI();
        System.out.println("AI units: " + ai.units().size());
        

    
    }
}

