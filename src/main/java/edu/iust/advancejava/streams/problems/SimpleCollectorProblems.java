package edu.iust.advancejava.streams.problems;

import java.util.stream.Collectors;
import java.util.stream.Stream;



public class SimpleCollectorProblems {

    public static Stream<Double> calculateGrossSalaries(Stream<Salary> salaries){
        return salaries.map(s -> s.getBasic() + s.getAllowances().orElse(0.0));

    }

    public static double calculateAverageSalary(Stream<Salary> salary){
       return calculateGrossSalaries(salary).collect(Collectors.averagingDouble(x -> x));

    }

    public static double calculateAggregateSalary(Stream<Salary> salary){
        return calculateGrossSalaries(salary).collect(Collectors.summingDouble(x -> x));

    }
}
