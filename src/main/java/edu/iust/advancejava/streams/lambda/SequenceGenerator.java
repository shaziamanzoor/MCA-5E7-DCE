package edu.iust.advancejava.streams.lambda;

import java.util.ArrayList;
import java.util.Collection;


//SequenceGenerator is a class which demonstrates various ways of generating a sequence:
// using step,
// using step logic (by implementing the function in functional interface),
// using Generator (using lambda expression instead of implementing the function of functional interface)

public class SequenceGenerator {

    public static Collection<Integer> integers(int min, int max, int step) {
        Collection<Integer> integers = new ArrayList<>();
        for (int i = min; i < max; i += step)
            integers.add(i);
        return integers;

    }

    public static Collection<Integer> integersWithStepLogic(int min, int max, StepLogic logic) {
        Collection<Integer> integers = new ArrayList<>();
        for (int i = min; i < max; i = logic.increment(i))
            integers.add(i);
        return integers;
    }

    public static <T> Collection<T> genericSeriesWithLogic(T seed, Generator<T> generator) {
        Collection<T> series = new ArrayList<>();
        series.add(seed);
        do {
            T newValue = generator.nextValue(seed);
            if (newValue == null)
                return series;
            series.add(newValue);
            seed = newValue;
        } while (true);
    }

}









