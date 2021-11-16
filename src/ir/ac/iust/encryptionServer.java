package ir.ac.iust;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class encryptionServer {

    public static void main(String[] args) throws IOException {
        ServerSocket welcomeSocket = new ServerSocket(1999);
        System.out.println("Encryption Server is running on port 1999");

        while (true) {
            try {
                System.out.println("Waiting for Client");
                Socket connectionSocket = welcomeSocket.accept();
                System.out.println("Client accepted!");
                ObjectInputStream inputStream = new ObjectInputStream(connectionSocket.getInputStream());
                ObjectOutputStream outputStream = new ObjectOutputStream(connectionSocket.getOutputStream());
                new ClientHandler(connectionSocket, inputStream, outputStream).start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
