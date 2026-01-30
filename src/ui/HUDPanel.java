// src/ui/HUDPanel.java
// src/ui/HUDPanel.java
package ui;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import model.AIController;
import model.Player;

public class HUDPanel extends JPanel {
    private final BoardPanel board;
    private final Player human;
    private final Player ai;
    private final JLabel status = new JLabel("Ready.");
    private final JLabel resources = new JLabel();

    public HUDPanel(BoardPanel board, Player human, Player ai) {
        this.board = board; this.human = human; this.ai = ai;
        setLayout(new FlowLayout(FlowLayout.LEFT, 15, 15));
        setBackground(new Color(35, 35, 40)); // Dark background
        setPreferredSize(new Dimension(0, 80)); // Taller panel

        // Create custom painted buttons
        CustomButton buildCamp = new CustomButton("🏰 Build Camp", new Color(255, 140, 0));
        CustomButton trainSoldier = new CustomButton("⚔️ Train Soldier", new Color(220, 20, 60));
        CustomButton trainArcher = new CustomButton("🏹 Train Archer", new Color(34, 139, 34));
        CustomButton trainCavalry = new CustomButton("🐴 Train Cavalry", new Color(138, 43, 226));
        CustomButton endTurn = new CustomButton("⏭️ End Turn", new Color(25, 118, 210));
 
        // Button actions
        endTurn.addActionListener(e -> {
            human.onTurnStart();
            ai.onTurnStart();
              


      




            if (!ai.units().isEmpty()) {
                AIController.playTurn(ai, human, board.map());
                status.setText("✅ Turn advanced. AI has played.");
            } else {
                status.setText("⚠️ Turn advanced. AI has no units.");
            }

            updateResources();
            board.repaint();
        });

        buildCamp.addActionListener(e -> {
            boolean ok = board.input().buildCamp();
            status.setText(ok ? "✅ Training camp built." : "❌ Cannot build.");
            updateResources(); 
            board.repaint();
        });
        
        trainSoldier.addActionListener(e -> { 
            status.setText(board.input().train("soldier") ? "✅ Soldier trained." : "❌ Cannot train."); 
            updateResources(); 
            board.repaint(); 
        });
        
        trainArcher.addActionListener(e -> { 
            status.setText(board.input().train("archer") ? "✅ Archer trained." : "❌ Cannot train."); 
            updateResources(); 
            board.repaint(); 
        });
        
        trainCavalry.addActionListener(e -> { 
            status.setText(board.input().train("cavalry") ? "✅ Cavalry trained." : "❌ Cannot train."); 
            updateResources(); 
            board.repaint(); 
        });

        // Style status label
        status.setForeground(Color.WHITE);
        status.setFont(new Font("SansSerif", Font.BOLD, 16));
        
        // Style resources label
        resources.setForeground(new Color(255, 215, 0));
        resources.setFont(new Font("SansSerif", Font.BOLD, 16));

        // Add components
        add(buildCamp); 
        add(trainSoldier); 
        add(trainArcher); 
        add(trainCavalry); 
        add(endTurn);
        add(createSeparator());
        add(status); 
        add(createSeparator());
        add(resources);

        updateResources();
    }

    /**
     * Custom button class with guaranteed rendering
     */
    private static class CustomButton extends JButton {
        private Color buttonColor;
        private boolean isHovered = false;
        private boolean isPressed = false;

        public CustomButton(String text, Color color) {
            super(text);
            this.buttonColor = color;
            
            setPreferredSize(new Dimension(150, 45));
            setFont(new Font("SansSerif", Font.BOLD, 14));
            setForeground(Color.WHITE);
            setFocusPainted(false);
            setBorderPainted(false);
            setContentAreaFilled(false);
            
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    isHovered = true;
                    setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    repaint();
                }
                
                @Override
                public void mouseExited(MouseEvent e) {
                    isHovered = false;
                    setCursor(Cursor.getDefaultCursor());
                    repaint();
                }
                
                @Override
                public void mousePressed(MouseEvent e) {
                    isPressed = true;
                    repaint();
                }
                
                @Override
                public void mouseReleased(MouseEvent e) {
                    isPressed = false;
                    repaint();
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Determine button color based on state
            Color drawColor = buttonColor;
            if (isPressed) {
                drawColor = buttonColor.darker();
            } else if (isHovered) {
                drawColor = buttonColor.brighter();
            }
            
            // Draw button background
            g2.setColor(drawColor);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
            
            // Draw border
            g2.setColor(drawColor.darker().darker());
            g2.setStroke(new BasicStroke(3));
            g2.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 10, 10);
            
            // Draw text
            g2.setColor(Color.WHITE);
            g2.setFont(getFont());
            FontMetrics fm = g2.getFontMetrics();
            int textWidth = fm.stringWidth(getText());
            int textHeight = fm.getHeight();
            int x = (getWidth() - textWidth) / 2;
            int y = (getHeight() + textHeight / 2) / 2 - 2;
            g2.drawString(getText(), x, y);
            
            g2.dispose();
        }
    }

    /**
     * Create a vertical separator
     */

    private JLabel createSeparator() {
        JLabel sep = new JLabel(" | ");
        sep.setForeground(new Color(100, 100, 100));
        sep.setFont(new Font("SansSerif", Font.BOLD, 20));
        return sep;
    }

    private void updateResources() {
        resources.setText("💰 " + human.resources());
    }

}







