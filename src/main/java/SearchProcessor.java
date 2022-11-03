import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

public class SearchProcessor {
    private final SearchEngine searchEngine;
    private final Gson gson;

    public SearchProcessor(SearchEngine searchEngine) {
        this.searchEngine = searchEngine;
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
    }

    public String process(String requestText) {
        List<PageEntry> result = searchEngine.search(requestText);
        return gson.toJson(result);
    }
}

