public class Config {
    // Define various configuration properties
    public static final int PORT = 6666;
    public static final String SERVER_IP = "127.0.0.1";
    public static final String PROTOCOL_SEPARATOR = "#";
    public static final String PROTOCOL_END = "P0";
    public static final String PROTOCOL_SENDMODEL = "P1";
    public static final String PROTOCOL_RECVMODEL = "P2";
    public static final String PROTOCOL_RESULT = "P3";
    // Add other configuration properties as needed
    
    public String createEndMessage(String clientId) {
        return clientId + Config.PROTOCOL_SEPARATOR + Config.PROTOCOL_END;
    }

    public String createSendModelMessage(String clientId, String gameConfig) {
        return clientId + Config.PROTOCOL_SEPARATOR + Config.PROTOCOL_SENDMODEL + 
               Config.PROTOCOL_SEPARATOR + gameConfig;
    }

    public String createReceiveModelMessage(String clientId) {
        return clientId + Config.PROTOCOL_SEPARATOR + Config.PROTOCOL_RECVMODEL;
    }

    public String createResultMessage(String clientId, String username, int points, int time) {
        return clientId + Config.PROTOCOL_SEPARATOR + Config.PROTOCOL_RESULT +
               Config.PROTOCOL_SEPARATOR + username + Config.PROTOCOL_SEPARATOR + 
               points + Config.PROTOCOL_SEPARATOR + time;
    }

    public void parseMessage(String message) {
        String[] parts = message.split(Config.PROTOCOL_SEPARATOR);
        String clientId = parts[0];
        String protocolId = parts[1];
        
        switch (protocolId) {
            case Config.PROTOCOL_END:
                // Handle ending execution
                break;
            case Config.PROTOCOL_SENDMODEL:
                // Handle sending game configuration
                String gameConfig = parts[2];
                break;
            case Config.PROTOCOL_RECVMODEL:
                // Handle receiving game configuration
                break;
            case Config.PROTOCOL_RESULT:
                // Handle receiving results
                String username = parts[2];
                int points = Integer.parseInt(parts[3]);
                int time = Integer.parseInt(parts[4]);
                break;
            // ... other cases ...
        }
    }

}
