package edu.iust.advancejava.streams.problems;

import java.util.function.Function;
import java.util.stream.Stream;

public class Fibonacci {

    public static Stream<Long> stream(){
        return Stream.iterate(new Pair<>(0L, 1L),
                pair ->  new Pair<>(pair.getSecond(), pair.getFirst() + pair.getSecond()))
                .map(Pair::getFirst);
    }

    public static Stream<Integer> iterateExample(){
        return Stream.iterate(1, n -> n + 1);
    }
}
