package edu.iust.advancejava.streams;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class Multiples {

    public static Stream<Integer> multiplesOf5(Stream<Integer> values){
        return values.filter(x -> x % 5 == 0);
    }

    public static Stream<Integer> multiplesOfN(Stream<Integer> values, int n){
        return values.filter(x -> x % n == 0);
    }

    public static int sumOfMultiplesOfN(Stream<Integer> values, int n){
        return values.filter(x -> x % n == 0).reduce(0, (result, x) -> result + x);
    }

    public static IntStream factors(int n){
        final int max = n < 5 ? n : n/2;
        return IntStream.range(1, max)
                .filter(x -> n % x == 0)
                .flatMap(x -> IntStream.of(x, n / x))
                .sorted();
    }

    public static boolean isPrime(int n) {
        return factors(n).count() == 2;
    }

    public static IntStream primeNumbers(){
        return IntStream.iterate(1, n -> n + 1)
                .filter(Multiples::isPrime);
    }

    public static List<Integer> primeFactors(int n){
        List<PrimeFactorIterator> result = Stream.iterate(new PrimeFactorIterator(n), i -> i.hasNext(), i -> i.iterate()).collect(toList());
        return result.get(result.size() - 1).getFactors();
    }

    public static class PrimeFactorIterator {
        private int factor;
        private List<Integer> factors;
        private int value;

        PrimeFactorIterator(int initialValue){
            factor = 2;
            factors = new ArrayList<>();
            value = initialValue;
        }

        public List<Integer> getFactors(){
            return factors;
        }

        public PrimeFactorIterator iterate(){
            if (value % factor == 0){
                factors.add(factor);
                value /= factor;
            } else {
                factor++;
            }

            return this;
        }

        public boolean hasNext(){
            return value > 1;
        }

    }

}
