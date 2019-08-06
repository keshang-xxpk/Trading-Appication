package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.dao.SecurityOrderDao;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Quote;
import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import ca.jrvs.apps.trading.model.dto.MarketOrderDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {

    @Mock
    AccountDao accountDao;
    @Mock
    QuoteDao quoteDao;
    @Mock
    PositionDao positionDao;
    @Mock
    SecurityOrderDao securityOrderDao;

    @InjectMocks
    public OrderService orderService;

    @Captor
    private ArgumentCaptor<SecurityOrder> captor;

    private MarketOrderDto marketOrderDto;
    private Quote quote;
    private Account account;
    private Long position;

    @Before
    public void setUp() {
        //"id",
        //        "accountId",
        //        "status",
        //        "ticker",
        //        "size",
        //        "price",
        //        "notes"
        marketOrderDto = new MarketOrderDto();
        marketOrderDto.setAccountId(1);
        marketOrderDto.setStatus("open");
        marketOrderDto.setTicker("AAPL");
        marketOrderDto.setSize(1);
        marketOrderDto.setPrice(100);
        marketOrderDto.setNotes("no");
        quote = new Quote();
        quote.setTicker("AAPL");
        quote.setLastPrice(200);
        quote.setAskPrice(100);
        quote.setAskSize(100);
        quote.setBidPrice(100);
        quote.setBidSize(100);
        account = new Account();
        account.setId(1);
        account.setTraderId(1);
        account.setAmount(100);
        position = 2L;
        Mockito.when(quoteDao.findById(marketOrderDto.getTicker())).thenReturn(quote);
        Mockito.when(accountDao.findByIdForUpdate(marketOrderDto.getAccountId())).thenReturn(account);
        Mockito.when(positionDao.findByIdAndTicker(account.getId(), marketOrderDto.getTicker()))
                .thenReturn(position);
    }
    @Test
    public void buyOrderFilledTest() {
        String filled = "FILLED";
        assertEquals(filled, getCaptorValue().getStatus());
    }

    @Test
    public void buyOrderCanceledTest() {
        String canceled = "CANCELED";
        account.setAmount(200);  // not enough
        assertEquals(canceled, getCaptorValue().getStatus());
    }

    @Test
    public void sellOrderFilledTest() {
        String filled = "FILLED";
        marketOrderDto.setSize(-2);
        assertEquals(filled, getCaptorValue().getStatus());
    }

    @Test
    public void sellOrderCanceledTest() {
        String canceled = "CANCELED";
        marketOrderDto.setSize(-3); // larger than current position
        assertEquals(canceled, getCaptorValue().getStatus());
    }

    protected SecurityOrder getCaptorValue() {
        orderService.executeMarketOrder(marketOrderDto);
        verify(securityOrderDao).save(captor.capture());
        return captor.getValue();
    }

}