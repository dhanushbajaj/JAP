package gol;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import java.util.Random;

import javax.swing.JColorChooser;
import javax.swing.JOptionPane;
import javax.swing.JToggleButton;

/**
 * GameController Class to handle basic functionalities
 * @author Dhanush Bajaj
 *
 */
public class GameController {
    private GameModel model;
    private GameView view;
    private Timer timer;
    private Color currentColor;

    /**
     * COnstructor for GameController
     * @param model
     * @param view
     */
    public GameController(GameModel model, GameView view) {
        this.model = model;
        this.view = view;
        // Attach action listeners to buttons in the view
        attachActionListeners();
    }

    /**
     * Method to handle Action Listeners
     */
    private void attachActionListeners() {
    	view.getStartButton().addActionListener(e -> startGame());
        view.getStopButton().addActionListener(e -> stopGame());
        view.getRandomButton().addActionListener(e -> generateRandomModel());
        view.getManualButton().addActionListener(e -> {
        	
        toggleManualMode();
        });
        view.getColorButton().addActionListener(e ->{
                // Open the color chooser dialog
                Color chosenColor = JColorChooser.showDialog(view.frame, "Choose a color", view.currentColor);
                if (chosenColor != null) { // If a color was chosen
                    view.currentColor = chosenColor; // Update the current color
                    
                }
            
        });

        // Text Fields
        view.getModelInput().addActionListener(e -> updateModelConfiguration());
        view.getStepsInput().addActionListener(e -> updateMaxSteps());
    }

    /**
     * method to start game
     */
    private void startGame() {
    	 if (timer != null && timer.isRunning()) {
             timer.stop();
         }

         
         model.applyConfiguration();

         // Set up the Timer to call the step method at fixed intervals
         timer = new Timer(100, new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 step();
             }
         });

         timer.start(); // Start the game loop
     }

    /**
     * method to toggle manual mode
     */
    private void toggleManualMode() {
        boolean isManualMode = view.isManualModeEnabled();
        view.setManualMode(!isManualMode);
        if (!isManualMode) {
            // Enable manual editing of cells
            for (int row = 0; row < model.getRows(); row++) {
                for (int col = 0; col < model.getCols(); col++) {
                    int finalRow = row;
                    int finalCol = col;
                    JToggleButton cellButton = view.getCellButton(row, col);
                    ActionListener listener = e -> model.setCellState(finalRow, finalCol, cellButton.isSelected());
                    cellButton.addActionListener(listener);
                }
            }
        } else {
            // Disable manual editing of cells
            for (int row = 0; row < model.getRows(); row++) {
                for (int col = 0; col < model.getCols(); col++) {
                    JToggleButton cellButton = view.getCellButton(row, col);
                    for (ActionListener al : cellButton.getActionListeners()) {
                        cellButton.removeActionListener(al);
                    }
                }
            }
        }
    }
    
    /**
     * method to stop game
     */
    private void stopGame() {
    	// Stop the timer if it's currently running
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }
        
        
        view.getStartButton().setEnabled(true); // Re-enable the start button
        view.getStopButton().setEnabled(false); // Disable the stop button if the game is not running
    }
    

    /**
     * method to generate random grid
     */
    private void generateRandomModel() {
    	Random rand = new Random();
        int rows = model.getRows();
        int cols = model.getCols();
        boolean[][] randomBoard = new boolean[rows][cols];

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                randomBoard[row][col] = rand.nextBoolean(); // Each cell has a 50% chance to be alive
            }
        }

        model.setGrid(randomBoard); // Update the model with the new random configuration
        view.updateGrid(randomBoard); // Update the view to display the new configuration
    }
    

    /**
     * method to update model
     */
    private void updateModelConfiguration() {
        // Get the text from the model input text field
        String config = view.getModelInput().getText();
        // Update the model with the new configuration
        model.setConfiguration(config);
    }

    /**
     * method to update max number of steps to display solution
     */
    public void updateMaxSteps() {
        // Get the text from the steps input text field
        String stepsText = view.getStepsInput().getText();
        // Parse and update the model or controller state with the max steps
        try {
            int maxSteps = Integer.parseInt(stepsText);
            for(int i=0;i<=maxSteps;i++) {
            	step();
            }
        } catch (NumberFormatException ex) {
            // Handle the case where the input is not a valid integer
            JOptionPane.showMessageDialog(view.getFrame(), "Please enter a valid number of steps.");
        }
    }
    
    /**
     * method to move one step ahead
     */
    private void step() {
        int rows = model.getRows();
        int cols = model.getCols();
        boolean[][] currentBoard = model.getGrid();
        boolean[][] nextBoard = new boolean[rows][cols];

        // Determine the next state of each cell
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int livingNeighbors = countLivingNeighbors(currentBoard, row, col);
                if (currentBoard[row][col]) {
                    // Any live cell with two or three live neighbors survives.
                    nextBoard[row][col] = (livingNeighbors == 2 || livingNeighbors == 3);
                } else {
                    // Any dead cell with three live neighbors becomes a live cell.
                    nextBoard[row][col] = (livingNeighbors == 3);
                }
            }
        }

        // Apply the next state to the model
        model.setGrid(nextBoard);

        // Update the view to reflect the new model state
        view.updateGrid(nextBoard);
    }

    /**
     * method to count living neighbors for next step
     * @param board
     * @param row
     * @param col
     * @return
     */
    public int countLivingNeighbors(boolean[][] board, int row, int col) {
        int livingNeighbors = 0;
        int rows = board.length;
        int cols = board[0].length;

        // Check all the neighbors around the cell
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) {
                    // Skip the cell itself
                    continue;
                }
                int neighborRow = row + i;
                int neighborCol = col + j;

                // Check the validity of the neighbor cell position
                if (neighborRow >= 0 && neighborRow < rows &&
                    neighborCol >= 0 && neighborCol < cols &&
                    board[neighborRow][neighborCol]) {
                    livingNeighbors++;
                }
            }
        }
        return livingNeighbors;
    }
    
    
    
}