/*package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import model.AIController;
import model.Player;

public class HUDPanel extends JPanel {
    private final BoardPanel board;
    private final Player human;
    private final Player ai;
    private final JLabel status = new JLabel("Ready.");
    private final JLabel resources = new JLabel();

    public HUDPanel(BoardPanel board, Player human, Player ai) {
        this.board = board; this.human = human; this.ai = ai;
        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        setBackground(new Color(45, 45, 48)); // Dark background

        // Create styled buttons
        JButton buildCamp = createStyledButton("Build Camp", new Color(255, 140, 0), Color.WHITE);
        JButton trainSoldier = createStyledButton("Train Soldier", new Color(220, 20, 60), Color.WHITE);
        JButton trainArcher = createStyledButton("Train Archer", new Color(34, 139, 34), Color.WHITE);
        JButton trainCavalry = createStyledButton("Train Cavalry", new Color(138, 43, 226), Color.WHITE);
        JButton endTurn = createStyledButton("End Turn", new Color(70, 130, 180), Color.WHITE);

        // Button actions
        endTurn.addActionListener(e -> {
            human.onTurnStart(); // collect production + reset moves
            ai.onTurnStart();    // reset AI moves and collect resources

            if (!ai.units().isEmpty()) {
                AIController.playTurn(ai, human, board.map()); // AI moves and attacks
                status.setText("Turn advanced. AI has played.");
            } else {
                status.setText("Turn advanced. AI has no units.");
            }

            updateResources();
            board.repaint();
        });

        buildCamp.addActionListener(e -> {
            boolean ok = board.input().buildCamp();
            status.setText(ok ? "Training camp built." : "Cannot build.");
            updateResources(); 
            board.repaint();
        });
        
        trainSoldier.addActionListener(e -> { 
            status.setText(board.input().train("soldier") ? "Soldier trained." : "Cannot train."); 
            updateResources(); 
            board.repaint(); 
        });
        
        trainArcher.addActionListener(e -> { 
            status.setText(board.input().train("archer") ? "Archer trained." : "Cannot train."); 
            updateResources(); 
            board.repaint(); 
        });
        
        trainCavalry.addActionListener(e -> { 
            status.setText(board.input().train("cavalry") ? "Cavalry trained." : "Cannot train."); 
            updateResources(); 
            board.repaint(); 
        });

        // Style status label
        status.setForeground(Color.WHITE);
        status.setFont(new Font("SansSerif", Font.BOLD, 14));
        
        // Style resources label
        resources.setForeground(new Color(255, 215, 0)); // Gold color
        resources.setFont(new Font("SansSerif", Font.BOLD, 14));

        // Add components
        add(buildCamp); 
        add(trainSoldier); 
        add(trainArcher); 
        add(trainCavalry); 
        add(endTurn);
        add(status); 
        add(resources);

        updateResources();
    }

    /**
     * Create a styled button with custom colors
     */
   /*  private JButton createStyledButton(String text, Color bgColor, Color fgColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFocusPainted(false);
        button.setFont(new Font("SansSerif", Font.BOLD, 12));
        button.setPreferredSize(new Dimension(120, 35));
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(bgColor.darker(), 2),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        button.setOpaque(true);
        
        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });
        
        return button;
    }

    private void updateResources() {
        resources.setText(" | Resources: " + human.resources());
    }
}*/


/*package ui;

import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import model.AIController;
import model.Player;


public class HUDPanel extends JPanel {
    private final BoardPanel board;
    private final Player human;
    private final Player ai;
    private final JLabel status = new JLabel("Ready.");
    private final JLabel resources = new JLabel();

    public HUDPanel(BoardPanel board, Player human, Player ai) {
        this.board = board; this.human = human; this.ai = ai;
        setLayout(new FlowLayout(FlowLayout.LEFT));

        JButton endTurn = new JButton("End Turn");
        JButton buildCamp = new JButton("Build Camp");
        JButton trainSoldier = new JButton("Train Soldier");
        JButton trainArcher = new JButton("Train Archer");
        JButton trainCavalry = new JButton("Train Cavalry");

        /*endTurn.addActionListener(e -> {
            human.onTurnStart(); // collect production + reset moves
            // naive AI turn
            ai.onTurnStart();
            status.setText("Turn advanced.");
            updateResources();
            board.repaint();
        });*/
/* 
endTurn.addActionListener(e -> {
    human.onTurnStart(); // collect production + reset moves
    ai.onTurnStart();    // reset AI moves and collect resources

    if (!ai.units().isEmpty()) {
        AIController.playTurn(ai, human, board.map()); // AI moves and attacks
        status.setText("Turn advanced. AI has played.");
    } else {
        status.setText("Turn advanced. AI has no units.");
    }

    updateResources();
    board.repaint();
});



        buildCamp.addActionListener(e -> {
            boolean ok = board.input().buildCamp();
            status.setText(ok ? "Training camp built." : "Cannot build.");
            updateResources(); board.repaint();
        });
        trainSoldier.addActionListener(e -> { status.setText(board.input().train("soldier") ? "Soldier trained." : "Cannot train."); updateResources(); board.repaint(); });
        trainArcher.addActionListener(e -> { status.setText(board.input().train("archer") ? "Archer trained." : "Cannot train."); updateResources(); board.repaint(); });
        trainCavalry.addActionListener(e -> { status.setText(board.input().train("cavalry") ? "Cavalry trained." : "Cannot train."); updateResources(); board.repaint(); });

        add(buildCamp); add(trainSoldier); add(trainArcher); add(trainCavalry); add(endTurn);
        add(status); add(resources);

        updateResources();
    }

    private void updateResources() {
        resources.setText(" | Resources: " + human.resources());
    }
}*/

