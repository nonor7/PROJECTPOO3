// src/ui/BoardPanel.java
package ui;

import game.GameConfig;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import model.*;

public class BoardPanel extends JPanel {
    private final MapGrid map;
    private final Player human;
    private final Player ai;
    private final InputController input;

    public BoardPanel(MapGrid map, Player human, Player ai) {
        this.map = map; this.human = human; this.ai = ai;
        this.input = new InputController(this, map, human, ai);

        setPreferredSize(new Dimension(map.width() * GameConfig.TILE_SIZE,
                                       map.height() * GameConfig.TILE_SIZE));
        setBackground(Color.BLACK);

        addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                int x = e.getX() / GameConfig.TILE_SIZE;
                int y = e.getY() / GameConfig.TILE_SIZE;
                if (e.getButton() == MouseEvent.BUTTON1) input.leftClick(x, y);
                if (e.getButton() == MouseEvent.BUTTON3) input.rightClick(x, y);
                repaint();
            }
        });
    }





   public MapGrid map() {
    return map;
    }










    public InputController input() { return input; }

    @Override protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // draw tiles
        for (int y=0; y<map.height(); y++) for (int x=0; x<map.width(); x++) {
            Tile t = map.get(x, y);
            drawTile(g, t);
            drawBuilding(g, t);
            drawUnit(g, t);
        }
        // selection highlight
        Tile sel = input.getSelected();
        if (sel != null) {
            g.setColor(new Color(255, 255, 0, 120));
            g.fillRect(sel.x() * GameConfig.TILE_SIZE, sel.y() * GameConfig.TILE_SIZE,
                       GameConfig.TILE_SIZE, GameConfig.TILE_SIZE);
        }
        // grid lines
        g.setColor(new Color(0, 0, 0, 50));
        for (int y=0; y<map.height(); y++) for (int x=0; x<map.width(); x++) {
            g.drawRect(x * GameConfig.TILE_SIZE, y * GameConfig.TILE_SIZE,
                       GameConfig.TILE_SIZE, GameConfig.TILE_SIZE);
        }
    }

   /*  private void drawTile(Graphics g, Tile t) {
        Color c = switch (t.getType()) {
            case GRASS -> new Color(90, 150, 90);
            case FOREST -> new Color(40, 110, 60);
            case WATER -> new Color(60, 90, 150);
            case MOUNTAIN -> new Color(120, 120, 120);
        };
        g.setColor(c);
        g.fillRect(t.x() * GameConfig.TILE_SIZE, t.y() * GameConfig.TILE_SIZE,
                   GameConfig.TILE_SIZE, GameConfig.TILE_SIZE);
    }*/

private void drawTile(Graphics g, Tile t) {
    Image img = TextureManager.get(t.getType());
    if (img != null) {
        g.drawImage(img, t.x() * GameConfig.TILE_SIZE, t.y() * GameConfig.TILE_SIZE,
                    GameConfig.TILE_SIZE, GameConfig.TILE_SIZE, null);
    } else {
        g.setColor(Color.GRAY);
        g.fillRect(t.x() * GameConfig.TILE_SIZE, t.y() * GameConfig.TILE_SIZE,
                   GameConfig.TILE_SIZE, GameConfig.TILE_SIZE);
    }
}



   /*  private void drawBuilding(Graphics g, Tile t) {
        if (t.getBuilding() == null) return;
        int px = t.x() * GameConfig.TILE_SIZE, py = t.y() * GameConfig.TILE_SIZE;
        g.setColor(Color.ORANGE);
        g.fillRect(px + 6, py + 6, GameConfig.TILE_SIZE - 12, GameConfig.TILE_SIZE - 12);
        g.setColor(Color.BLACK);
        g.setFont(new Font("SansSerif", Font.PLAIN, 12));
        g.drawString(t.getBuilding().shortName(), px + 10, py + 20);
    }*/
   private void drawBuilding(Graphics g, Tile t) {
    Building b = t.getBuilding();
    if (b == null) return;

    int px = t.x() * GameConfig.TILE_SIZE;
    int py = t.y() * GameConfig.TILE_SIZE;

    Image img = BuildingTextureManager.get(b);
    if (img != null) {
        g.drawImage(img, px, py, GameConfig.TILE_SIZE, GameConfig.TILE_SIZE, null);
    } else {
        // fallback: orange square
        g.setColor(Color.ORANGE);
        g.fillRect(px + 6, py + 6, GameConfig.TILE_SIZE - 12, GameConfig.TILE_SIZE - 12);
        g.setColor(Color.BLACK);
        g.setFont(new Font("SansSerif", Font.PLAIN, 12));
        g.drawString(b.shortName(), px + 10, py + 20);
    }
}




   /*  private void drawUnit(Graphics g, Tile t) {
        Unit u = t.getUnit();
        if (u == null) return;
        int px = t.x() * GameConfig.TILE_SIZE, py = t.y() * GameConfig.TILE_SIZE;
        //In Java, you can 
        // declare multiple variables of the same type 
        // in a single line, separated by commas.
        g.setColor(u.faction() == Faction.RED ? Color.RED : Color.BLUE);//hadi kochi if else
        //g.fillOval(px + 12, py + 12, GameConfig.TILE_SIZE - 24, GameConfig.TILE_SIZE - 24);
        g.fillOval(px , py , GameConfig.TILE_SIZE , GameConfig.TILE_SIZE );
        //It draws a filled oval (circle/ellipse) inside the rectangle defined by (x, y, width, height).
        //px and py are the top-left corner of the tile.
        //If we drew the circle starting exactly at (px, py), it would sit in the corner, not centered.
        g.setColor(Color.WHITE);
        //g.setFont(new Font("SansSerif", Font.BOLD, 12));
        g.setFont(new Font("SansSerif", Font.BOLD, 24));
        //g.drawString(u.shortName(), px + 16, py + 28);
        //px and py are the pixel coordinates of the tile (top-left corner).
        //Adding 16 and 28 shifts the text inside the tile, so it doesn’t overlap the edges.
        g.drawString(u.shortName(), px + 8, py + 32);
        // health bar
        g.setColor(Color.DARK_GRAY);
        g.fillRect(px + 8, py + GameConfig.TILE_SIZE - 10, GameConfig.TILE_SIZE - 16, 6);
        g.setColor(Color.GREEN);
        double hpRatio = Math.max(0, Math.min(1.0, u.hp() / 40.0));
        g.fillRect(px + 8, py + GameConfig.TILE_SIZE - 10, (int)((GameConfig.TILE_SIZE - 16) * hpRatio), 6);
    }
*/







