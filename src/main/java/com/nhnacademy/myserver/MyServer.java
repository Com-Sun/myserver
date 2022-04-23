package com.nhnacademy.myserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(80);
            System.out.println("serverSocket is bind at 80 port");

        } catch (IOException e) {
            e.printStackTrace();
        }
        
        try {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                ResponseThread responseThread = new ResponseThread(clientSocket);
                responseThread.run();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
