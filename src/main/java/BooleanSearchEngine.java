import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class BooleanSearchEngine implements SearchEngine {
    private final Map<IndexKey, Integer> index = new HashMap<>();
    private final Set<String> stopWords = new HashSet<>();

    public BooleanSearchEngine(File pdfsDir, File stopWordsFile) throws IOException {
        indexFiles(pdfsDir);
        loadStopWords(stopWordsFile);
    }

    private void loadStopWords(File file) throws IOException {
        try (BufferedReader in = new BufferedReader(new FileReader(file))) {
            in.lines().forEach(line -> {
                if (!line.isEmpty()) {
                    stopWords.add(line.trim().toLowerCase());
                }
            });
        }
    }

    private void indexFiles(File pdfsDir) throws IOException {
        for (File file : Objects.requireNonNull(pdfsDir.listFiles())) {
            var doc = new PdfDocument(new PdfReader(file));
            String fileName = file.getName();
            for (int i = 1; i <= doc.getNumberOfPages(); i++) {
                indexPage(doc, i, fileName);
            }
            doc.close();
        }
    }

    private void indexPage(PdfDocument doc, int pageNumber, String fileName) {
        var text = PdfTextExtractor.getTextFromPage(doc.getPage(pageNumber));
        var words = text.split("\\P{IsAlphabetic}+");
        for (var word : words) {
            if (word.isEmpty()) {
                continue;
            }
            IndexKey key = new IndexKey(word.toLowerCase(), fileName, pageNumber);
            index.put(key, index.getOrDefault(key, 0) + 1);
        }
    }

    @Override
    public List<PageEntry> search(String word) {
        List<String> words = Arrays.asList(word.trim().toLowerCase().split(" "));

        return index.entrySet().stream()
                .filter(entry -> words.contains(entry.getKey().getWord()))
                .filter(entry -> !stopWords.contains(entry.getKey().getWord()))
                .collect(Collectors.groupingBy(entry -> entry.getKey().getGroupingKey(), Collectors.summingInt(Map.Entry::getValue)))
                .entrySet().stream()
                .map(PageEntry::new)
                .sorted(Collections.reverseOrder())
                .collect(Collectors.toList());
    }
}