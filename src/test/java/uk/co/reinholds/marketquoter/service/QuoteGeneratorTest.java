package uk.co.reinholds.marketquoter.service;

import org.junit.Test;
import uk.co.reinholds.marketquoter.dto.MarketOffer;

import java.util.TreeSet;

import static org.junit.Assert.*;

public class QuoteGeneratorTest {

    @Test
    public void getQuote() throws Exception {
        QuoteGenerator quoteGenerator = new QuoteGenerator();
        Quote quote = quoteGenerator.getQuote("Market Data for Exercise - csv.csv", 1000);
        assertEquals(0.07, quote.getYearlyInterestRate(), 0.001);
    }

    @Test(expected = LoadRequestFailureException.class)
    public void findBestQuote_shouldThrowException_ifNoOfferAreAvailable() throws Exception {
        QuoteGenerator quoteGenerator = new QuoteGenerator();
        quoteGenerator.calculateBestQuote(new TreeSet<>(), 10000);
    }

    @Test
    public void findBestQuote_shouldReturnCorrectRate_whenOnlyOneOfferNeeded() throws Exception {
        QuoteGenerator quoteGenerator = new QuoteGenerator();
        TreeSet<MarketOffer> marketOffers = new TreeSet<>();
        double rate = 0.043;
        marketOffers.add(new MarketOffer("Jane", rate, 1000));
        Quote bestQuote = quoteGenerator.calculateBestQuote(marketOffers, 1000);
        assertEquals(rate, bestQuote.getYearlyInterestRate(), 0.001);
    }

    @Test
    public void findBestQuote_shouldReturnCorrectRate_whenTwoFullOffersNeeded() throws Exception {
        QuoteGenerator quoteGenerator = new QuoteGenerator();
        TreeSet<MarketOffer> marketOffers = new TreeSet<>();
        marketOffers.add(new MarketOffer("Jane", 0.04, 500));
        marketOffers.add(new MarketOffer("Janet", 0.06, 500));
        Quote bestQuote = quoteGenerator.calculateBestQuote(marketOffers, 1000);
        assertEquals(0.05, bestQuote.getYearlyInterestRate(), 0.001);
    }

    @Test
    public void findBestQuote_shouldReturnCorrectRate_whenTwoFullAndOnePartialOffersNeeded() throws Exception {
        QuoteGenerator quoteGenerator = new QuoteGenerator();
        TreeSet<MarketOffer> marketOffers = new TreeSet<>();
        marketOffers.add(new MarketOffer("Jane", 0.04, 500));
        marketOffers.add(new MarketOffer("Janet", 0.06, 500));
        marketOffers.add(new MarketOffer("Janet", 0.10, 1000));
        Quote bestQuote = quoteGenerator.calculateBestQuote(marketOffers, 1500);
        assertEquals(0.067, bestQuote.getYearlyInterestRate(), 0.001);
    }

}