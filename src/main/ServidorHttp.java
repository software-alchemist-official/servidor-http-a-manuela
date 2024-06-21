package main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

public class ServidorHttp {

    private static final Logger LOG = Logger.getLogger(ServidorHttp.class.getName());
    private static final int PORT = 8080;

    public void startServer() {
        try {

            ServerSocket serverSocket = new ServerSocket(PORT);
            LOG.info("Servidor iniciado en el puerto: " + PORT);

            while(!serverSocket.isClosed()) {
                Socket client = serverSocket.accept();
                new Thread(new ClientHandler(client)).start();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
