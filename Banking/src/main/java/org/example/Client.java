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


        try (Socket socket = new Socket(hostname, port)) {
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

            // listen for message from server
            Thread listener = new Thread(() ->{
                String serverMessage;
                try{
                    while ((serverMessage = input.readLine()) != null){
                        System.out.println(serverMessage);
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
            });

            listener.start();

            // send message to server
            String userMessage;
            while((userMessage = userInput.readLine()) != null){
                output.println(userMessage);
                if (userMessage.equalsIgnoreCase("exit") || userMessage.equalsIgnoreCase("quit")){
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
