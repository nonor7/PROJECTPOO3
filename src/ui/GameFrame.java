// src/ui/GameFrame.java


// src/ui/GameFrame.java
// src/ui/GameFrame.java
package ui;

import java.awt.*;
import javax.swing.*;
import model.MapGrid;
import model.Player;

public class GameFrame {
    private final MapGrid map;
    private final Player human;
    private final Player ai;

    public GameFrame(MapGrid map, Player human, Player ai) {
        this.map = map; this.human = human; this.ai = ai;
    }

    public void showUI() {
        JFrame f = new JFrame("Strategy Game - Medieval Warfare");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create main panels
        BoardPanel board = new BoardPanel(map, human, ai);
        HUDPanel hud = new HUDPanel(board, human, ai);
        JPanel infoPanel = createInfoPanel();
        JPanel sidePanel = createSidePanel();

        // Set up layout
        f.setLayout(new BorderLayout(5, 5));
        
        // Add panels with borders
        f.add(createTitlePanel(), BorderLayout.NORTH);
        
        // Center content with board
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(hud, BorderLayout.NORTH);
        centerPanel.add(board, BorderLayout.CENTER);
        f.add(centerPanel, BorderLayout.CENTER);
        
        // Add side panel for info
        f.add(sidePanel, BorderLayout.EAST);
        
        // Add bottom info panel
        f.add(infoPanel, BorderLayout.SOUTH);

        // Final frame settings
        f.pack();
        f.setMinimumSize(new Dimension(1200, 800));
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

    /**
     * Create title panel with game name
     */
    private JPanel createTitlePanel() {
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(30, 30, 35));
        titlePanel.setPreferredSize(new Dimension(0, 50));
        titlePanel.setLayout(new BorderLayout());
        
        JLabel titleLabel = new JLabel("⚔️ MEDIEVAL STRATEGY WARFARE ⚔️", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        titleLabel.setForeground(new Color(255, 215, 0)); // Gold
        
        titlePanel.add(titleLabel, BorderLayout.CENTER);
        
        return titlePanel;
    }

    /**
     * Create side panel with game statistics
     */
    private JPanel createSidePanel() {
        JPanel sidePanel = new JPanel();
        sidePanel.setBackground(new Color(40, 40, 45));
        sidePanel.setPreferredSize(new Dimension(200, 0));
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Player info section
        sidePanel.add(createSectionLabel("🔴 PLAYER"));
        sidePanel.add(Box.createVerticalStrut(10));
        sidePanel.add(createInfoLabel("Units: " + human.units().size()));
        sidePanel.add(createInfoLabel("Buildings: " + human.buildings().size()));
        sidePanel.add(Box.createVerticalStrut(20));

        // AI info section
        sidePanel.add(createSectionLabel("🔵 AI OPPONENT"));
        sidePanel.add(Box.createVerticalStrut(10));
        sidePanel.add(createInfoLabel("Units: " + ai.units().size()));
        sidePanel.add(createInfoLabel("Buildings: " + ai.buildings().size()));
        sidePanel.add(Box.createVerticalStrut(20));

        // Legend section
        sidePanel.add(createSectionLabel("📖 LEGEND"));
        sidePanel.add(Box.createVerticalStrut(10));
        sidePanel.add(createInfoLabel("🟩 Grass - Normal"));
        sidePanel.add(createInfoLabel("🟫 Forest - Slow"));
        sidePanel.add(createInfoLabel("🟦 Water - Impassable"));
        sidePanel.add(createInfoLabel("⛰️ Mountain - Impassable"));
        sidePanel.add(Box.createVerticalStrut(20));

        // Controls section
        sidePanel.add(createSectionLabel("🎮 CONTROLS"));
        sidePanel.add(Box.createVerticalStrut(10));
        sidePanel.add(createInfoLabel("Left Click - Select"));
        sidePanel.add(createInfoLabel("Right Click - Move/Attack"));
        sidePanel.add(Box.createVerticalStrut(20));

        // Tips section
        sidePanel.add(createSectionLabel("💡 TIPS"));
        sidePanel.add(Box.createVerticalStrut(10));
        JTextArea tips = new JTextArea(
            "Build training camps to produce units. " +
            "Train soldiers, archers, and cavalry. " +
            "Manage resources wisely!"
        );
        tips.setWrapStyleWord(true);
        tips.setLineWrap(true);
        tips.setEditable(false);
        tips.setFocusable(false);
        tips.setBackground(new Color(40, 40, 45));
        tips.setForeground(new Color(200, 200, 200));
        tips.setFont(new Font("SansSerif", Font.PLAIN, 11));
        sidePanel.add(tips);

        return sidePanel;
    }

    /**
     * Create bottom info panel with game stats
     */
    private JPanel createInfoPanel() {
        JPanel infoPanel = new JPanel();
        infoPanel.setBackground(new Color(30, 30, 35));
        infoPanel.setPreferredSize(new Dimension(0, 30));
        infoPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 5));

        JLabel mapInfo = new JLabel("Map: " + map.width() + "x" + map.height());
        mapInfo.setForeground(Color.LIGHT_GRAY);
        mapInfo.setFont(new Font("SansSerif", Font.PLAIN, 12));

        JLabel turnInfo = new JLabel("Turn: 1");
        turnInfo.setForeground(Color.LIGHT_GRAY);
        turnInfo.setFont(new Font("SansSerif", Font.PLAIN, 12));

        JLabel versionInfo = new JLabel("v1.0");
        versionInfo.setForeground(Color.GRAY);
        versionInfo.setFont(new Font("SansSerif", Font.PLAIN, 10));

        infoPanel.add(mapInfo);
        infoPanel.add(turnInfo);
        infoPanel.add(new JSeparator(SwingConstants.VERTICAL));
        infoPanel.add(versionInfo);

        return infoPanel;
    }

    /**
     * Create a section label (header style)
     */
    private JLabel createSectionLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(new Color(255, 215, 0)); // Gold
        label.setFont(new Font("SansSerif", Font.BOLD, 14));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    /**
     * Create an info label (regular text)
     */
    private JLabel createInfoLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(new Color(200, 200, 200)); // Light gray
        label.setFont(new Font("SansSerif", Font.PLAIN, 12));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }


}


/*package ui;

import model.MapGrid;
import model.Player;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;

public class GameFrame {
    private final MapGrid map;
    private final Player human;
    private final Player ai;

    public GameFrame(MapGrid map, Player human, Player ai) {
        this.map = map; this.human = human; this.ai = ai;
    }

    public void showUI() {
        JFrame f = new JFrame("Strategy Game (Swing)");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        BoardPanel board = new BoardPanel(map, human, ai);
        HUDPanel hud = new HUDPanel(board, human, ai);

        f.setLayout(new BorderLayout());
        f.add(hud, BorderLayout.NORTH);
        f.add(board, BorderLayout.CENTER);

        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
}*/

