package server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server_Storage implements Server {


    public Server_Storage() {
        try {
            System.out.println("Server is starting up...");
            ServerSocket serverSocket = new ServerSocket(8811);
            System.out.println("Server is started up...");
            while (true) {
                System.out.println("Server is listening for clients...");
                Socket socket = serverSocket.accept();
                System.out.println("Client accepted: " + socket);
                new ClientHandler(this, socket);
            }
        } catch (IOException e) {
            throw new RuntimeException("SWW", e);
        }
    }

}
