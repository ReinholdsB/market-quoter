package uk.co.reinholds.marketquoter;

import org.junit.Test;

import static org.junit.Assert.*;

public class MarketQuoterRunnerTest {

    @Test
    public void run_returnsTrue_whenGivenGoodLoanSize() throws Exception {
        assertTrue(new MarketQuoterRunner().run("src/test/resources/test-market-data.csv", 1000));
    }
    @Test
    public void run_returnsFalse_whenGivenTooLargeLoanSize() throws Exception {
        assertFalse(new MarketQuoterRunner().run("src/test/resources/test-market-data.csv", 999999));
    }

}