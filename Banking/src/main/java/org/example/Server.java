package org.example;
import java.io.*;
import java.net.*;
import java.net.ServerSocket;
import java.util.Scanner;


public class Server {
    public static void main(String[] args) {
        int port = 1234;


        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port " + port);

            Socket socket = serverSocket.accept();
            System.out.println("Client connected");

            // Input from client
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String clientMessage = input.readLine();
            System.out.println("Received from client: " + clientMessage);

            // Output to client
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
            output.println("Message received: " + clientMessage);

            // Close resources
            input.close();
            output.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
