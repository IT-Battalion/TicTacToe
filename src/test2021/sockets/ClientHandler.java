package test2021.sockets;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("Sending Data...");
            writer.write("Successful connected!");
            writer.println();
            writer.write("What is your name?");
            writer.println();
            writer.write("::");
            writer.println();

            System.out.println("Receiving Answer...");
            String answer;
            while ((answer = reader.readLine()) != null) {
                if (answer.equalsIgnoreCase("quit")) {
                    System.out.println("Client rage quitted!");
                    writer.write("Do not rage quit! That is not nice!");
                    writer.println();
                    shutdown();
                } else {
                    System.out.println(answer);
                    writer.write("Cool name " + answer + ".");
                    writer.println();
                }
            }
        } catch (IOException e) {
            shutdown();
        }
    }

    public void shutdown() {
        try {
            this.socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
