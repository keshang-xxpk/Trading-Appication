package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.MarketDataDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import ca.jrvs.apps.trading.model.domain.Quote;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static ca.jrvs.apps.trading.service.QuoteService.buildQuoteFromIexQuote;
import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class QuoteServiceTest {

    @Mock
    QuoteDao quoteDao;

    @Mock
    MarketDataDao marketDataDao;

    @InjectMocks
    public QuoteService quoteService;

    @Captor
    private ArgumentCaptor<List<Quote>> captor;

    private IexQuote iexQuoteAAPL;
    private IexQuote iexQuoteFB;

    @Before
    public void setUp() {
        iexQuoteAAPL = new IexQuote();
        iexQuoteAAPL.setSymbol("aapl");
        iexQuoteAAPL.setLatestPrice("200");
        iexQuoteAAPL.setIexAskPrice("180");
        iexQuoteFB = new IexQuote();
        iexQuoteFB.setSymbol("fb");
        iexQuoteFB.setLatestPrice("100");
        iexQuoteFB.setIexBidPrice("90");
    }

    @Test
    public void testBuildQuoteFromIexQuote() {
        assertEquals(iexQuoteAAPL.getSymbol(), buildQuoteFromIexQuote(iexQuoteAAPL).getTicker());
        assertTrue(buildQuoteFromIexQuote(iexQuoteAAPL).getBidPrice() == 0.0);
    }

    @Test
    public void initQuotes() {
        List<IexQuote> iexQuotes = Arrays.asList(iexQuoteAAPL, iexQuoteFB);
        List<String> tickers = Arrays.asList("aapl", "fb");
        Mockito.when(marketDataDao.findIexQuoteByTicker(tickers)).thenReturn(iexQuotes);
        Mockito.when(quoteDao.existsById("aapl")).thenReturn(false);
        Mockito.when(quoteDao.existsById("fb")).thenReturn(true);

        try {
            quoteService.initQuotes(tickers);
            fail();
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void updateMarketData() {
        Quote quoteAAPL = buildQuoteFromIexQuote(iexQuoteAAPL);
        Quote quoteFB = buildQuoteFromIexQuote(iexQuoteFB);
        List<String> tickers = Arrays.asList("aapl", "fb");
        List<Quote> quotes = Arrays.asList(quoteAAPL, quoteFB);
        Mockito.when(quoteDao.findAll()).thenReturn(quotes);
        iexQuoteAAPL.setIexBidPrice("80");
        iexQuoteFB.setIexBidPrice("90");
        List<IexQuote> iexQuotes = Arrays.asList(iexQuoteAAPL, iexQuoteFB);
        Quote quoteAAPL2 = buildQuoteFromIexQuote(iexQuoteAAPL);
        Mockito.when(marketDataDao.findIexQuoteByTicker(tickers)).thenReturn(iexQuotes);

        quoteService.updateMarketData();
        verify(quoteDao).update(captor.capture());
        final List<Quote> capturedArgument = captor.getValue();
        Quote quote3 = capturedArgument.get(0);
        assertTrue(quote3.getBidPrice() == 80);
    }

}
