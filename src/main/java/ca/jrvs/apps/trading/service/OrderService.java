package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.dao.SecurityOrderDao;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Quote;
import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import ca.jrvs.apps.trading.model.dto.MarketOrderDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    private AccountDao accountDao;
    private SecurityOrderDao securityOrderDao;
    private QuoteDao quoteDao;
    private PositionDao positionDao;

    @Autowired
    public OrderService(AccountDao accountDao, SecurityOrderDao securityOrderDao,
                        QuoteDao quoteDao, PositionDao positionDao) {
        this.accountDao = accountDao;
        this.securityOrderDao = securityOrderDao;
        this.quoteDao = quoteDao;
        this.positionDao = positionDao;
    }

    /**
     * Execute a market order
     * <p>
     * - validate the order (e.g. size, and ticker)
     * - Create a securityOrder (for security_order table)
     * - Handle buy or sell order
     * - buy order : check account balance
     * - sell order: check position for the ticker/symbol
     * - (please don't forget to update securityOrder.status)
     * - Save and return securityOrder
     * <p>
     * NOTE: you will need to some helper methods (protected or private)
     *
     * @param orderDto market order
     * @return SecurityOrder from security_order table
     * @throws org.springframework.dao.DataAccessException if unable to get data from DAO
     * @throws IllegalArgumentException                    for invalid input
     */
    public SecurityOrder executeMarketOrder(MarketOrderDto orderDto) {
        //valiadate the order(size,ticker)
        if (orderDto.getSize() == null || orderDto.getSize() == 0) {
            throw new IllegalArgumentException("Invalid order size()");
        }
        //ticker
        Quote quote = quoteDao.findById(orderDto.getTicker());
        if (quote == null) {
            throw new IllegalArgumentException(("Incalid ticker"));
        }
        //create a securityOrder(for security_order table)
        SecurityOrder securityOrder = new SecurityOrder();
        securityOrder.setAccountId(orderDto.getAccountId());
        securityOrder.setTicker(orderDto.getTicker());
        securityOrder.setSize(orderDto.getSize());

        Account account = accountDao.findByTraderIdForUpdate(orderDto.getAccountId());

        if (orderDto.getSize() > 0) {
            securityOrder.setPrice(Double.valueOf(quote.getAskPrice()));
            handleBuyMarketOrder(orderDto, securityOrder, account);
        } else {
            securityOrder.setPrice(Double.valueOf(quote.getBidPrice()));
            handleSellMarketOrder(orderDto, securityOrder, account);
        }
        return securityOrderDao.save(securityOrder);
    }

    public void handleBuyMarketOrder(MarketOrderDto orderDto, SecurityOrder order, Account account) {
        Double buypower = orderDto.getSize() * order.getPrice();
        if (account.getAmount() >= buypower) {
            double updateAmount = account.getAmount() - buypower;
            accountDao.updateAmountById(orderDto.getAccountId(), updateAmount);
            order.setStatus("FILLED");
        } else {
            order.setStatus("CANCELED");
            order.setNotes("Insufficient fund.Requires buypower:" + buypower);
        }
    }

    protected void handleSellMarketOrder(MarketOrderDto orderDto, SecurityOrder order, Account account) {
        Long position = positionDao.findByIdAndTicker(orderDto.getAccountId(), orderDto.getTicker());
        logger.debug("AccountId:" + orderDto.getAccountId() + "has position:" + position);
        if (position + orderDto.getSize() >= 0) {
            Double sellAmount = -order.getSize() * order.getPrice();
            double updateAmount = account.getAmount() + sellAmount;
            order.setStatus("FILLED");
            accountDao.updateAmountById(orderDto.getAccountId(), updateAmount);
        } else {
            order.setStatus("CANCELLED");
            order.setNotes("Insufficent position");
        }

    }

}