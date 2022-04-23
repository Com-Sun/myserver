package com.nhnacademy.myserver;

import com.nhnacademy.myserver.domain.Request;
import com.nhnacademy.myserver.domain.Response;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

public class ResponseThread implements Runnable{
    Socket socket;
    DataInputStream in;
    PrintStream out;

    ResponseThread (Socket socket) {
        this.socket = socket;
        try {
            in = new DataInputStream(socket.getInputStream());
            out = new PrintStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            byte[] byteArr = new byte[4096];
            int readByteCount = in.read(byteArr);
            String receivedHttpRequest = new String(byteArr, 0, readByteCount, "UTF-8");
            String[] httpRequest; // 0번: 헤더, 1번: 바디
            httpRequest = receivedHttpRequest.split("\r\n\r\n");
            Request request = new Request(httpRequest);

            if (httpRequest[0].contains("GET")) {
                Response response = new Response(request, socket.getInetAddress().toString().replace("/", ""));
                System.out.println(response.getResponseHeader());
                out.println(response.getResponseHeader());
                out.println(response.getResponseBody());
            }

            if (httpRequest[0].contains("POST")){
                System.out.println();
                System.out.println(httpRequest[1]);
            }
        } catch (IOException e) {

        }
    }
}
