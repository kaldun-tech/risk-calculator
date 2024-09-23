import java.util.List;
import java.util.ArrayList;

public class Returns {
    private final List<Double> returnPercents;

    /** Create list of returns for historical prices in chronological order */
    public Returns(List<Double> dailyPrices) {
        if (dailyPrices == null || dailyPrices.size() < 2) {
            throw new ArgumentException("Cannot create returns for nvalid prices list");
        }
        returns = new ArrayList(dailyPrices.size() - 1);
        for (int i = dailyPrices.size() - 1; 0 < i; --i) {
            double currentPrice = dailyPrices.get(i);
            double previousPrice = dailyPrices.get(i - 1);
            double returnPercent = calculateReturnPercent(previousPrice, currentPrice);
            returnPercents.set(i - 1, returnPercent);
        }
    }

    public List<Double> getReturnPercents() {
        return returnPercents;
    }

    public List<Double> getSortedReturns() {
        // Use a standard sort
    }

    /** Calculates return for a previous value and current */
    public double calculateReturnPercent(double previousPrice, double currentPrice) {
        return (currentPrice - previousPrice) * 100.0 / previousPrice;
    }
}