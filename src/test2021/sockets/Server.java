package test2021.sockets;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private final ServerSocket serverSocket;
    private static final ExecutorService executorService = Executors.newCachedThreadPool(Executors.defaultThreadFactory());

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void shutdown() {
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public static void main(String[] args) {
        try (
                ServerSocket ss = new ServerSocket(4999);
        ) {
            System.out.println("Starting Server...");
            Server server = new Server(ss);
            while (!server.getServerSocket().isClosed()) {
                Socket socket = server.getServerSocket().accept();
                System.out.println("Accepting new Client!");
                executorService.execute(new ClientHandler(socket));
            }
            System.out.println("Shutting down Server.");
            server.shutdown();
            executorService.shutdown();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
