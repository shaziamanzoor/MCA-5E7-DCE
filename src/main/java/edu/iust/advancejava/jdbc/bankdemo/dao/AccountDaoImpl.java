package edu.iust.advancejava.jdbc.bankdemo.dao;

import edu.iust.advancejava.jdbc.bankdemo.entities.Account;
import edu.iust.advancejava.jdbc.connection.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AccountDaoImpl implements AccountDao {

    private final ConnectionManager manager;

    public AccountDaoImpl(ConnectionManager manager){
        this.manager = manager;
    }

    @Override
    public void create(Account account) throws SQLException {
        String sql = "insert into Account(name, balance, dateOfBirth) values(?, ?, ?)";
        try(Connection conn = manager.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, account.getName());
                stmt.setBigDecimal(2, account.getBalance());
                stmt.setDate(3, Date.valueOf(account.getDateOfBirth()));
                stmt.execute();
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    rs.next();
                    account.setId(rs.getInt(1));
                }
                conn.commit();
            } catch (Exception e){
                conn.rollback();
                throw e;
            }
        }
    }

    @Override
    public void update(Account account) throws SQLException {
        String sql = "update Account set name=?, dateOfBirth=? where id = ?";
        try(Connection conn = manager.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, account.getName());
                stmt.setDate(2, Date.valueOf(account.getDateOfBirth()));
                stmt.setInt(3, account.getId());
                stmt.execute();
                conn.commit();
            }catch (Exception e){
                conn.rollback();
                throw e;
            }
        }
    }

    @Override
    public void delete(Account account) throws SQLException {
        String sql = "delete from Account where id = ?";
        try(Connection conn = manager.getConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, account.getId());
                stmt.execute();
            }
        }
    }

    @Override
    public Optional<Account> getById(int id) throws SQLException {
        String sql = "select name, balance, dateOfBirth from Account where id = ?";
        try(Connection conn = manager.getConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        Account account = new Account();
                        account.setId(id);
                        account.setName(rs.getString(1));
                        account.setBalance(rs.getBigDecimal(2));
                        account.setDateOfBirth(rs.getDate(3).toLocalDate());
                        return Optional.of(account);
                    } else {
                        return Optional.empty();
                    }
                }
            }
        }
    }

    @Override
    public List<Account> getAll() throws SQLException {
        String sql = "select id, name, balance, dateOfBirth from Account";
        try(Connection conn = manager.getConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                try (ResultSet rs = stmt.executeQuery()) {
                    List<Account> accounts = new ArrayList<>();
                    while (rs.next()) {
                        Account account = new Account();
                        account.setId(rs.getInt(1));
                        account.setName(rs.getString(2));
                        account.setBalance(rs.getBigDecimal(3));
                        account.setDateOfBirth(rs.getDate(4).toLocalDate());
                        accounts.add(account);
                    }
                    return accounts;
                }
            }
        }

    }

    @Override
    public List<Account> getByName(String name) throws SQLException {
        String sql = "select id, name, balance, dateOfBirth from Account where name like ?";
        try(Connection conn = manager.getConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, "%" + name + "%");
                try (ResultSet rs = stmt.executeQuery()) {
                    List<Account> accounts = new ArrayList<>();
                    while (rs.next()) {
                        Account account = new Account();
                        account.setId(rs.getInt(1));
                        account.setName(rs.getString(2));
                        account.setBalance(rs.getBigDecimal(3));
                        account.setDateOfBirth(rs.getDate(4).toLocalDate());
                        accounts.add(account);
                    }
                    return accounts;
                }
            }
        }
    }
}
