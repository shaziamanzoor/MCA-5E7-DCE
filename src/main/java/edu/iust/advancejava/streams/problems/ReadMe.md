## Collectors
What is a Collector?
Once we are done with the processing of a stream, we need to collect the result which may be a list, e.g. in case we have applied filter or map, or it may be an aggregate value e.g. sum, count, average etc. This can be accomplished with the help of collector. A collector plays the part of a reducer, but more efficient. 

Here are few simple examples.

[`SimpleCollectorProblems#calculateTotalSalaries`]() is a helper method that takes a Stream of Salaries and returns a Stream of  gross salaries(ie basic pay + allowances). 

``` java

public static Stream<Double> calculateGrossSalaries(Stream<Salary> salaries){
        return salaries.map(s -> s.getBasic() + s.getAllowances().orElse(0.0));

    }
    
```

[`SimpleCollectorProblems#calculateAverageSalary`]() method takes a Stream of Salaries and returns the average of salaries using `averagingDouble()`, a method of Collectors class. 

```java

public static double calculateAverageSalary(Stream<Salary> salary){
       return calculateTotalSalaries(salary).collect(Collectors.averagingDouble(x -> x));

    }
    
```

[`SimpleCollectorProblems#calculateAggregateSalary`]() method takes a Stream of Salary and returns the aggregate of salaries using `summingDouble()` a method of Collectors class.

```java

public static double calculateAggregateSalary(Stream<Salary> salary){
        return calculateTotalSalaries(salary).collect(Collectors.summingDouble(x -> x));

    }

```

## Digging Deeper
The Stream API provides a number of ways to create a new collector. Let's use one of these to create two Collectors. 

[`CollectionProblems#groupBy`]() takes an interface Function which accepts two types`<T, U>` and returns a Collector. The Collector interface defines a set of methods which are used during the reduction process. In this example, its takes a supplier of type `T`, an accumulator ie a `Map which takes type U and a List of type T`  and a finisher which is also a `Map which takes type U and a List of type T` . The groupBy allows us to group the result of methods accoring to the argument supplied.

```java

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
    
```

[`CollectionProblems#frequencies`]() returns the frequency of a value in a Hashmap.The Method takes no parameters and returns a collector.

```java

public static <T> Collector<T, Map<T, Integer>, Map<T, Integer>> frequencies(){
        return Collector.of(
                HashMap::new,
                (acc, x) -> {
                    int count = acc.getOrDefault(x, 0);
                    acc.put(x, count + 1);
                },
                (xs, ys) -> {
                    xs.forEach((key, value) -> ys.merge(key, value, (xValue, yValue) -> xValue + yValue));
                    return xs;
                });
    }

}

```

## The few classical problems of recurrsion using streams. 
The following two problems are frequently used in many  programming language courses. Let us revisit them and try solving them using Streams.

[`Fibonacci#stream`]() generates an infinite  fibonacci stream by usuing a Class Pair.

```java

 public static Stream<Long> stream(){
        return Stream.iterate(new Pair<>(0L, 1L),
                pair ->  new Pair<>(pair.getSecond(), pair.getFirst() + pair.getSecond()))
                .map(Pair::getFirst);
    }
    
```

[`Factorial#stream`]() generates an infinite  series of factorials .
 
```java

public static Stream<Long> stream(){
        return Stream.iterate(new Pair<>(1L, 1L),
                pair -> new Pair<>(pair.getFirst() + 1, pair.getSecond() * (pair.getFirst() + 1)))
                .map(Pair::getSecond);
    }
    
```

[`LeibnizPi#calculate`]()Generateas LeibnizPi series for calculating the value of Pi


```java
 
public class LeibnizPi {

    public static double calculate(int terms){
        double sum = Stream.iterate(new Pair<>(1, 0.0),
                pair -> new Pair<>(pair.getFirst() + 4, 1.0 / ((pair.getFirst() + 2) * pair.getFirst())))
                .limit(terms).mapToDouble(Pair::getSecond).sum();

        return sum * 8;
    }
}

```

##OPTIONAL
What is Optional?


[`OptionalExamples#max`]() takes two parameter of type `T` and returns an Optional which takes  type `T which extends Comparable  interface which inturn takes type T as a parameter`.The functional allows us to compare the values and return the result using Optional.  

```Java

public static <T extends Comparable<T>> Optional<T> max(T x, T y){
        return (x == null || y == null) ? Optional.empty(): Optional.of(x.compareTo(y) > 0 ? x : y);
    }
    
```

[`OptionalExamples#find`]() takes a stream of type `T` and a `Functional interface Predicate` from  java's API and returns Optional.

```java

public static <T> Optional<T> find(Stream<T> xs, Predicate<T> predicate){
        return xs.filter(predicate).findFirst();
    }
    
```

[`OptionalExamples#findInEither`]()takes two streams and a `Predicate `it compares the first value of the stream  ` xs` returned by filter and stores it in match , if no value is present it then calls find with `ys` and a predicate.

```java

public static <T> Optional<T> findInEither(Stream<T> xs, Stream<T> ys, Predicate<T> predicate){
        Optional<T> match = find(xs, predicate);
        return match.isPresent() ? match : find(ys, predicate);
    }
    
```