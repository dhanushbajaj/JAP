package cs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Represents a graphical user interface for simulating cellular automata.
 * This class provides controls for user interaction and visualizes the simulation results.
 */

public class CAInterface extends JPanel {
    
    /** The cellular automata simulation instance. */
private CellularAutomata automata;
    
    /** The input field for binary rules. */
private JTextField binaryInput;
    
    /** The button to initiate the simulation. */
private JButton showButton;
    
    /** The grid representing the cellular automata states. */
private int[][] grid;

    public CAInterface(int width, int height) {
        this.automata = new CellularAutomata(width);
        this.grid = new int[height][width];

        // Set layout
        
        setLayout(new BorderLayout());

        // Header
        add(new JLabel(new ImageIcon("ca.png")), BorderLayout.NORTH);

        // Binary Input and Controls in the footer
        JPanel footerPanel = new JPanel();
        binaryInput = new JTextField(8);
        showButton = new JButton(java.util.ResourceBundle.getBundle("cs.CS").getString("start") + " " + java.util.ResourceBundle.getBundle("cs.CS").getString("model"));
        showButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int rule = Integer.parseInt(binaryInput.getText(), 2);
                automata.setRule(rule);
                for (int i = 0; i < height; i++) {
                    grid[i] = automata.getNextState();
                }
                repaint();
            }
        });
        footerPanel.add(new JLabel("Rule:"));
        footerPanel.add(binaryInput);
        footerPanel.add(showButton);
        add(footerPanel, BorderLayout.SOUTH);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 1) {
                    g.fillRect(j, i, 1, 1);
                }
            }
        }
    }
}