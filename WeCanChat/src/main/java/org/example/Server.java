package org.example;
import java.io.*;
import java.net.*;
import java.net.ServerSocket;
import java.util.*;


public class Server {
    // Keep track of all connected clients
    static List<ClientHandler> clients = Collections.synchronizedList(new ArrayList<>());

    public static void main(String[] args) {
        int port = 1234;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("🚀 Chatroom server started on port " + port);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("👤 New client attempting to connect...");

                ClientHandler clientHandler = new ClientHandler(socket, clients);
                clients.add(clientHandler);
                new Thread(clientHandler).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
