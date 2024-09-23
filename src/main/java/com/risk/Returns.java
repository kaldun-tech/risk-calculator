import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Returns {
    private final List<Double> returnPercents;

    /** Create list of returns for historical prices in chronological order */
    public Returns(List<Double> dailyPrices) {
        if (dailyPrices == null || dailyPrices.size() < 2) {
            throw new ArgumentException("Cannot create returns for invalid prices list");
        }
        returns = new ArrayList(dailyPrices.size() - 1);
        buildReturnPercents(dailyPrices);
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

    /** Gets the return percentages as a sorted list of doubles from lowest to highest */
    public List<Double> getReturnPercents() {
        return returnPercents;
    }

    /** Calculates percent return for a previous value and current */
    public double calculateReturnPercent(double previousPrice, double currentPrice) {
        return (currentPrice - previousPrice) * 100.0 / previousPrice;
    }
}