/*public void animateMove(Unit unit, Tile target) {
 
    
    unit.isAnimating = true;
    int startX = unit.getPixelX();
    int startY = unit.getPixelY();
    int endX = target.x() * GameConfig.TILE_SIZE;
    int endY = target.y() * GameConfig.TILE_SIZE;

    int steps = 10;
    int delay = 30; // ms per frame
    int dx = (endX - startX) / steps;
    int dy = (endY - startY) / steps;

    javax.swing.Timer timer = new javax.swing.Timer(delay, null);
    final int[] count = {0};

    /*timer.addActionListener(e -> {
        if (count[0] < steps) {
            unit.setPixelPosition(startX + dx * count[0], startY + dy * count[0]);
            count[0]++;
            repaint();
        } else {
            timer.stop();
            unit.setPixelPosition(endX, endY);
            unit.tile().setUnit(null); // remove from old tile
            target.setUnit(unit);      // place on new tile
            unit.tile = target;
            unit.isAnimating = false;
            repaint();
        }
    });
    
    timer.start();
}*/
public void animateMove(Unit unit, Tile target) {
    // Check if unit has moves left
    if (unit.getMove() <= 0) {
        System.out.println("No moves left for this unit.");
        return;
    }

    unit.isAnimating = true;
    int startX = unit.getPixelX();
    int startY = unit.getPixelY();
    int endX = target.x() * GameConfig.TILE_SIZE;
    int endY = target.y() * GameConfig.TILE_SIZE;

    int steps = 10;
    int delay = 30; // ms per frame
    int dx = (endX - startX) / steps;
    int dy = (endY - startY) / steps;

    javax.swing.Timer timer = new javax.swing.Timer(delay, null);
    final int[] count = {0};

    timer.addActionListener(e -> {
        if (count[0] < steps) {
            unit.setPixelPosition(startX + dx * count[0], startY + dy * count[0]);
            count[0]++;
            repaint();
        } else {
            timer.stop();
            unit.setPixelPosition(endX, endY);

            // Update tile references
            unit.tile().setUnit(null);
            target.setUnit(unit);
            unit.tile = target;

            // ✅ decrement move counter here
            unit.setMove(unit.getMove() - 1);

            unit.isAnimating = false;
            repaint();
        }
    });

    timer.start();
}










private void drawUnit(Graphics g, Tile t) {
    Unit u = t.getUnit();
    if (u == null) return;
    int px = u.isAnimating() ? u.getPixelX() : t.x() * GameConfig.TILE_SIZE;
    int py = u.isAnimating() ? u.getPixelY() : t.y() * GameConfig.TILE_SIZE;

    

    Image img = UnitTextureManager.get(u);
    if (img != null) {
        g.drawImage(img, px, py, GameConfig.TILE_SIZE, GameConfig.TILE_SIZE, null);
    } else {
        // fallback: draw colored circle
        g.setColor(u.faction() == Faction.RED ? Color.RED : Color.BLUE);
        g.fillOval(px, py, GameConfig.TILE_SIZE, GameConfig.TILE_SIZE);
        g.setColor(Color.WHITE);
        g.setFont(new Font("SansSerif", Font.BOLD, 24));
        g.drawString(u.shortName(), px + 8, py + 32);
    }

    // health bar
    g.setColor(Color.DARK_GRAY);
    g.fillRect(px + 8, py + GameConfig.TILE_SIZE - 10, GameConfig.TILE_SIZE - 16, 6);
    g.setColor(Color.GREEN);
    double hpRatio = Math.max(0, Math.min(1.0, u.hp() / 40.0));
    g.fillRect(px + 8, py + GameConfig.TILE_SIZE - 10, (int)((GameConfig.TILE_SIZE - 16) * hpRatio), 6);
}

  

}


