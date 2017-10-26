package uk.co.reinholds.marketquoter.service;

import org.junit.Test;
import uk.co.reinholds.marketquoter.service.domain.MarketOffer;
import uk.co.reinholds.marketquoter.service.domain.Quote;

import java.util.TreeSet;

import static org.junit.Assert.*;

public class QuoteGeneratorTest {

    @Test
    public void getQuote_shouldReadFileAndReturnCorrectValues() throws Exception {
        QuoteGenerator quoteGenerator = new QuoteGenerator();
        Quote quote = quoteGenerator.getBestAvailableQuote("src/test/resources/test-market-data.csv", 1000).get();
        assertEquals(0.07, quote.getYearlyInterestRate(), 0.001);

        Quote quote2 = quoteGenerator.getBestAvailableQuote("src/test/resources/test-market-data.csv", 2000).get();
        assertEquals(0.073, quote2.getYearlyInterestRate(), 0.001);
    }

    @Test
    public void findBestQuote_shouldThrowException_ifNoOfferAreAvailable() throws Exception {
        QuoteGenerator quoteGenerator = new QuoteGenerator();
        assertFalse(quoteGenerator.calculateBestQuote(new TreeSet<>(), 10000)
                .isPresent());
    }

    @Test
    public void findBestQuote_shouldReturnCorrectRate_whenOnlyOneOfferNeeded() throws Exception {
        QuoteGenerator quoteGenerator = new QuoteGenerator();
        TreeSet<MarketOffer> marketOffers = new TreeSet<>();
        double rate = 0.043;

        marketOffers.add(new MarketOffer("Jane", rate, 1000));

        Quote quote = quoteGenerator.calculateBestQuote(marketOffers, 1000).get();
        assertEquals(rate, quote.getYearlyInterestRate(), 0.001);
    }

    @Test
    public void findBestQuote_shouldReturnCorrectRate_whenTwoFullOffersNeeded() throws Exception {
        QuoteGenerator quoteGenerator = new QuoteGenerator();
        TreeSet<MarketOffer> marketOffers = new TreeSet<>();

        marketOffers.add(new MarketOffer("Jane", 0.04, 500));
        marketOffers.add(new MarketOffer("Janet", 0.06, 500));

        Quote quote = quoteGenerator.calculateBestQuote(marketOffers, 1000).get();
        assertEquals(0.05, quote.getYearlyInterestRate(), 0.001);
    }

    @Test
    public void findBestQuote_shouldReturnCorrectRate_whenTwoFullAndOnePartialOffersNeeded() throws Exception {
        QuoteGenerator quoteGenerator = new QuoteGenerator();
        TreeSet<MarketOffer> marketOffers = new TreeSet<>();

        marketOffers.add(new MarketOffer("Jane", 0.04, 500));
        marketOffers.add(new MarketOffer("Janet", 0.06, 500));
        marketOffers.add(new MarketOffer("Janet", 0.10, 1000));

        Quote quote = quoteGenerator.calculateBestQuote(marketOffers, 1500).get();
        assertEquals(0.067, quote.getYearlyInterestRate(), 0.001);
    }

}