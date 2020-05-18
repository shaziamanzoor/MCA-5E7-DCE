package edu.iust.advancejava.jdbc.bankdemo.dao;

import edu.iust.advancejava.jdbc.bankdemo.entities.Account;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface AccountDao {
    void create(Account account) throws SQLException;
    void update(Account account) throws SQLException;
    void delete(Account account) throws SQLException;
    Optional<Account> getById(int id) throws SQLException;
    List<Account> getAll() throws SQLException;
    List<Account> getByName(String name) throws SQLException;
}
