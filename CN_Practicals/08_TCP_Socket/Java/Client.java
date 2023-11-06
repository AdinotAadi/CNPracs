// Program for TCP Client
import java.io.*;
import java.net.*;

public class Client {
    // Initialize the socket and I/O streams
    private Socket socket = null;
    private DataInputStream input = null;
    private DataOutputStream out = null;

    // Constructor to put IP address and Port:
    public Client(String address, int port) {
        // Try ot establish the communication
        try {
            socket = new Socket(address, port);
            System.out.println("Connected!");

            // Take input from the terminal
            input = new DataInputStream(System.in);

            // Send output to the socket
            out = new DataOutputStream(socket.getOutputStream());
        } catch (UnknownHostException u) {
            System.out.println(u);
            return;
        } catch (IOException i) {
            System.out.println(i);
            return;
        }

        // String to read message from input
        String line = "";

        // Keep reading until "!#End" is recognized
        while(!line.equals("!#End")) {
            try {
                line = input.readLine();
                out.writeUTF(line);
            } catch (IOException i){
                System.out.println(i);
            }
        }

        // Close the connection
        try {
            input.close();
            out.close();
            socket.close();
        } catch (IOException i) {
            System.out.println(i);
        }
    }

    public static void main(String args[]) {
        Client client = new Client("127.0.0.1", 1234);
    }
}