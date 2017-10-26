package uk.co.reinholds.marketquoter.service;

import org.junit.Test;
import uk.co.reinholds.marketquoter.service.domain.Quote;

import static org.junit.Assert.assertEquals;

public class QuoteTest {
    @Test
    public void getMonthlyRepayment() throws Exception {
        Quote quote = new Quote(1000, 36, 0.07);
        assertEquals(30.78, quote.getMonthlyRepayment(), 0.01);
    }

    @Test
    public void getTotalRepayment() throws Exception {
        Quote quote = new Quote(1000, 36, 0.07);
        assertEquals(1108.10, quote.getTotalRepayment(), 0.1);
    }

}