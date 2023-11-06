package cs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;


/**
 * Entry point for a program that provides a selection of different simulations or models.
 * This class contains a GUI for the user to choose between Cellular Automata, Game of Life, Turing Machine Server, and Turing Machine Client.
 */

public class CSModel {
    
    /**
     * Main method that initializes the program and displays the GUI for model selection.
     *
     * @param args Command line arguments (not used).
     */

public static void main(String[] args) {
    	Locale.setDefault(new Locale("en", "US"));
        String[] options = {
            "[A12] CA - Cellular Automata",
            "[A22] GL - Game of Life",
            "[A32] TM - Turing Machine Server",
            "[A32] TM Client"
        };

    JPanel panel = new JPanel();   
    // Language selection combo box
    JComboBox<String> languageComboBox = new JComboBox<>(new String[]{"English (US)", "Français (FR)", "Português (BR)"});
    languageComboBox.setSelectedIndex(0);  // Default to English
    languageComboBox.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String selectedLanguage = (String) languageComboBox.getSelectedItem();
            switch (selectedLanguage) {
                case "English (US)":
                    Locale.setDefault(new Locale("en", "US"));
                    break;
                case "Français (FR)":
                    Locale.setDefault(new Locale("fr", "FR"));
                    break;
                case "Português (BR)":
                    Locale.setDefault(new Locale("pt", "BR"));
                    break;
            }
            // Refresh the dialog to reflect language change
            panel.revalidate();
            panel.repaint();
        }
    });
    panel.add(languageComboBox);



        // Create a JPanel with a JComboBox for model selection
        JComboBox<String> comboBox = new JComboBox<>(options);
        panel.add(comboBox);
        int choice = JOptionPane.showConfirmDialog(
            null,
            panel,
            java.util.ResourceBundle.getBundle("cs.CS").getString("model"),
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );

        // Check if the user clicked OK and handle the selected model
        if (choice == JOptionPane.OK_OPTION) {
            String selectedOption = (String) comboBox.getSelectedItem();
            switch (selectedOption) {
                case "[A12] CA - Cellular Automata":
                    // Initialize Cellular Automata
                    displayCellularAutomata();
                    break;
                // Further cases can be added as needed for other models
                default:
                    System.exit(0);
            }
        }
    }

    private static void displayCellularAutomata() {
        JFrame frame = new JFrame("Cellular Automata Model");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 500);
        frame.add(new CAInterface(1000, 500)); // Assuming CAInterface takes width and height as parameters
        frame.setVisible(true);
    }
}