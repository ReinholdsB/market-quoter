package uk.co.reinholds.marketquoter.dto;

public class MarketOffer implements Comparable<MarketOffer> {

    private final String name;
    private final Double rate;
    private final int amount;

    public MarketOffer(String name, double rate, int amount) {
        this.name = name;
        this.rate = rate;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public Double getRate() {
        return rate;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MarketOffer that = (MarketOffer) o;

        if (Double.compare(that.rate, rate) != 0) return false;
        if (amount != that.amount) return false;
        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = name != null ? name.hashCode() : 0;
        temp = Double.doubleToLongBits(rate);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + amount;
        return result;
    }

    @Override
    public int compareTo(MarketOffer o) {
        return this.rate.compareTo(o.rate);
    }
}
