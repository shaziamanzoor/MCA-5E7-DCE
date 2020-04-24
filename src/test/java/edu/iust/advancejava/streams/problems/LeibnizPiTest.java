package edu.iust.advancejava.streams.problems;

import org.junit.Test;

import static org.junit.Assert.*;

public class LeibnizPiTest {

    @Test
    public void testCalculate() throws Exception {
        assertEquals(3.1457, LeibnizPi.calculate(150), 0.01);
    }

}