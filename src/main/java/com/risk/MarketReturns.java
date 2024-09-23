package main.java.com.risk;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class MarketReturns {
    private final ArrayList<Double> returnPercents;

    /** Create market returns from historical prices */
    public MarketReturns(List<Double> dailyPrices) {
        if (dailyPrices == null || dailyPrices.size() < 2) {
            throw new IllegalArgumentException("Cannot create returns for invalid prices list");
        }
        returns = new ArrayList<>(dailyPrices.size() - 1);
        buildReturnPercents(dailyPrices);
        sortReturnPercentsLowestToHighest();
    }

    private void buildReturnPercents(List<Double> dailyPrices) {
        for (int i = dailyPrices.size() - 1; 0 < i; --i) {
            double currentPrice = dailyPrices.get(i);
            double previousPrice = dailyPrices.get(i - 1);
            double returnPercent = calculateReturnPercent(previousPrice, currentPrice);
            returnPercents.set(i - 1, returnPercent);
        }
    }

    private void sortReturnPercentsLowestToHighest() {
        Collections.sort(returnPercents);
    }

    /** Gets the return percentages as a sorted array of doubles from lowest to highest */
    public double[] getReturnPercents() {
        return returnPercents.toArray(new double[returnPercents.size()]);
    }

    /** Calculates percent return for a previous value and current
     * @throws java.lang.IllegalArgumentException for non-positive previous or current price */
    public static double calculateReturnPercent(double previousPrice, double currentPrice) throws IllegalArgumentException {
        if (previousPrice <= 0) {
            throw new IllegalArgumentException("Invalid previousPrice");
        } else if (currentPrice <= 0) {
            throw new IllegalArgumentException("Invalid currentPrice");
        }
        return (currentPrice - previousPrice) * 100.0 / previousPrice;
    }
}