package cs;


/**
 * Represents the core logic for cellular automata simulation.
 * This class maintains the current state and applies the given rule to compute the next state.
 */

public class CellularAutomata {
    
    /** The cellular automata rule in binary format. */
private String ruleBinary;
    
    /** The current state of the cellular automata. */
private int[] currentState;

    // Constructor
    
    /**
     * Initializes the cellular automata with a given width.
     * The initial state has a single '1' in the center.
     *
     * @param width The width of the cellular automata.
     */
public CellularAutomata(int width) {
        this.currentState = new int[width];
        this.currentState[width/2] = 1;  // Initialize with a single 1 in the center
    }

    
    /**
     * Sets the rule for the cellular automata based on the given rule number.
     *
     * @param ruleNumber The rule number to set.
     */
public void setRule(int ruleNumber) {
        this.ruleBinary = String.format("%8s", Integer.toBinaryString(ruleNumber)).replace(' ', '0');
    }

    
    /**
     * Computes and returns the next state of the cellular automata based on the current state and rule.
     *
     * @return The next state of the cellular automata.
     */
public int[] getNextState() {
        int[] nextState = new int[currentState.length];

        for (int i = 0; i < currentState.length; i++) {
            int left = (i == 0) ? 0 : currentState[i - 1];
            int center = currentState[i];
            int right = (i == currentState.length - 1) ? 0 : currentState[i + 1];
            String pattern = "" + left + center + right;
            int ruleIndex = Integer.parseInt(pattern, 2);
            nextState[i] = ruleBinary.charAt(7 - ruleIndex) == '1' ? 1 : 0;
        }

        currentState = nextState;
        return currentState;
    }
}