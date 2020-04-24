package edu.iust.advancejava.streams.demo;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class CreateHashMapTest {

    @Test
    public void putExample() throws Exception {
        Map<Integer, String> inputMap = new HashMap<>();
        inputMap.put(1,"a");
        inputMap.put(1, "b"); //Overwrite.

        Map<Integer, String> expected = new HashMap<>();
        expected.put(1, "b");
        assertEquals(expected, inputMap);
    }

    @Test
    public void mergeExample() throws Exception {
        Map<Integer, String> inputMap = new HashMap<>();
        inputMap.put(1,"a");
        inputMap.merge(1, "b", (value1, value2)-> value1  +"," + value2 ); //merge

        Map<Integer, String> expected = new HashMap<>();
        expected.put(1, "a,b");
        assertEquals(expected, inputMap);
    }

}