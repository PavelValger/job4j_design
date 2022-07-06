package ru.job4j.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class EchoServer {
    public static void main(String[] args) throws IOException {
        try (ServerSocket server = new ServerSocket(9000)) {
            while (!server.isClosed()) {
                List<String> message = new ArrayList<>(1);
                Socket socket = server.accept();
                try (OutputStream out = socket.getOutputStream();
                     BufferedReader in = new BufferedReader(
                             new InputStreamReader(socket.getInputStream()))) {
                    out.write("HTTP/1.1 200 OK\r\n\r\n".getBytes());
                    for (String str = in.readLine();
                         str != null && !str.isEmpty(); str = in.readLine()) {
                        System.out.println(str);
                        if (message.isEmpty()) {
                            message.add(str);
                        }
                    }
                    if (message.get(0).contains("=Bye")) {
                        server.close();
                        System.out.println("The server has shut down");
                    }
                    out.flush();
                }
            }
        }
    }
}
