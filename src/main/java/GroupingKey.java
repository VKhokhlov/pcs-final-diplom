import java.util.Objects;

public class GroupingKey {
    private final IndexKey indexKey;

    public GroupingKey(IndexKey indexKey) {
        this.indexKey = indexKey;
    }

    public IndexKey getIndexKey() {
        return indexKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupingKey that = (GroupingKey) o;
        return indexKey.getPage() == that.indexKey.getPage() && indexKey.getPdfName().equals(that.indexKey.getPdfName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(indexKey.getPdfName(), indexKey.getPage());
    }
}
