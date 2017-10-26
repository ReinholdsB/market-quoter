package uk.co.reinholds.marketquoter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import uk.co.reinholds.marketquoter.service.Quote;
import uk.co.reinholds.marketquoter.service.QuoteGenerator;

import static java.lang.System.exit;

@SpringBootApplication
public class MarketQuoterApplication implements CommandLineRunner {

    @Autowired
    protected QuoteGenerator quoteGenerator;

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(MarketQuoterApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        if (args.length > 1) {
            String marketFileLocation = args[0];
            Integer loanAmount = Integer.valueOf(args[1]);
            Quote quote = this.quoteGenerator.getQuote(marketFileLocation, loanAmount);
            System.out.println("=================================================");
            System.out.println("Best loan offer that I can make is as follows:");
            System.out.println("=================================================");
            quote.print();
            System.out.println("=================================================");

        } else {
            System.out.println("Please provide all input parameters!");
            System.out.println("Usage: 'script.jar marketDataFilename.csv {loadAmountInteger}'");
        }

        exit(0);
    }
}
