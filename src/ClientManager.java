import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

public class ClientManager implements Runnable {
    private final Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;
    private boolean isRunning = true;
    Data data;

    public ClientManager(Socket clientSocket, Data data) throws IOException {
        this.clientSocket = clientSocket;
        this.data = data;
    }
    @Override
    public void run() {
        setupStreams();
        while (isRunning) {
            try {
                // Reading message from client
                String message = in.readLine();

                String[] receivedInfo = message.split(" ");
                    if (receivedInfo.length != 3) {
                        sendMessage("ERROR");
                        data.incrementIncorrectOperations();
                        continue;
                    }

                // Performing the operation
                if (receivedInfo[0].equals(Operators.ADDITION.getPrefix())) {
                    int result = Integer.parseInt(receivedInfo[1]) + Integer.parseInt(receivedInfo[2]);
                    sendMessage(String.valueOf(result));
                    data.incrementAddOperations();
                    data.addToSumOfResults(result);

                } else if (receivedInfo[0].equals(Operators.SUBTRACTION.getPrefix())) {
                    int result = Integer.parseInt(receivedInfo[1]) - Integer.parseInt(receivedInfo[2]);
                    sendMessage(String.valueOf(result));
                    data.incrementSubOperations();
                    data.addToSumOfResults(result);

                } else if (receivedInfo[0].equals(Operators.MULTIPLICATION.getPrefix())) {
                    int result = Integer.parseInt(receivedInfo[1]) * Integer.parseInt(receivedInfo[2]);
                    sendMessage(String.valueOf(result));
                    data.incrementMulOperations();
                    data.addToSumOfResults(result);

                } else if (receivedInfo[0].equals(Operators.DIVISION.getPrefix())) {
                    int result = Integer.parseInt(receivedInfo[1]) / Integer.parseInt(receivedInfo[2]);
                    sendMessage(String.valueOf(result));
                    data.incrementDivOperations();
                    data.addToSumOfResults(result);
                } else {
                    sendMessage("ERROR");
                    data.incrementIncorrectOperations();
                }

                // Store statistic data
                data.incrementComputedRequests();
            } catch (SocketException | NullPointerException e ) {
                closeConnection();
            } catch (NumberFormatException | ArithmeticException e) {
                sendMessage("ERROR");
                data.incrementIncorrectOperations();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Sets up the input and output streams for communication with the client.
     */
    private void setupStreams() {
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends a message to the client.
     *
     * @param message the message to be sent to the client
     */
    private void sendMessage(String message) {
        out.println(message);
    }

    /**
     * Closes the connection with the client and releases resources.
     */
    private void closeConnection() {
        try {
            isRunning = false;
            clientSocket.close();
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}