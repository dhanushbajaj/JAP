import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends JFrame {
    private JButton startButton;
    private JButton endButton;
    private JCheckBox finalizeCheckBox;
    private JTextArea logArea;
    private JTextField portField;
    private ServerSocket serverSocket;
    private boolean isRunning = false;

    public Server() {
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        setTitle("TURING MACHINE - SERVER");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLayout(new BorderLayout());

        // Top Panel
        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Port:"));
        portField = new JTextField(5);
        portField.setText("12345"); // Default port
        topPanel.add(portField);
        startButton = new JButton("Start");
        endButton = new JButton("End");
        finalizeCheckBox = new JCheckBox("Finalizes");
        topPanel.add(startButton);
        topPanel.add(finalizeCheckBox);
        topPanel.add(endButton);

        // Log Area
        logArea = new JTextArea();
        logArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logArea);
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Action Listeners
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int port = Integer.parseInt(portField.getText());
                startServer(port);
            }
        });

        endButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopServer();
            }
        });

        setVisible(true);
    }

    private void startServer(int port) {
        logArea.append("Starting server on port " + port + "...\n");
        isRunning = true;
        try {
            serverSocket = new ServerSocket(port);
            while (isRunning) {
                Socket clientSocket = serverSocket.accept();
                logArea.append("Client connected: " + clientSocket.getInetAddress() + "\n");
                // Handle client in a new thread
                new ClientHandler(clientSocket, this).start();
            }
        } catch (IOException e) {
            logArea.append("Error starting server: " + e.getMessage() + "\n");
        }
    }

    private void stopServer() {
        logArea.append("Stopping server...\n");
        isRunning = false;
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException e) {
            logArea.append("Error stopping server: " + e.getMessage() + "\n");
        }
    }

    // Method to append log messages
    public void appendLog(String message) {
        logArea.append(message + "\n");
    }

    private static class ClientHandler extends Thread {
        private Socket clientSocket;
        private Server server;

        public ClientHandler(Socket socket, Server server) {
            this.clientSocket = socket;
            this.server = server;
        }

        public void run() {
            // Implement client handling logic here
            // For example, read input from the client and append it to the server log
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    server.appendLog("Client says: " + inputLine);
                    // Add logic to handle client disconnection and "finalize" behavior
                }
            } catch (IOException e) {
                server.appendLog("Error handling client: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Server();
            }
        });
    }
}
