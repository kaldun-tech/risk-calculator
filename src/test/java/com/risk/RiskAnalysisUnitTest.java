package com.risk;

import java.lang.IllegalArgumentException;
import java.util.List;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.math.RoundingMode;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RiskAnalysisUnitTest {
    private static final List<Double> SIMPLE_PRICES = PriceListBuilder.buildList(PriceListBuilder.SIMPLE_PRICES);
    private static final List<Double> ADVANCED_PRICES = PriceListBuilder.buildList(PriceListBuilder.ADVANCED_PRICES);
    private static final double EXP_VAR_PERCENTILE = -0.0583;
    private static final double ADV_EXP_VAR = 58.25;

    // Invalid prices list tested in MarketReturnsUnitTest

    @Test
    public void testRiskAnalysisConstructor_invalidPositionSize() {
        assertThrows(IllegalArgumentException.class, () -> new RiskAnalysis(SIMPLE_PRICES, -10, 1));
        assertThrows(IllegalArgumentException.class, () -> new RiskAnalysis(SIMPLE_PRICES, 0, 1));
    }

    @Test
    public void testRiskAnalysisConstructor_invalidConfidence() {
        assertThrows(IllegalArgumentException.class, () -> new RiskAnalysis(SIMPLE_PRICES, 100, -5));
        assertThrows(IllegalArgumentException.class, () -> new RiskAnalysis(SIMPLE_PRICES, 100, 0));
        assertThrows(IllegalArgumentException.class, () -> new RiskAnalysis(SIMPLE_PRICES, 100, 1.000001));
        assertThrows(IllegalArgumentException.class, () -> new RiskAnalysis(SIMPLE_PRICES, 100, 2));
    }

    @Test
    public void testRiskAnalysisGetters() {
        RiskAnalysis simple = new RiskAnalysis(SIMPLE_PRICES);
        assertEquals(RiskAnalysis.DEFAULT_POSITIONSIZE, simple.getPositionSize());
        assertEquals(RiskAnalysis.DEFAULT_CONFIDENCE, simple.getConfidence());
    }

    @Test
    public void testRiskAnalysisAdvancedMetrics() {
        RiskAnalysis advanced = new RiskAnalysis(ADVANCED_PRICES);
        double returnAtTargetPercentile = advanced.getReturnAtTargetPercentile();
        assertEquals(EXP_VAR_PERCENTILE, round(returnAtTargetPercentile, 4));
        assertEquals(ADV_EXP_VAR, round(advanced.valueAtRisk(), 2));
        assertEquals(ADV_EXP_VAR, round(advanced.conditionalValueAtRisk(), 2));
    }

    private static double round(double value, int places) {
        BigDecimal bigD = new BigDecimal(Double.toString(value));
        bigD = bigD.setScale(places, RoundingMode.HALF_UP);
        return bigD.doubleValue();
    }
}