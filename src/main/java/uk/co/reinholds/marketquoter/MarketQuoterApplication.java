package uk.co.reinholds.marketquoter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import uk.co.reinholds.marketquoter.service.QuoteGenerator;

@SpringBootApplication
public class MarketQuoterApplication implements CommandLineRunner {

    @Autowired
    protected QuoteGenerator quoteGenerator;

    public static void main(String[] args) {
        SpringApplication.run(MarketQuoterApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(
                this.quoteGenerator.getQuote(args[0], Integer.valueOf(args[1])));

        if (args.length > 0 && args[0].equals("exitcode")) {
            throw new ExitException();
        }
    }
}
