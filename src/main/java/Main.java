import java.io.File;

public class Main {
    static final String STOP_WORDS_FILE = "stop-ru.txt";
    static final String PDFS_DIR = "pdfs";
    static final int PORT = 8989;

    public static void main(String[] args) throws Exception {
        SearchEngine engine = new BooleanSearchEngine(new File(PDFS_DIR), new File(STOP_WORDS_FILE));
        SearchProcessor processor = new SearchProcessor(engine);
        SearchServer server = new SearchServer(PORT, processor);
        server.start();
    }
}