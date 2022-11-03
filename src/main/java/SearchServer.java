import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SearchServer {
    private final int port;
    private final SearchProcessor processor;

    public SearchServer(int port, SearchProcessor processor) {
        this.port = port;
        this.processor = processor;
    }

    public void start() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started!");

            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
                ) {
                    System.out.println("New connection accepted, port " + clientSocket.getPort() + ".");

                    String request = in.readLine();
                    String response = processor.process(request);
                    out.println(response);

                    System.out.println("Request:\n" + request);
                    System.out.println("Response:\n" + response);
                }
            }
        }
    }

}
