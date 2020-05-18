package edu.iust.advancejava.jdbc.bankdemo.dao;

import edu.iust.advancejava.jdbc.bankdemo.entities.Account;
import edu.iust.advancejava.jdbc.bankdemo.entities.BankTransaction;
import edu.iust.advancejava.jdbc.connection.ConnectionManager;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TransactionDaoImpl implements TransactionDao {

    private final ConnectionManager manager;

    public TransactionDaoImpl(ConnectionManager manager){
        this.manager = manager;
    }

    @Override
    public void create(BankTransaction transaction) throws SQLException {
        String transactionSql = "insert into BankTransaction(creditAccount, debitAccount, amount, transactionTime) " +
                "values(?, ?, ?, ?)";
        String creditSql = "update Account set balance=balance+? where id = ?";
        String debitSql = "update Account set balance=balance-? where id=?";

        try(Connection conn = manager.getConnection()) {
            try {
                //Begin Transaction
                conn.setAutoCommit(false);
                try (PreparedStatement stmt = conn.prepareStatement(creditSql)) {
                    stmt.setBigDecimal(1, transaction.getAmount());
                    stmt.setInt(2, transaction.getCreditAccount().getId());
                    stmt.execute();
                }

                try (PreparedStatement stmt = conn.prepareStatement(debitSql)) {
                    stmt.setBigDecimal(1, transaction.getAmount());
                    stmt.setInt(2, transaction.getDebitAccount().getId());
                    stmt.execute();
                }

                try (PreparedStatement stmt = conn.prepareStatement(transactionSql,
                        PreparedStatement.RETURN_GENERATED_KEYS)) {
                    stmt.setInt(1, transaction.getCreditAccount().getId());
                    stmt.setInt(2, transaction.getDebitAccount().getId());
                    stmt.setBigDecimal(2, transaction.getAmount());
                    stmt.setDate(3, Date.valueOf(transaction.getTransactionDate()));

                    try (ResultSet rs = stmt.getGeneratedKeys()) {
                        rs.next();
                        transaction.setId(rs.getInt(1));
                    }
                }
                conn.commit();
            }catch(Exception e){
                conn.rollback();
                throw e;
            }
        }
    }

    @Override
    public Optional<BankTransaction> getById(int id) throws SQLException {
        String sql = "select creditAccount, debitAccount, amount, transactionDate, " +
                "c.name, c.balance, c.dateOfBirth, d.name, d.balance, d.dateOfBirth " +
                "from BankTransaction bt " +
                "inner join Account c on bt.creditAccount = c.id " +
                "inner join Account d on bt.debitAccount = d.id " +
                "where id = ?";
        try (Connection conn = manager.getConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        BankTransaction transaction = new BankTransaction();
                        transaction.setId(id);
                        transaction.setAmount(rs.getBigDecimal(3));
                        transaction.setTransactionDate(rs.getDate(4).toLocalDate());
                        Account creditAccount = new Account(rs.getString(5),
                                rs.getDate(7).toLocalDate(), rs.getBigDecimal(6));
                        Account debitAccount = new Account(rs.getString(8),
                                rs.getDate(10).toLocalDate(), rs.getBigDecimal(9));
                        transaction.setCreditAccount(creditAccount);
                        transaction.setDebitAccount(debitAccount);
                        return Optional.of(transaction);
                    } else {
                        return Optional.empty();
                    }
                }
            }
        }
    }

    @Override
    public List<BankTransaction> getTransactionsWithin(LocalDate start, LocalDate end) throws SQLException {
        String sql = "select creditAccount, debitAccount, amount, transactionDate, " +
                "c.name, c.balance, c.dateOfBirth, d.name, d.balance, d.dateOfBirth, bt.id " +
                "from BankTransaction bt " +
                "inner join Account c on bt.creditAccount = c.id " +
                "inner join Account d on bt.debitAccount = d.id " +
                "where transactionDate between ? and ?";
        try (Connection conn = manager.getConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setDate(1, Date.valueOf(start));
                stmt.setDate(2, Date.valueOf(end));
                try (ResultSet rs = stmt.executeQuery()) {
                    List<BankTransaction> transactions = new ArrayList<>();
                    while(rs.next()) {
                        BankTransaction transaction = new BankTransaction();
                        transaction.setId(rs.getInt(11));
                        transaction.setAmount(rs.getBigDecimal(3));
                        transaction.setTransactionDate(rs.getDate(4).toLocalDate());
                        Account creditAccount = new Account(rs.getString(5),
                                rs.getDate(7).toLocalDate(), rs.getBigDecimal(6));
                        Account debitAccount = new Account(rs.getString(8),
                                rs.getDate(10).toLocalDate(), rs.getBigDecimal(9));
                        transaction.setCreditAccount(creditAccount);
                        transaction.setDebitAccount(debitAccount);
                        transactions.add(transaction);
                    }
                    return transactions;
                }
            }
        }
    }

}
