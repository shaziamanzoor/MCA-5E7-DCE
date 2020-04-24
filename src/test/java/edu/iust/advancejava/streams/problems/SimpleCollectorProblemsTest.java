package edu.iust.advancejava.streams.problems;

import org.junit.Test;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static edu.iust.advancejava.streams.problems.SimpleCollectorProblems.calculateAggregateSalary;
import static edu.iust.advancejava.streams.problems.SimpleCollectorProblems.calculateAverageSalary;
import static edu.iust.advancejava.streams.problems.SimpleCollectorProblems.calculateGrossSalaries;
import static org.junit.Assert.*;

public class SimpleCollectorProblemsTest {

    @Test
    public void testCalculateGrossSalaries(){
        assertArrayEquals(new Double[]{100.0, 700.0, 1100.0},
                calculateGrossSalaries(sampleSalaries()).collect(Collectors.toList()).toArray());
    }

    @Test
    public void testCalculateAverageSalary(){
        assertEquals(633.33,
                calculateAverageSalary(sampleSalaries()), 0.01);
    }
    @Test
    public void testCalculateAggregateSalary(){
        assertEquals(1900,
                calculateAggregateSalary(sampleSalaries()), 0.01);
    }

    public Stream<Salary> sampleSalaries(){
        return Arrays.stream(new Salary[]{
                new Salary(100.0, Optional.empty()),
                new Salary(500.0, Optional.of(200.0)),
                new Salary(700.0, Optional.of(400.0))
        });
    }
}