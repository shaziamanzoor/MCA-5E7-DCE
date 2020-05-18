package edu.iust.advancejava.jdbc.bankdemo.dao;

import edu.iust.advancejava.jdbc.bankdemo.entities.Account;
import edu.iust.advancejava.jdbc.bankdemo.entities.BankTransaction;
import edu.iust.advancejava.jdbc.connection.ConnectionManager;
import edu.iust.advancejava.jdbc.connection.ConnectionManagerImpl;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAmount;
import java.util.Optional;

import static org.junit.Assert.*;

public class TransactionDaoImplTest {

    private TransactionDao dao;
    private AccountDao accountDao;
    private Account creditAccount;
    private Account debitAccount;


    @Before
    public void before() throws  Exception{
        ConnectionManager manager = new ConnectionManagerImpl();
        manager.initialize();
        dao = new TransactionDaoImpl(manager);
        accountDao = new AccountDaoImpl(manager);
        creditAccount = new Account("Ahmad",
                LocalDate.now().minus(20, ChronoUnit.YEARS),
                new BigDecimal(55.0));
        debitAccount = new Account("Salma",
                LocalDate.now().minus(30, ChronoUnit.YEARS),
                new BigDecimal(60.0));
        accountDao.create(creditAccount);
        accountDao.create(debitAccount);
    }

    @Test
    public void testCreate() throws Exception {
        BankTransaction transaction = new BankTransaction();
        transaction.setDebitAccount(debitAccount);
        transaction.setCreditAccount(creditAccount);
        transaction.setTransactionDate(LocalDate.now());
        transaction.setAmount(new BigDecimal(20.0));

        dao.create(transaction);
        Optional<BankTransaction> transactionResult = dao.getById(transaction.getId());
        assertTrue(transactionResult.isPresent());

        assertEquals(new BigDecimal(75.0), transactionResult.get().getCreditAccount().getBalance());
        assertEquals(new BigDecimal(40.0), transactionResult.get().getDebitAccount().getBalance());
    }

    @Test
    public void testGetTransactionsWithin() throws Exception {
    }

}