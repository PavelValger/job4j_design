package ru.job4j.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(9000)) {
            while (!server.isClosed()) {
                Socket socket = server.accept();
                try (OutputStream out = socket.getOutputStream();
                     BufferedReader in = new BufferedReader(
                             new InputStreamReader(socket.getInputStream()))) {
                    out.write("HTTP/1.1 200 OK\r\n\r\n".getBytes());
                    String string = in.readLine();
                    String[] divider = string.split(" ", 3);
                    String[] message = divider[1].split("=", 2);
                    if (!"Exit".equals(message[1])) {
                        out.write(message[1].getBytes());
                        for (String line = string;
                             line != null && !line.isEmpty(); line = in.readLine()) {
                            System.out.println(line);
                        }
                    } else {
                        out.write("Bye, dear friend.".getBytes());
                        server.close();
                        System.out.println("The server has shut down");
                    }
                    out.flush();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
