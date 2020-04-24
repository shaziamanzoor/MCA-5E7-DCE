package edu.iust.advancejava.streams.problems;

import org.junit.Test;

import java.util.Arrays;
import java.util.stream.Collectors;


import static org.junit.Assert.*;

public class FactorialTest {


    @Test
    public void testStream(){
        assertEquals(Arrays.asList(1L, 2L, 6L, 24L, 120L, 720L), Factorial.stream().limit(6).collect(Collectors.toList()));
    }

}