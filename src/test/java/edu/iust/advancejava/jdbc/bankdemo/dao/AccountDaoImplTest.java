package edu.iust.advancejava.jdbc.bankdemo.dao;

import edu.iust.advancejava.jdbc.bankdemo.entities.Account;
import edu.iust.advancejava.jdbc.connection.ConnectionManager;
import edu.iust.advancejava.jdbc.connection.ConnectionManagerImpl;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class AccountDaoImplTest {
    private AccountDao dao;

    @Before
    public void beginTest() throws Exception {
        ConnectionManager cm = new ConnectionManagerImpl();
        cm.initialize();
        dao = new AccountDaoImpl(cm);
    }

    @Test
    public void testCreate() throws Exception {
        Account account = new Account("John", LocalDate.now(), new BigDecimal(1000.0));
        dao.create(account);
        assertTrue(dao.getById(account.getId()).isPresent());

        account.setName("Mary");
        dao.update(account);

        Optional<Account> updatedAccount = dao.getById(account.getId());
        assertTrue(updatedAccount.isPresent());
        assertEquals("Mary", updatedAccount.get().getName());

        dao.delete(account);

        assertFalse(dao.getById(account.getId()).isPresent());
    }

    @Test
    public void testGetAll() throws Exception {
        String name = "Zakir";
        Account account = new Account(name, LocalDate.now(), new BigDecimal(1000.0));
        int userCount = dao.getAll().size();
        dao.create(account);
        assertTrue(dao.getById(account.getId()).isPresent());
        List<Account> accounts = dao.getAll();
        assertEquals(userCount + 1, accounts.size());
        Account zakir = accounts.get(userCount);
        assertEquals(name, zakir.getName());
        assertEquals(new BigDecimal(1000.0), zakir.getBalance());
        dao.delete(zakir);
    }

    @Test
    public void testGetByName() throws Exception {
        String name = "Zakir";
        Account account = new Account(name, LocalDate.now(), new BigDecimal(1000.0));
        int userCount = dao.getByName(name).size();
        dao.create(account);
        assertTrue(dao.getById(account.getId()).isPresent());
        List<Account> accounts = dao.getByName(name);
        assertEquals(userCount + 1, accounts.size());
        Account zakir = accounts.get(userCount);
        assertEquals(name, zakir.getName());
        assertEquals(new BigDecimal(1000.0), zakir.getBalance());
        dao.delete(zakir);
    }

}