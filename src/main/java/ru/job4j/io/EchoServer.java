package ru.job4j.io;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EchoServer {
    private static final Logger LOG = LoggerFactory.getLogger(EchoServer.class.getName());
    private static int count = 1;

    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(9000)) {
            System.out.println("Server started");
            while (!server.isClosed()) {
                Socket socket = server.accept();
                try (PrintWriter out = new PrintWriter(
                        new BufferedWriter(
                                new OutputStreamWriter(socket.getOutputStream())));
                     BufferedReader in = new BufferedReader(
                             new InputStreamReader(socket.getInputStream()))) {
                    out.write("HTTP/1.1 200 OK\r\n\r\n");
                    String string = in.readLine();
                    if (string.contains("=")) {
                        String[] divider = string.split(" ", 3);
                        String[] message = divider[1].split("=", 2);
                        if (!"Exit".equals(message[1])) {
                            System.out.printf("The user %s has connected%n", count++);
                            out.write(String.format("%s\n", message[1]));
                            for (String line = string;
                                 line != null && !line.isEmpty(); line = in.readLine()) {
                                System.out.println(line);
                            }
                        } else {
                            out.write("Bye, dear friend.\n");
                            server.close();
                            System.out.println("The server has shut down");
                        }
                        out.flush();
                    }
                }
            }
        } catch (IOException e) {
            System.out.printf("User %s entered incorrect parameters\n", count);
            LOG.error("IOException in log example", e);
        }
    }
}
