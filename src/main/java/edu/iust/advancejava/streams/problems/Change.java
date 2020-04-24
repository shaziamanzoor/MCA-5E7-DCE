package edu.iust.advancejava.streams.problems;

import java.util.*;
import java.util.stream.Stream;
// number can be considered as money, coins is change in coins for the amount of money
public class Change {

    public static Stream<List<Integer>> stream(long number, List<Integer> coins){
        coins.sort((x, y) -> y - x);
        return createChange(number, coins);
    }

    private static Stream<List<Integer>> createChange(long number, List<Integer> coins) {
        if (number == 0)
            return Stream.of(new ArrayList<Integer>());
        else if (number < 0 || coins.isEmpty())
            return Stream.empty();
        else {
            int coin = coins.get(0);
            if (coin > number){
                return createChange(number, rest(coins));
            } else {
                Stream<List<Integer>> withCoin = createChange(number - coin, coins).map(l -> append(l, coin));
                Stream<List<Integer>> withoutCoin = createChange(number, rest(coins));
                return Stream.concat(withCoin, withoutCoin);
            }
        }
    }

    private static List<Integer> rest(List<Integer> xs){
        return xs.size() == 1 ? new ArrayList<>() : xs.subList(1, xs.size());
    }

    private static List<Integer> append(List<Integer> coins, int coin) {
        List<Integer> copy = new ArrayList<>(coins);
        copy.add(coin);
        return copy;
    }
}
