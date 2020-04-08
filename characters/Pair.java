package characters;

import java.util.Objects;

public class Pair<First, Second> {
    private First first;
    private Second second;

    public Pair(final First first, final Second second) {
        this.first = first;
        this.second = second;
    }
    /** */
    public void setFirst(final First first) {
        this.first = first;
    }
    /** */
    public void setSecond(final Second second) {
        this.second = second;
    }
    /** */
    public First getFirst() {
        return first;
    }
    /** */
    public Second getSecond() {
        return second;
    }
    /** */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Pair pair = (Pair) o;

        if (!Objects.equals(first, pair.first)) {
            return false;
        }
        return Objects.equals(second, pair.second);
    }
    /** */
    @Override
    public int hashCode() {
        int result = first != null ? first.hashCode() : 0;
        result = result + (second != null ? second.hashCode() : 0);
        return result;
    }

}
