package edu.iust.advancejava.streams.problems;

import java.util.Optional;

public class Salary {
    private Double basic;
    private Optional<Double> allowances;

    public Salary(Double basic, Optional<Double> allowances){
        this.basic = basic;
        this.allowances = allowances;
    }

    public Double getBasic() {
        return basic;
    }

    public Optional<Double> getAllowances() {
        return allowances;
    }

    public Double getTotalSalary(){
        return allowances.map(a -> a + basic).orElse(basic);
    }
}
