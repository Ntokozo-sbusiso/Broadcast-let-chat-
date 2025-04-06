package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


public class Client {
    public static void main(String[] args) {
        String hostname = "localhost";
        int port = 1234;
        Scanner scanner = new Scanner(System.in);
        try (Socket socket = new Socket(hostname, port)) {
            // Send message to server
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
            System.out.println("Say something: ");
            String inp = scanner.nextLine();
            output.println(inp);

            // Receive reply
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String serverReply = input.readLine();
            System.out.println("Server replied: " + serverReply);

            // Close resources
            output.close();
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
