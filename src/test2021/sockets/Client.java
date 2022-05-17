package test2021.sockets;

import java.io.IOException;
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

    public static void main(String[] args) {
        try (Socket client = new Socket(InetAddress.getLocalHost(), 5000);) {
            Client c = new Client(client);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
