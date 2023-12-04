import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class TuringMachineGUI extends JFrame {
    private final JTextField tmInputField;
    private final JTextArea logArea;
    private final TuringMachine turingMachine;

    public TuringMachineGUI() {
        super("Turing Machine Simulator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLayout(new BorderLayout());

        tmInputField = new JTextField(40);
        logArea = new JTextArea();
        logArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logArea);

        JButton validateButton = new JButton("Validate");
        validateButton.addActionListener(this::validateAction);

        JButton runButton = new JButton("Run");
        runButton.addActionListener(this::runAction);

        JPanel controlPanel = new JPanel();
        controlPanel.add(new JLabel("TM:"));
        controlPanel.add(tmInputField);
        controlPanel.add(validateButton);
        controlPanel.add(runButton);

        add(controlPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        turingMachine = new TuringMachine();
        setVisible(true);
    }

    private void validateAction(ActionEvent e) {
        try {
            turingMachine.loadDefinition(tmInputField.getText());
            if (turingMachine.validate()) {
                logArea.setText("Turing machine is valid.\n");
            } else {
                logArea.setText("Turing machine is invalid.\n");
            }
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "Invalid TM definition: " + ex.getMessage(),
                    "Validation Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void runAction(ActionEvent e) {
        String tapeOutput = turingMachine.run();
        logArea.append("Turing machine finished execution.\n");
        logArea.append("Final tape configuration: " + tapeOutput + "\n");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TuringMachineGUI::new);
    }
}
