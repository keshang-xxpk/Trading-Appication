package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.SecurityOrderDao;
import ca.jrvs.apps.trading.dao.TraderDao;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Position;
import ca.jrvs.apps.trading.model.domain.Trader;
import ca.jrvs.apps.trading.model.view.TraderAccountView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegisterService {

    private TraderDao traderDao;
    private AccountDao accountDao;
    private PositionDao positionDao;
    private SecurityOrderDao securityOrderDao;

    @Autowired
    public RegisterService(TraderDao traderDao, AccountDao accountDao,
                           PositionDao positionDao, SecurityOrderDao securityOrderDao) {
        this.traderDao = traderDao;
        this.accountDao = accountDao;
        this.positionDao = positionDao;
        this.securityOrderDao = securityOrderDao;
    }

    /**
     * Create a new trader and initialize a new account with 0 amount.
     * - validate user input (all fields must be non empty)
     * - create a trader
     * - create an account
     * - create, setup, and return a new traderAccountView
     *
     * @param trader trader info
     * @return traderAccountView
     * @throws ca.jrvs.apps.trading.dao.ResourceNotFoundException if ticker is not found from IEX
     * @throws org.springframework.dao.DataAccessException        if unable to retrieve data
     * @throws IllegalArgumentException                           for invalid input
     */
    public TraderAccountView createTraderAndAccount(Trader trader) {
        if (trader.getCountry() == null || trader.getDob() == null
                || trader.getEmail() == null || trader.getFirstName() == null
                || trader.getId() == null || trader.getLastName() == null) {
            throw new IllegalArgumentException("Invaild trader");
        }
        if (traderDao.existsById(trader.getId())) {
            throw new IllegalStateException("Trader has already in file.");
        }
        traderDao.save(trader);
        TraderAccountView traderAccountView = new TraderAccountView();
        traderAccountView.setTrader(trader);
        Account account = new Account();
        account.setAmount(0);
        accountDao.save(account);
        traderAccountView.setAccount(account);

        return traderAccountView;
    }

    /**
     * A trader can be deleted iff no open position and no cash balance.
     * - validate traderID
     * - get trader account by traderId and check account balance
     * - get positions by accountId and check positions
     * - delete all securityOrders, account, trader (in this order)
     *
     * @param traderId
     * @throws ca.jrvs.apps.trading.dao.ResourceNotFoundException if ticker is not found from IEX
     * @throws org.springframework.dao.DataAccessException        if unable to retrieve data
     * @throws IllegalArgumentException                           for invalid input
     */
    public void deleteTraderById(Integer traderId) {
        Trader trader = traderDao.findById(traderId);
        if (traderId == null) {
            throw new IllegalArgumentException("Input traderId is empty.Please input an  valid traderId!");
        }
        Account account = accountDao.findByTraderId(traderId);
        if (account.getAmount() != 0) {
            throw new IllegalArgumentException("Can not delete trader because balance is not 0!");
        }
        List<Position> positions = positionDao.findByAccountId(account.getId());
        positions.forEach(position -> {
            if (position.getPosition() != 0) {
                throw new IllegalArgumentException("Can not delete the trader cause there is open position!");
            }
        });
        securityOrderDao.deletebyAccountId(account.getId());
        accountDao.deleteById(account.getId());
        traderDao.deleteById(traderId);
    }

}