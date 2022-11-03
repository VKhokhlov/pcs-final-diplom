import java.io.*;
import java.net.Socket;

public class SearchClient {
    private static final int PORT = 8989;
    private static final String HOST = "localhost";
    private static final String FILE = "requests.txt";

    public static void main(String[] args) {
        try (BufferedReader in = new BufferedReader(new FileReader(FILE))
        ) {
            in.lines().forEach(SearchClient::send);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void send(String line) {
        if (line.isEmpty() || line.startsWith("#")) {
            return;
        }

        try (Socket clientSocket = new Socket(HOST, PORT);
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
        ) {
            System.out.println("Request:\n" + line);
            out.println(line);
            System.out.println("Response:\n" + read(in) + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String read(BufferedReader in) throws IOException {
        StringBuilder stringBuilder = new StringBuilder().append(in.readLine());
        while (in.ready()) {
            stringBuilder.append("\n").append(in.readLine());
        }
        return stringBuilder.toString();
    }
}
