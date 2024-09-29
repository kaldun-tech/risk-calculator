package com.risk;

import java.util.List;

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
        } else if (confidence <= 0 || 1 < confidence) {
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

    /** Calculate value at risk: opposite of return (loss) at the target percentile times position value */
    public double valueAtRisk() {
        double returnAtTargetPercentile = getReturnAtTargetPercentile();
        return -returnAtTargetPercentile * positionSize;
    }

    /** Calculate conditional value at risk: average of all returns (losses) worse than VaR percentile times position value */
    public double conditionalValueAtRisk() {
        double minimumReturn = getReturnAtTargetPercentile();
        return -getAverageReturnsWorseThan(minimumReturn) * positionSize;
    }

    // Helpers

    /** Evaluate the return at target percentile */
    public double getReturnAtTargetPercentile() {
        double[] percentReturns = returns.getReturnPercents();
        int index = (int) Math.ceil(percentReturns.length * (1 - confidence)) - 1;
        return percentReturns[index];
    }

    /** Gets the average returns of all returns worse than the maximum value.
     * This relies on returns being sorted lowest to highest. */
    private double getAverageReturnsWorseThan(double maximum) {
        int count = 0;
        double totalReturns = 0.0;
        double[] percents = returns.getReturnPercents();
        for (int i = 0; i < percents.length && percents[i] <= maximum; ++i ) {
            totalReturns += percents[i];
            ++count;
        }
        return totalReturns / count;
    }
}
