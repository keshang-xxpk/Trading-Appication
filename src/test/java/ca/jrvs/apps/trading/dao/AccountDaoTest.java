package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Trader;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfiguration.class})
@Sql({"classpath:schema.sql"})


public class AccountDaoTest {
    @Autowired
    private AccountDao accountDao;

    @Autowired
    private TraderDao traderDao;

    private Account savedAccount;
    private Trader savedTrader;

    @Before
    public void Insert() {
        savedTrader = new Trader();
        savedTrader.setCountry("CANADA");
        savedTrader.setDob("2019-08-05");
        savedTrader.setEmail("ke.shang.jrvs.ca@gmail.com");
        savedTrader.setFirstName("Ke");
        savedTrader.setLastName("Shang");
        savedTrader = traderDao.save(savedTrader);

        savedAccount = new Account();
        savedAccount.setTraderId(savedTrader.getId());
        savedAccount.setAmount(100);
        savedAccount.setId(100000);
        savedAccount = accountDao.save(savedAccount);

    }

    @After
    public void DeleteOne() {
        accountDao.deleteById(savedAccount.getId());
        assertFalse(accountDao.existsById(1));
        traderDao.deleteById(savedTrader.getId());
        assertFalse(traderDao.existsById(1));
    }


    @Test
    public void findByTraderId() {
        Account account = accountDao.findByTraderId(1);
        assertSame(account.getId(), savedAccount.getId());
    }

    @Test
    public void findByTraderIdForUpdate() {
        Account account = accountDao.findByTraderIdForUpdate(1);
        savedAccount = accountDao.findByTraderId(1);
        assertTrue(account.getTraderId() == savedAccount.getTraderId());
    }

    @Test
    public void updateAmountById() {
        Account account = accountDao.updateAmountById(1,100.0);
        savedAccount = accountDao.findByIdForUpdate(1);
        assertTrue(account.getAmount() == savedAccount.getAmount());
    }

}