package codingdojo;

public class ComparableUtils {
    public static <T extends Comparable<T>> boolean isALessThanOrEqualToB(T a, T b) {
        return a.compareTo(b) <= 0;
    }

    public static <T extends Comparable<T>> boolean isALessThanB(T a, T b) {
        return a.compareTo(b) < 0;
    }
}
