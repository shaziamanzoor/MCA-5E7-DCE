package edu.iust.advancejava.jdbc.bankdemo.dao;

import edu.iust.advancejava.jdbc.bankdemo.entities.BankTransaction;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TransactionDao {
    void create(BankTransaction transaction) throws SQLException;
    Optional<BankTransaction> getById(int id) throws SQLException;
    List<BankTransaction> getTransactionsWithin(LocalDate start, LocalDate end) throws SQLException;
}
