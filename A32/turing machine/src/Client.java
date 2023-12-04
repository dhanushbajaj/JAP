import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

public class Client extends JFrame {
    private JTextField userField;
    private JTextField serverField;
    private JTextField portField;
    private JTextField tmField;
    private JTextArea logArea;
    private JButton connectButton;
    private JButton endButton;
    private JButton validateButton;
    private JButton sendButton;
    private JButton receiveButton;
    private JButton runButton;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public Client() {
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        setTitle("TURING MACHINE - CLIENT");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLayout(new BorderLayout());

        // Top Panel
        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("User:"));
        userField = new JTextField(6);
        topPanel.add(userField);
        topPanel.add(new JLabel("Server:"));
        serverField = new JTextField(10);
        serverField.setText("localhost");
        topPanel.add(serverField);
        topPanel.add(new JLabel("Port:"));
        portField = new JTextField(5);
        portField.setText("12345");
        topPanel.add(portField);
        connectButton = new JButton("Connect");
        endButton = new JButton("End");
        topPanel.add(connectButton);
        topPanel.add(endButton);

        // TM Panel
        JPanel tmPanel = new JPanel();
        tmPanel.add(new JLabel("TM:"));
        tmField = new JTextField(20);
        tmPanel.add(tmField);
        validateButton = new JButton("Validate");
        sendButton = new JButton("Send");
        receiveButton = new JButton("Receive");
        runButton = new JButton("Run");
        tmPanel.add(validateButton);
        tmPanel.add(sendButton);
        tmPanel.add(receiveButton);
        tmPanel.add(runButton);

        // Log Area
        logArea = new JTextArea();
        logArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logArea);

        add(topPanel, BorderLayout.NORTH);
        add(tmPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        // Action Listeners
        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int port = Integer.parseInt(portField.getText());
                String server = serverField.getText();
                startConnection(server, port);
            }
        });

        endButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopConnection();
            }
        });

        // Add action listeners for validate, send, receive, run buttons...

        setVisible(true);
    }

    private void startConnection(String ip, int port) {
        try {
            clientSocket = new Socket(ip, port);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            logArea.append("Connected to server at " + ip + " on port " + port + "\n");
        } catch (IOException e) {
            logArea.append("Could not connect to server: " + e.getMessage() + "\n");
        }
    }

    private void stopConnection() {
        try {
            if (clientSocket != null) {
                clientSocket.close();
                logArea.append("Connection closed.\n");
            }
        } catch (IOException e) {
            logArea.append("Error when closing the connection: " + e.getMessage() + "\n");
        }
    }

    private void connectToServer() {
        new Thread(() -> {
            try {
                Socket socket = new Socket(Config.SERVER_IP, Config.PORT);
                // Further processing...
            } catch (IOException e) {
                // Executed on the Event Dispatch Thread to avoid threading issues with Swing components
                SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(this,
                    "Error connecting to server: " + e.getMessage(),
                    "Connection Error",
                    JOptionPane.ERROR_MESSAGE));
            }
        }).start();
    }

    // Example of a thread-safe way to update GUI from a network operation thread
    private void appendLogSafe(String text) {
        SwingUtilities.invokeLater(() -> {
            logArea.append(text + "\n");
        });
    }
    // Add methods for validate, send, receive, run...

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Client();
            }
        });
    }
}
