package test2021.sockets;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    private final Socket socket;

    public Client(Socket socket) {
        this.socket = socket;
    }

    public Socket getSocket() {
        return socket;
    }

    public void shutdown() {
        try {
            socket.close();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void awaitCommunication() {
        System.out.println("Awaiting Communication from Server...");
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            String in;
            String input;
            BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

            while ((in = reader.readLine()) != null) {
                if (in.equalsIgnoreCase("::")) {
                    input = stdin.readLine();
                    PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
                    writer.write(input);
                    writer.println();
                    break;
                } else {
                    System.out.println(in);
                }
            }

            System.out.println(reader.readLine());

            while ((input = stdin.readLine()) != null) {
                PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
                if (input.equalsIgnoreCase("quit")) {
                    writer.write(input);
                    writer.println();
                    break;
                }
            }

            System.out.println(reader.readLine());
        } catch (IOException e) {
            shutdown();
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        try (Socket client = new Socket(InetAddress.getLocalHost(), 4999)) {
            System.out.println("Starting Client...");
            Client c = new Client(client);
            c.awaitCommunication();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
