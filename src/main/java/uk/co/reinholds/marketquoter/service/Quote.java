package uk.co.reinholds.marketquoter.service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class Quote {
    private static final BigDecimal MONTHS_IN_A_YEAR = BigDecimal.valueOf(12);

    private final int amount;
    private final int periodInMonths;
    private final double yearlyInterestRate;
    private final double monthlyRepayment;

    public Quote(int amount, int periodInMonths, double yearlyInterestRate) {
        this.amount = amount;
        this.periodInMonths = periodInMonths;
        this.yearlyInterestRate = yearlyInterestRate;

        this.monthlyRepayment = calculateMonthlyRepayment(amount, periodInMonths, yearlyInterestRate)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }


    private BigDecimal calculateMonthlyRepayment(int amount, int months, double interestRate) {
        BigDecimal amountRemaining = BigDecimal.valueOf(amount);
        BigDecimal paidTillNow = BigDecimal.ZERO;
        BigDecimal periodInMonths = BigDecimal.valueOf(months);
        BigDecimal monthlyInterest = BigDecimal.valueOf(interestRate).divide(MONTHS_IN_A_YEAR, MathContext.DECIMAL32);

        BigDecimal baseMonthlyPayment = amountRemaining.divide(periodInMonths, MathContext.DECIMAL32);

        for (int i = 0; i < months; i++) {
            BigDecimal interestThisMonth = amountRemaining.multiply(monthlyInterest);
            BigDecimal thisMonthsPayment = baseMonthlyPayment.add(interestThisMonth);

            paidTillNow = paidTillNow.add(thisMonthsPayment);
            amountRemaining = amountRemaining.add(interestThisMonth).subtract(thisMonthsPayment);
        }
        return paidTillNow.divide(periodInMonths, MathContext.DECIMAL32);
    }


    public double getMonthlyRepayment() {
        return monthlyRepayment;
    }

    public double getTotalRepayment() {
        return monthlyRepayment * periodInMonths;
    }

    public int getAmount() {
        return amount;
    }

    public int getPeriodInMonths() {
        return periodInMonths;
    }

    public double getYearlyInterestRate() {
        return yearlyInterestRate;
    }

    public void print() {
        System.out.println("Requested amount: £" + amount);
        System.out.println("Rate: " + yearlyInterestRate * 100 + "%");
        System.out.println("Monthly repayment: £" + monthlyRepayment);
        System.out.println("Total repayment: £" + getTotalRepayment());
    }
}
