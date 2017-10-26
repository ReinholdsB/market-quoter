package uk.co.reinholds.marketquoter;

import uk.co.reinholds.marketquoter.service.domain.Quote;
import uk.co.reinholds.marketquoter.service.QuoteGenerator;

import java.util.Optional;

public class MarketQuoterRunner {


    public static void main(String... args) {
        if (args.length > 1) {
            String marketFileLocation = args[0];
            Integer loanAmount = Integer.valueOf(args[1]);
            if (marketFileLocation.isEmpty()) {
                System.out.println("Please provide a valid file location!");
            } else if (loanAmount % 100 != 0) {
                System.out.println("Please provide loan size that is dividable by 100!");
            } else {
                MarketQuoterRunner marketQuoterRunner = new MarketQuoterRunner();
                marketQuoterRunner.run(marketFileLocation, loanAmount);
            }
        } else {
            System.out.println("Please provide all input parameters!");
            System.out.println("Usage: 'script.jar ${marketDataFile.csv} ${loadAmount}'");
        }
        System.exit(0);
    }

    protected boolean run(String marketFileLocation, int loanAmount) {
        Optional<Quote> quote = new QuoteGenerator().getBestAvailableQuote(marketFileLocation, loanAmount);
        if (quote.isPresent()){
            System.out.println("=================================================");
            System.out.println("Best loan offer that I can make is as follows:");
            System.out.println("=================================================");
            quote.get().print();
            System.out.println("=================================================");
            return true;
        } else {
            System.out.println("=================================================");
            System.out.println("Unfortunately requested load cannot be fulfilled at the moment.");
            System.out.println("Try to decrease value of loan and try again.");
            System.out.println("=================================================");
            return false;
        }
    }

}
