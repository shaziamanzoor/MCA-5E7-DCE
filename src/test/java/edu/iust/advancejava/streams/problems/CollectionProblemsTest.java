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

}