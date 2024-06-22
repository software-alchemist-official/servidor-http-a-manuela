package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private final Socket client;

    public ClientHandler(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        try (BufferedReader in = getReader(); PrintWriter out = getWriter()) {

            String line;
            StringBuilder request = new StringBuilder();

            while (!(line = in.readLine()).isEmpty()) { // <-- Mientras la línea no esté vacía, aún hay request.
                request.append(line).append("\n"); // <-- Asigno formato.
            }

            System.out.println("Petición recibida:");
            System.out.println(request);

            // Construir la respuesta HTTP
            String responseBody = "<html><body><h1>HOLA MUNDO!</h1></body></html>";
            String responseHeaders = "HTTP/1.1 200 OK\r\n" +
                    "Content-Type: text/html\r\n" +
                    "Content-Length: " + responseBody.length() + "\r\n" +
                    "Connection: close\r\n\r\n";
            String response = responseHeaders + responseBody;

            // Enviar la respuesta al cliente
            out.write(response);
            out.flush();

            //Para testear probar en el navegador -> localhost:8080


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(client.getInputStream()));
    }

    private PrintWriter getWriter() throws IOException {
        return new PrintWriter(client.getOutputStream());
    }
}
