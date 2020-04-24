package edu.iust.advancejava.streams.problems;

import java.util.stream.Stream;

/**
 * Calculation of Pi using Leibniz method.
 *
 * 1/1*3 + 1/5*7 + 1/9*11 this is the series we are generating
 */
public class LeibnizPi {

    public static double calculate(int terms){
        double sum = Stream.iterate(new Pair<>(1, 0.0),
                pair -> new Pair<>(pair.getFirst() + 4, 1.0 / ((pair.getFirst() + 2) * pair.getFirst())))
                .limit(terms).mapToDouble(Pair::getSecond).sum();

        return sum * 8;
    }
}
