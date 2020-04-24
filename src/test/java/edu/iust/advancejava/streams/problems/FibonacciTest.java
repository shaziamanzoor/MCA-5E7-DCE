package edu.iust.advancejava.streams.problems;

import org.junit.Test;

import java.util.Arrays;
import java.util.stream.Collectors;

import static edu.iust.advancejava.streams.problems.Fibonacci.iterateExample;
import static org.junit.Assert.*;

public class FibonacciTest {

    @Test
    public void testStream() throws Exception {
        assertEquals(
                Arrays.asList(0L, 1L, 1L, 2L, 3L, 5L),
                Fibonacci.stream().limit(6).collect(Collectors.toList()));
    }

    @Test
    public void testIterateExample() throws Exception {
        assertEquals(Arrays.asList(1, 2, 3, 4, 5), iterateExample().limit(5).collect(Collectors.toList()));
    }
}