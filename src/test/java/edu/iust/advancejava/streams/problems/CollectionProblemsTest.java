package edu.iust.advancejava.streams.problems;

import org.junit.Test;

import java.util.*;
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static edu.iust.advancejava.streams.problems.CollectionProblems.*;

public class CollectionProblemsTest {

    @Test
    public void testMapMerge(){
        Map<Integer, String> m = new HashMap<>();
        m.put(1, "one");
        m.put(2, "two");
        m.merge(2, "دو", (x, y) -> x + "/" + y);
        assertEquals( "two/دو", m.get(2));
    }

    @Test
    public void testMapMergeWithLists(){
        Map<Integer, List<String>> m = new HashMap<>();
        m.put(1, Collections.singletonList("one"));
        m.put(2, Collections.singletonList("two"));
        m.merge(2, Collections.singletonList("دو"), (x, y) -> {
            List<String> z = new ArrayList<>();
            z.addAll(x);
            z.addAll(y);
            return z;
        });
        assertEquals(Arrays.asList("two","دو"), m.get(2));
    }

    @Test
    public void testGroupBy(){
        Stream<String> stream  = Stream.of("one", "two", "three", "four", "five");
        Map<Integer, List<String>> expected = new HashMap<>();
        expected.put(3, Arrays.asList("one", "two"));
        expected.put(4, Arrays.asList("four", "five"));
        expected.put(5, Collections.singletonList("three"));
        assertEquals(expected, stream.collect(groupBy(String::length)));
    }

    @Test
    public void testFrequencies() throws Exception {
        Stream<Integer> stream = Stream.of(1, 2, 1, 2, 3, 1, 4, 5, 6, 4);
        Map<Integer, Integer> expected = new HashMap<>();
        expected.put(1, 3); // Three ones
        expected.put(2, 2);
        expected.put(3, 1);
        expected.put(4, 2);
        expected.put(5, 1);
        expected.put(6, 1);
        assertEquals(expected, stream.collect(frequencies()));
    }


    @Test
    public void testFrequenciesWithParallelStream() throws Exception {
        // count the frequency of characters in a string
        String message = "Java 8 ExAmpLe to execuTe StreaMs in pArallel";

        Map<Character, Integer> expectedCharFrequency = new HashMap<>();
        expectedCharFrequency.put('j', 1);
        expectedCharFrequency.put('a', 6);
        expectedCharFrequency.put('v', 1);
        expectedCharFrequency.put('8', 1);
        expectedCharFrequency.put('e', 7);
        expectedCharFrequency.put('x', 2);
        expectedCharFrequency.put('m', 2);
        expectedCharFrequency.put('p', 2);
        expectedCharFrequency.put('l', 4);
        expectedCharFrequency.put('t', 3);
        expectedCharFrequency.put('o', 1);
        expectedCharFrequency.put('c', 1);
        expectedCharFrequency.put('u', 1);
        expectedCharFrequency.put('s', 2);
        expectedCharFrequency.put('r', 2);
        expectedCharFrequency.put('i', 1);
        expectedCharFrequency.put('n', 1);
        expectedCharFrequency.put(' ', 7);

        // First thing first, convert string into stream of characters
        // use .chars() to get an instance of IntStream(integer representation of the characters) from the input String.
        // and then convert it to Stream char via .mapToObj()

        Stream<Character> characterStream = message.toLowerCase().chars().mapToObj(c -> (char) c).parallel();
        assertTrue(characterStream.isParallel());

        assertEquals(
                expectedCharFrequency,
                characterStream.collect(frequencies()));
    }


    @Test public void testFrequenciesInParallel2(){
        Stream<String> dayWiseWeatherReportStream = Stream.of(
                "Sunny", "Rainy", "Cloudy", "Sunny", "Rainy", "Sunny", "Sunny", "Cloudy", "Rainy", "Sunny",
                "Sunny", "Rainy", "Cloudy", "Sunny", "Rainy", "Cloudy", "Rainy", "Sunny", "Sunny", "Rainy",
                "Sunny", "Sunny", "Rainy", "Sunny", "Sunny", "Sunny", "Sunny", "Sunny", "Windy", "Windy",
                "Rainy").parallel();

        Map<String, Integer> expectedMonthlyFrequencyReport = new HashMap<>();
        expectedMonthlyFrequencyReport.put("Sunny", 16);
        expectedMonthlyFrequencyReport.put("Rainy", 9);
        expectedMonthlyFrequencyReport.put("Cloudy", 4);
        expectedMonthlyFrequencyReport.put("Windy", 2);

        assertEquals(
                expectedMonthlyFrequencyReport,
                dayWiseWeatherReportStream.collect(frequencies())
        );

        assertTrue(dayWiseWeatherReportStream.isParallel());
    }

}
