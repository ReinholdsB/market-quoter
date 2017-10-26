package uk.co.reinholds.marketquoter.service;

import uk.co.reinholds.marketquoter.service.domain.MarketOffer;
import uk.co.reinholds.marketquoter.service.domain.Quote;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Optional;
import java.util.TreeSet;

public class QuoteGenerator {

    public Optional<Quote> getBestAvailableQuote(String marketFileLocation, int loanAmount) {
        TreeSet<MarketOffer> marketOffers = readMarketOffersFromFile(marketFileLocation);
        return calculateBestQuote(marketOffers, loanAmount);
    }

    protected TreeSet<MarketOffer> readMarketOffersFromFile(String marketFileLocation) {
        TreeSet<MarketOffer> marketOffers = new TreeSet<>();

        try (BufferedReader br = new BufferedReader(new FileReader(marketFileLocation))) {

            br.lines().skip(1).forEach(line -> {
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
        return marketOffers;
    }


    protected Optional<Quote> calculateBestQuote(TreeSet<MarketOffer> marketOffers, int loanAmount) {
        BigDecimal totalInterestRate = BigDecimal.ZERO;
        int notFilledYet = loanAmount;

        while (notFilledYet > 0) {
            MarketOffer offer = marketOffers.pollFirst();
            if (offer != null) {
                BigDecimal sizeNeeded;
                if (notFilledYet <= offer.getAmount()) {
                    sizeNeeded = BigDecimal.valueOf(notFilledYet);
                } else {
                    sizeNeeded = BigDecimal.valueOf(offer.getAmount());
                }
                totalInterestRate = totalInterestRate.add(
                        sizeNeeded.multiply(BigDecimal.valueOf(offer.getRate())));
                notFilledYet -= offer.getAmount();
            } else {
                return Optional.empty();
            }
        }
        totalInterestRate = totalInterestRate.divide(BigDecimal.valueOf(loanAmount), MathContext.DECIMAL32);
        return Optional.of(new Quote(
                loanAmount,
                36,
                totalInterestRate.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue()));
    }
}
