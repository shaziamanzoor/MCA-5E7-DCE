package edu.iust.advancejava.jdbc.bankdemo.entities;

import java.math.BigDecimal;
import java.time.LocalDate;

public class BankTransaction {
    private int id;
    private Account creditAccount;
    private Account debitAccount;
    private LocalDate transactionDate;
    private BigDecimal amount;

    public Account getCreditAccount() {
        return creditAccount;
    }

    public void setCreditAccount(Account creditAccount) {
        this.creditAccount = creditAccount;
    }

    public Account getDebitAccount() {
        return debitAccount;
    }

    public void setDebitAccount(Account debitAccount) {
        this.debitAccount = debitAccount;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "BankTransaction{" +
                "creditAccount=" + creditAccount +
                ", debitAccount=" + debitAccount +
                ", transactionDate=" + transactionDate +
                ", amount=" + amount +
                '}';
    }

    public int getId(){
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
