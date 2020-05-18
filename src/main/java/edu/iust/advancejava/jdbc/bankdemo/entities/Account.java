package edu.iust.advancejava.jdbc.bankdemo.entities;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Account {
    private int id;
    private String name;
    private BigDecimal balance;
    private LocalDate dateOfBirth;

    public Account(){}

    public Account(String name, LocalDate dateOfBirth, BigDecimal balance) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", balance=" + balance +
                ", dateOfBirth=" + dateOfBirth +
                '}';
    }
}
