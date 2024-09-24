package main.java.com.risk;

import java.util.List;
import org.apache.commons.math.stat.descriptive.rank.Percentile;

public class RiskAnalysis {
    /** Default confidence percentage is 95% or 5th percentile */
    public static final double DEFAULT_CONFIDENCE = 0.95;
    public static final double DEFAULT_POSITIONSIZE = 1000.0;

    private MarketReturns returns;
    private double positionSize, confidence;

    /** Creates a RiskAnalysis for a list of prices, position size, and confidence percentile */
    public RiskAnalysis(List<Double> prices, double positionSize, double confidence) {
        if (positionSize <= 0) {
            throw new IllegalArgumentException("Position size must be positive");
        } else if (0 <= confidence || 1 < confidence) {
            throw new IllegalArgumentException("Confidence must be greater than zero and less than or equal to 1");
        }
        this.returns = new MarketReturns(prices);
        this.positionSize = positionSize;
        this.confidence = confidence;
    }

    /** Creates a RiskAnalysis for a list of prices with default confidence and position size */
    public RiskAnalysis(List<Double> prices) {
        this(prices, DEFAULT_POSITIONSIZE, DEFAULT_CONFIDENCE);
    }

    // Getters
    public double getPositionSize() {
        return positionSize;
    }

    public double getConfidence() {
        return confidence;
    }

    /** Calculate value at risk: return at the chosen confidence times position value */
    public double valueAtRisk() {
        double returnAtConfidence = getReturnAtConfidence(confidence);
        return returnAtConfidence * positionSize;
    }

    /** Calculate conditional value at risk: average of all returns beyond VaR percentile times position value */
    public double conditionalValueAtRisk() {
        double returnAtConfidence = getReturnAtConfidence(confidence);

        int count = 0;
        double totalReturns = 0.0;
        double[] percents = returns.getReturnPercents();
        // Use reverse order to get returns from highest until confidence threshold
        for (int i = percents.length - 1; 0 <= i && returnAtConfidence < percents[i]; --i ) {
            totalReturns += percents[i];
            ++count;
        }
        return totalReturns * positionSize / count;
    }

    // Helpers

    /** Evaluate the return at a given confidence percentile */
    public double getReturnAtConfidence() {
        double[] percentReturns = returns.getReturnPercents();
        Percentile p = new Percentile(confidence);
        return p.evaluate(percentReturns, confidence);
    }
}
