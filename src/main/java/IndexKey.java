import java.util.Objects;

public class IndexKey {
    private final String word;
    private final String pdfName;
    private final int page;
    private final GroupingKey groupingKey;

    public IndexKey(String word, String pdfName, int page) {
        this.word = word;
        this.pdfName = pdfName;
        this.page = page;
        this.groupingKey = new GroupingKey(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IndexKey indexKey = (IndexKey) o;
        return page == indexKey.page && word.equals(indexKey.word) && pdfName.equals(indexKey.pdfName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(word, pdfName, page);
    }

    public String getWord() {
        return word;
    }

    public String getPdfName() {
        return pdfName;
    }

    public int getPage() {
        return page;
    }

    public GroupingKey getGroupingKey() {
        return groupingKey;
    }
}
