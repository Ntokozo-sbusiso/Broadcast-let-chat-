package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;


public class ClientHandler implements Runnable {
    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;
    private String username;
    private List<ClientHandler> clients;

    public ClientHandler(Socket socket, List<ClientHandler> clients) throws IOException {
        this.socket = socket;
        this.clients = clients;
        this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.output = new PrintWriter(socket.getOutputStream(), true);
    }

    @Override
    public void run() {
        try {
            // Ask for a username
            output.println("Welcome! Enter your username:");
            username = input.readLine();

            broadcast("🟢 " + username + " has joined the chat!");

            String message;
            while ((message = input.readLine()) != null) {
                if (message.equalsIgnoreCase("exit") || message.equalsIgnoreCase("quit")) {
                    broadcast("🔴 " + username + " has left the chat.");
                    break;
                }

                broadcast("💬 " + username + ": " + message);
            }

        } catch (IOException e) {
            System.out.println("❌ Client error: " + e.getMessage());
        } finally {
            try {
                clients.remove(this);
                socket.close();
                input.close();
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void broadcast(String message) {
        synchronized (clients) {
            for (ClientHandler client : clients) {
                if (client != this) {
                    client.output.println(message);
                }
            }
        }
    }
}
