package gol;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;
/**
 * Class GameView - handles creation of main window
 * @author Dhanush Bajaj
 *
 */
public class GameView {
    public JFrame frame;
    private JPanel gridPanel;
    private JButton randomButton, manualButton, startButton, stopButton, colorButton;
    private JCheckBox multiColorCheckbox;
    private JTextField modelInput, stepsInput, stepsExecutedDisplay;
    private JColorChooser colorChooser;
    private ImageIcon logoIcon;
    public Color currentColor;
    private boolean multiColor = false;
    private JToggleButton[][] cellButtons;
    private boolean manualModeEnabled = false;
    private int rows;
    private int cols;
    private GameModel model;
    private GameController controller;
    private int neighbours;
    
    /**
     * Constructor for class
     */
    public GameView() {
    	Locale.setDefault(new Locale("en", "US"));
        initializeComponents();
        currentColor = Color.BLACK;
        this.neighbours=neighbours;
        this.model = model;
        this.controller = controller;
        this.rows = rows;
        this.cols = cols;
        cellButtons = new JToggleButton[rows][cols];
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                JToggleButton cellButton = new JToggleButton();
                cellButtons[row][col] = cellButton;
                cellButton.setEnabled(false); // Disabled by default
                
            }
        }
    }

    /**
     * Method to intialize components
     */
    private void initializeComponents() {
    	
        frame = new JFrame("Game of Life");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Menu Bar
        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Game");
        JMenu helpMenu = new JMenu("Help");
        JMenu languageMenu = new JMenu("Language");

        // Create and add menu items to the Game menu
        JMenuItem newItem = new JMenuItem("New", new ImageIcon("menuiconnew.png"));
        newItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Dispose of the current window
                JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(newItem);
                if (currentFrame != null) {
                    currentFrame.dispose();
                }
                // Create and show a new game window
                Game.createGameWindow();
            }
        });
        JMenuItem solutionItem = new JMenuItem("Solution", new ImageIcon("menuiconsol.png"));
        solutionItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.updateMaxSteps(); // Terminate the application
            }
        });
        JMenuItem exitItem = new JMenuItem("Exit", new ImageIcon("menuiconext.png"));
        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Terminate the application
            }
        });

        gameMenu.add(newItem);
        gameMenu.add(solutionItem);
        gameMenu.add(exitItem);

        // Create and add menu items to the Help menu
        JMenuItem colorsItem = new JMenuItem("Colors");
        JMenuItem aboutItem = new JMenuItem("About");
        
        helpMenu.add(colorsItem);
        helpMenu.add(aboutItem);

        // Create and add menu items to the Language menu
        JMenuItem englishItem = new JMenuItem("English");
        englishItem.addActionListener(e -> Locale.setDefault(new Locale("en", "US")));
        JMenuItem portugueseItem = new JMenuItem("Portuguese");
        portugueseItem.addActionListener(e -> Locale.setDefault(new Locale("pt", "BR")));
        JMenuItem frenchItem = new JMenuItem("French");
        frenchItem.addActionListener(e -> Locale.setDefault(new Locale("fr", "FR")));
        
        languageMenu.add(englishItem);
        languageMenu.add(portugueseItem);
        languageMenu.add(frenchItem);

        // Add menus to menu bar
        menuBar.add(gameMenu);
        menuBar.add(languageMenu);
        menuBar.add(helpMenu);

        // Set the menu bar
        frame.setJMenuBar(menuBar);


        // Logo
        logoIcon = new ImageIcon("logo.png"); 
        JLabel logoLabel = new JLabel(logoIcon);
        logoLabel.setHorizontalAlignment(JLabel.CENTER);
        frame.add(logoLabel, BorderLayout.NORTH);

        // Grid Panel
        gridPanel = new JPanel(new GridLayout(50, 50)); // 50x50 grid
        for (int i = 0; i < 50 * 50; i++) {
            JPanel cell = new JPanel();
            cell.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            gridPanel.add(cell);
        }
        JScrollPane gridScrollPane = new JScrollPane(gridPanel); // Scrollable pane for larger grids
        frame.add(gridScrollPane, BorderLayout.CENTER);

        // Bottom Controls
        JPanel controlsPanel = new JPanel();
        randomButton = new JButton(java.util.ResourceBundle.getBundle("gol.GL").getString("random"));
        manualButton = new JButton(java.util.ResourceBundle.getBundle("gol.GL").getString("manual"));
        modelInput = new JTextField(18);
        multiColorCheckbox = new JCheckBox(java.util.ResourceBundle.getBundle("gol.GL").getString("multi-color"));
        colorButton = new JButton(java.util.ResourceBundle.getBundle("gol.GL").getString("color"));
        startButton = new JButton(java.util.ResourceBundle.getBundle("gol.GL").getString("start"));
        stepsInput = new JTextField(5);
        stepsExecutedDisplay = new JTextField(5);
        stepsExecutedDisplay.setEditable(false);
        stopButton = new JButton("Stop");

        controlsPanel.add(randomButton);
        controlsPanel.add(manualButton);
        controlsPanel.add(new JLabel("Model:"));
        controlsPanel.add(modelInput);
        controlsPanel.add(multiColorCheckbox);
        controlsPanel.add(colorButton);
        controlsPanel.add(new JLabel("Steps:"));
        controlsPanel.add(stepsInput);
        controlsPanel.add(new JLabel("Executed:"));
        controlsPanel.add(stepsExecutedDisplay);
        controlsPanel.add(startButton);
        controlsPanel.add(stopButton);

        frame.add(controlsPanel, BorderLayout.SOUTH);

        // Color Chooser Dialog
        colorChooser = new JColorChooser();

        frame.revalidate();
        frame.repaint();
        
        // Finalize the frame
        frame.pack(); // Adjusts window to fit all components
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    /**
     * method to verify if manual mode is toggled
     * @param enabled
     */
    public void setManualMode(boolean enabled) {
        manualModeEnabled = enabled;
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                cellButtons[row][col].setEnabled(enabled);
            }
        }
    }
    
    public boolean isManualModeEnabled() {
        return this.manualModeEnabled;
    }

    /**
     *  Method to get a cell button by its row and column
     * 
     * @param row
     * @param col
     * @return
     */
    public JToggleButton getCellButton(int row, int col) {
        return cellButtons[row][col];
    }
    
    /**
     * method to update the grid output
     * @param grid
     */
    public void updateGrid(boolean[][] grid) {
        // Use invokeLater to ensure that updates are made on the EDT
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                if (gridPanel == null) {
                    // Create the grid panel only if it does not exist
                    gridPanel = new JPanel(new GridLayout(grid.length, grid[0].length));
                    frame.add(gridPanel, BorderLayout.CENTER);

                    for (int row = 0; row < grid.length; row++) {
                        for (int col = 0; col < grid[row].length; col++) {
                            JPanel cellPanel = (JPanel) gridPanel.getComponent(row * grid[row].length + col);
                            if (grid[row][col]) { // If the cell is "on"
                            	
                                Color cellColor = multiColorCheckbox.isSelected() ? getRandomColor() : currentColor;
                                cellPanel.setBackground(cellColor);
                            } else {
                                cellPanel.setBackground(Color.WHITE);
                            }
                        }
                    }
                } else {
                    // Update existing cell panels
                	for (int row = 0; row < grid.length; row++) {
                        for (int col = 0; col < grid[row].length; col++) {
                            JPanel cellPanel = (JPanel) gridPanel.getComponent(row * grid[row].length + col);
                            if (grid[row][col]) { // If the cell is "on"
                            	
                                Color cellColor = multiColorCheckbox.isSelected() ? getRandomColor() : currentColor;
                                cellPanel.setBackground(cellColor);
                            } else {
                                cellPanel.setBackground(Color.WHITE);
                            }
                        }
                    }
                }

                // Revalidate and repaint the frame to display updates
                frame.revalidate();
                frame.repaint();
            }
        });
    }


    public JButton getRandomButton() {
        return randomButton;
    }

    public JButton getManualButton() {
        return manualButton;
    }

    public JButton getStartButton() {
        return startButton;
    }

    public JButton getStopButton() {
        return stopButton;
    }

    public JButton getColorButton() {
        return colorButton;
    }

    // Getters for text fields
    public JTextField getModelInput() {
        return modelInput;
    }

    public JTextField getStepsInput() {
        return stepsInput;
    }

    public JTextField getStepsExecutedDisplay() {
        return stepsExecutedDisplay;
    }

    // Getter for the checkbox
    public JCheckBox getMultiColorCheckbox() {
        return multiColorCheckbox;
    }

    // Getter for the color chooser
    public JColorChooser getColorChooser() {
        return colorChooser;
    }
    
    public void setModelInput(String text) {
        modelInput.setText(text);
    }

    public void setStepsInput(String text) {
        stepsInput.setText(text);
    }

    public void setStepsExecutedDisplay(String text) {
        stepsExecutedDisplay.setText(text);
    }

    // Setter for the checkbox
    public void setMultiColorCheckbox(boolean isSelected) {
        multiColorCheckbox.setSelected(isSelected);
    }

    public JFrame getFrame() {
        return frame;
    }

    

    // Method to get the current color
    public Color getCurrentColor() {
        return currentColor;
    }

    
    public void setColor(Color color) {
        this.currentColor = color;
        
    }

    public void setMultiColor(boolean multiColor) {
        this.multiColor = multiColor;
        
    }

    /**
     * method to color the cell according to number of neighbors
     * @return
     */
    private Color getRandomColor() {
    	switch (neighbours) {
        case 0: return Color.RED;
        case 1: return Color.GREEN;
        case 2: return new Color(0x000080); 
        case 3: return Color.YELLOW;
        case 4: return Color.PINK;
        case 5: return Color.CYAN;
        case 6: return Color.GRAY;
        case 7: return new Color(0xF5F5DC); // Beige
        case 8: return new Color(0xE6E6FA); // Lavender
        default: return Color.BLACK;
    	}
    }

    /**
     * helper method
     * @param neighbors
     * @return
     */
    private int getColorBasedOnNeighbors(int neighbors) {
        neighbours = neighbors;
    	return neighbours;
    }
    
    /**
     * method to count neighbors
     */
    private void countNeibour() {
        int rows = model.getRows();
        int cols = model.getCols();
        boolean[][] currentBoard = model.getGrid();
        boolean[][] nextBoard = new boolean[rows][cols];

        // Determine the next state of each cell
        for (int row = 0; row < rows; row++) {
        	for (int col = 0; col < cols; col++){
        		int livingNeighbors = controller.countLivingNeighbors(currentBoard, row, col);
        		getColorBasedOnNeighbors(livingNeighbors);
        	}
        }
    }
    
}


