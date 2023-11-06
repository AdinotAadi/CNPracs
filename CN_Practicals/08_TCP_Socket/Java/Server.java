// Program for TCP Server
import java.io.*;
import java.net.*;

public class Server {
    // Initialize the socket and I/O streams
    private Socket socket = null;
    private ServerSocket server = null;
    private DataInputStream in = null;

    // Constructor for Port
    public Server(int port) {
        // Start Server and wait for the connection
        try {
            server = new ServerSocket(port);
            System.out.println("Server Started!");

            System.out.println("Waiting for a client.");
            socket = server.accept();
            System.out.println("Client Accepted!");

            // Take input from the client socket
            in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            String line = "";

            // Read message from the client until "!#End" is sent
            while (!line.equals("!#End")) {
                try {
                    line = in.readUTF();
                    System.out.println(line);
                } catch (IOException i) {
                    System.out.println(i);
                }
            }
            System.out.println("Closing the connection.");

            // Close the connection
            socket.close();
            in.close();
        }
        catch (IOException i) {
            System.out.println(i);
        }
    }

    public static void main(String args[]) {
        Server server = new Server(1234);
    }
}