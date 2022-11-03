import java.util.Map;

public class PageEntry implements Comparable<PageEntry> {
    private final String pdfName;
    private final int page;
    private final int count;

    public PageEntry(Map.Entry<GroupingKey, Integer> entry) {
        this.pdfName = entry.getKey().getIndexKey().getPdfName();
        this.page = entry.getKey().getIndexKey().getPage();
        this.count = entry.getValue();
    }

    @Override
    public int compareTo(PageEntry o) {
        return Integer.compare(this.count, o.count);
    }

    @Override
    public String toString() {
        return "PageEntry{" +
                "pdfName='" + pdfName + '\'' +
                ", page=" + page +
                ", count=" + count +
                '}';
    }
}
