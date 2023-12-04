import java.util.*;

public class TuringMachine {
    private List<String> tape;
    private Map<String, String> transitions;
    private int headPosition;
    private String currentState;

    public TuringMachine() {
        tape = new ArrayList<>();
        transitions = new HashMap<>();
        // Initialize the tape with zeros and the head in the middle
        for (int i = 0; i < 24; i++) {
            tape.add("0");
        }
        headPosition = tape.size() / 2;
        currentState = "0"; // Initial state
    }

    public void loadDefinition(String definition) {
        // Parse and store the transition rules
        parseDefinition(definition);
    }

    private void parseDefinition(String definition) {
        String[] tuples = definition.split(" ");
        for (String tuple : tuples) {
            if (tuple.length() != 5) {
                throw new IllegalArgumentException("Invalid tuple length");
            }
            String key = tuple.substring(0, 2); // Current state and read symbol
            transitions.put(key, tuple.substring(2));
        }
    }

    public boolean validate() {
        // Validate the machine's definition
        // Placeholder for validation logic
        return true;
    }

    public String run() {
        StringBuilder output = new StringBuilder();
        while (true) {
            String currentSymbol = tape.get(headPosition);
            String key = currentState + currentSymbol;
            String transition = transitions.get(key);

            if (transition == null || currentState.equals("0")) {
                // Halt condition
                break;
            }

            currentState = transition.substring(0, 1);
            String writeSymbol = transition.substring(1, 2);
            String direction = transition.substring(2, 3);

            tape.set(headPosition, writeSymbol);
            if (direction.equals("0")) {
                headPosition--; // Move left
            } else if (direction.equals("1")) {
                headPosition++; // Move right
            } else {
                // Halt with acceptance or error
                break;
            }

            // Expand the tape if necessary
            expandTapeIfNeeded();
        }
        tape.forEach(output::append);
        return output.toString();
    }

    private void expandTapeIfNeeded() {
        if (headPosition < 0) {
            tape.add(0, "0");
            headPosition = 0;
        } else if (headPosition >= tape.size()) {
            tape.add("0");
        }
    }
}
