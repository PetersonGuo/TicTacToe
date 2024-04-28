
public class Pair<T1, T2> {
    public T1 first;
    public T2 second;

    public Pair(T1 first, T2 second) {
        this.first = first;
        this.second = second;
    }

    public Pair() {
        this(null, null);
    }

    public Pair(Pair<T1, T2> p) {
        this(p.first, p.second);
    }

    public Pair(Object o[]) {
        this((T1) o[0], (T2) o[1]);
    }

    public boolean equals(Object o) {
        if (o instanceof Pair) {
            Pair<?, ?> p = (Pair<?, ?>) o;
            return first.equals(p.first) && second.equals(p.second);
        }
        return false;
    }

    public String toString() {
        return "(" + first + ", " + second + ")";
    }
}
