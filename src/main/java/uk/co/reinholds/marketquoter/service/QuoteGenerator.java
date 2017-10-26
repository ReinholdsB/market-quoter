package uk.co.reinholds.marketquoter.service;

import org.springframework.stereotype.Component;
import uk.co.reinholds.marketquoter.dto.MarketOffer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.TreeSet;

@Component
public class QuoteGenerator {

    public Quote getQuote(String marketFileLocation, int loanAmount) {
        TreeSet<MarketOffer> marketOffers = readMarketOffersFromFile(marketFileLocation);
        return calculateBestQuote(marketOffers, loanAmount);
    }

    protected TreeSet<MarketOffer> readMarketOffersFromFile(String marketFileLocation) {
        TreeSet<MarketOffer> marketOffers = new TreeSet<>();

        try (BufferedReader br = new BufferedReader(new FileReader(marketFileLocation))) {

            br.lines().forEach(line -> {
                String[] marketOfferEntry = line.split(",");
                try {
                    String name = marketOfferEntry[0];
                    double rate = Double.valueOf(marketOfferEntry[1]);
                    int amount = Integer.valueOf(marketOfferEntry[2]);
                    marketOffers.add(new MarketOffer(name, rate, amount));
                } catch (NumberFormatException e) {
                    System.out.println("Skipping line=" + line + "! Not a valid MarketOffer entry");
                }
            });

        } catch (IOException e) {
            System.out.println("Failed to read file provided=" + marketFileLocation + "! Error message=" + e.getMessage());
        }
        if (marketOffers.isEmpty()) {
            throw new LoanRequestFailureException("Not enough market offers to fulfill loan request!");
        }
        return marketOffers;
    }


    protected Quote calculateBestQuote(TreeSet<MarketOffer> marketOffers, int loanAmount) {
        BigDecimal totalInterestRate = BigDecimal.ZERO;
        int residualAmountNeeded = loanAmount;

        while (residualAmountNeeded > 0) {
            MarketOffer offer = marketOffers.pollFirst();
            if (offer != null) {
                if (residualAmountNeeded - offer.getAmount() >= 0) {
                    totalInterestRate = totalInterestRate.add(
                            BigDecimal.valueOf(offer.getAmount())
                                    .divide(BigDecimal.valueOf(loanAmount), MathContext.DECIMAL32)
                                    .multiply(BigDecimal.valueOf(offer.getRate())));
                } else {
                    totalInterestRate = totalInterestRate.add(
                            BigDecimal.valueOf(offer.getAmount() - residualAmountNeeded)
                                    .divide(BigDecimal.valueOf(loanAmount), MathContext.DECIMAL32)
                                    .multiply(BigDecimal.valueOf(offer.getRate())));
                }
                residualAmountNeeded -= offer.getAmount();
            }
        }
        return new Quote(loanAmount, 36, totalInterestRate.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue());
    }
}
