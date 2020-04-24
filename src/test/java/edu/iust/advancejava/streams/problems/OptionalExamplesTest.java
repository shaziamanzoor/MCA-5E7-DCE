package edu.iust.advancejava.streams.problems;

import org.junit.Test;

import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class OptionalExamplesTest {

    @Test
    public void testMax(){
        assertTrue(OptionalExamples.max(3, 5).isPresent());
        assertFalse(OptionalExamples.max(null, 15).isPresent());
        assertEquals(OptionalExamples.max(5, 11).get(), new Integer(11));
    }

    @Test
    public void testFindInEither(){
        assertTrue(OptionalExamples.findInEither(Stream.of(1, 2, 3), Stream.of(6, 8, 10), OptionalExamplesTest::odd).isPresent());
        assertTrue(OptionalExamples.findInEither(Stream.of(6, 2, 4), Stream.of(6, 8, 11), OptionalExamplesTest::odd).isPresent());
        assertFalse(OptionalExamples.findInEither(Stream.of(6, 2, 4), Stream.of(6, 8, 10), OptionalExamplesTest::odd).isPresent());
        Optional<Integer> match = OptionalExamples.findInEither(Stream.of(6, 2, 4), Stream.of(7, 8, 10), OptionalExamplesTest::odd);
        assertEquals(match.get(), new Integer(7));
    }

    private static boolean odd(int x){
        return x % 2 != 0;
    }

}