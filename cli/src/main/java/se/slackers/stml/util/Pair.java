package se.slackers.stml.util;

public class Pair<A, B> {
    private final A first;
    private final B second;

    public static <U, V> Pair<U, V> of(U a, V b) {
        return new Pair<>(a, b);
    }

    public Pair(A first, B second) {
        this.first = first;
        this.second = second;
    }

    public A getFirst() {
        return first;
    }

    public B getSecond() {
        return second;
    }
}
