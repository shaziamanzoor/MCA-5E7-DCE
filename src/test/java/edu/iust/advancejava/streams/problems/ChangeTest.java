package edu.iust.advancejava.streams.problems;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class ChangeTest {

    @Test
    public void testStream() {
        assertEquals(
                Change.stream(10, Arrays.asList(5, 2)).collect(Collectors.toList()),
                Arrays.asList(Arrays.asList(5, 5), Arrays.asList(2, 2, 2, 2, 2)));
        assertEquals(
                Change.stream(19, Arrays.asList(5, 2)).collect(Collectors.toList()),
                Arrays.asList(Arrays.asList(2, 2, 5, 5, 5), Arrays.asList(2, 2, 2, 2, 2, 2, 2, 5)));
    }

}