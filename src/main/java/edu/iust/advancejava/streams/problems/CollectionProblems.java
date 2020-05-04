package edu.iust.advancejava.streams.problems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;

public class CollectionProblems {

    public static <U, T> Collector<T, Map<U, List<T>>, Map<U, List<T>>> groupBy(Function<T, U> grouper){
        return Collector.of(
                HashMap::new,
                (acc, x) -> {
                    U groupKey = grouper.apply(x);
                    List<T> group = acc.getOrDefault(groupKey, new ArrayList<>());
                    group.add(x);
                    acc.put(groupKey, group);
                },
                (xs, ys) -> {
                    xs.forEach((key, value) -> ys.merge(key, value, (xValue, yValue) -> {
                        xValue.addAll(yValue);
                        return xValue;
                    }));

                    return ys;
                });
    }

    public static <T> Collector<T, Map<T, Integer>, Map<T, Integer>> frequencies(){
        return Collector.of(
                HashMap::new,
                (acc, x) -> acc.merge(x, (acc.getOrDefault(x,0)) + 1, (v1, v2)-> v2),
                (xs, ys) -> {
                    xs.forEach((key, value) -> ys.merge(key, value, (xValue, yValue) -> xValue + yValue));
                    return ys;
                });
    }

}
