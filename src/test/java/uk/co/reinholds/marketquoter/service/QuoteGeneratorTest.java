package uk.co.reinholds.marketquoter.service;

import org.junit.Test;

import java.util.TreeSet;

import static org.junit.Assert.*;

public class QuoteGeneratorTest {

    @Test
    public void getQuote() throws Exception {
        QuoteGenerator quoteGenerator = new QuoteGenerator();
        Quote quote = quoteGenerator.getQuote("Market Data for Exercise - csv.csv", 1000);
        assertEquals(0.29, quote.getYearlyInterestRate(), 0.001);
    }

    @Test(expected = LoadRequestFailureException.class)
    public void findBestQuote() throws Exception {
        QuoteGenerator quoteGenerator = new QuoteGenerator();
        quoteGenerator.findBestQuote(new TreeSet<>(), 10000);
    }

}