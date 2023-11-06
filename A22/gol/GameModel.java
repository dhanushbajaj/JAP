package gol;

import javax.swing.JToggleButton;
/**
 * 
 * GameMOdel class to maintain some data points
 * @author Dhanush Bajaj
 *
 */
public class GameModel {
    private int rows;
    private int cols;
    private boolean[][] board;
    private String configuration;
    private JToggleButton[][] cellButtons;

    /**
     * COnstructor for GameModel
     */
    public GameModel() {
        this.rows = 50;
        this.cols = 50;
        this.board = new boolean[rows][cols];
        this.configuration = "";
        this.cellButtons = new JToggleButton[rows][cols];
    }

    /**
     *  Setters and getters for the configuration string
     * @return
     */
    public String getConfiguration() {
        return configuration;
    }

    public void setConfiguration(String configuration) {
        this.configuration = configuration;
        applyConfiguration();
    }

    /** 
     * Applies the configuration string to the board
     */
    void applyConfiguration() {
        String[] bits = configuration.trim().split("\\s+");
        for (int i = 0; i < bits.length; i++) {
            int row = i / cols;
            int col = i % cols;
            if (row < rows && col < cols) {
                board[row][col] = bits[i].equals("1");
            }
        }
    }

    /**
     * Methods to get and set the state of a cell
     * @param row
     * @param col
     * @return
     */
    public boolean isCellAlive(int row, int col) {
        if (row >= 0 && row < rows && col >= 0 && col < cols) {
            return board[row][col];
        }
        return false;
    }

    public void setCellState(int row, int col, boolean alive) {
        if (row >= 0 && row < rows && col >= 0 && col < cols) {
            board[row][col] = alive;
        }
    }

    /**
     *  Method to get the entire grid
     * @return
     */
    public boolean[][] getGrid() {
        return board;
    }

    /**
     *  Method to set the entire grid
     * @param newBoard
     */
    public void setGrid(boolean[][] newBoard) {
        if (newBoard.length == rows && newBoard[0].length == cols) {
            this.board = newBoard;
        }
    }

    /**
     *  Method to get the dimensions of the board
     * @return
     */
    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }
}
