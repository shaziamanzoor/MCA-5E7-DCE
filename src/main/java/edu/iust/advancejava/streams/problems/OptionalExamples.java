package edu.iust.advancejava.streams.problems;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class OptionalExamples {

    public static <T extends Comparable<T>> Optional<T> max(T x, T y){
        return (x == null || y == null) ? Optional.empty(): Optional.of(x.compareTo(y) > 0 ? x : y);
    }

    public static <T> Optional<T> find(Stream<T> xs, Predicate<T> predicate){
        return xs.filter(predicate).findFirst();
    }

    public static <T> Optional<T> findInEither(Stream<T> xs, Stream<T> ys, Predicate<T> predicate){
        Optional<T> match = find(xs, predicate);
        return match.isPresent() ? match : find(ys, predicate);
    }

}
