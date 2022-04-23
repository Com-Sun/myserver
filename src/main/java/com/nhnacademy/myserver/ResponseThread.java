package com.nhnacademy.myserver;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ResponseThread implements Runnable{
    Socket socket;
    DataInputStream in;

    ResponseThread (Socket socket) {
        this.socket = socket;
        try {
            in = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {

    }
}
