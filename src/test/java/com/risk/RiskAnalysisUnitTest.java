package com.risk;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.List;

public class RiskAnalaysisUnitTest {
    private static final List<Double> SIMPLE_PRICE = PriceListBuilder.buildList(PriceListBuilder.SIMPLE_PRICE);
    private static final List<Double> ADVANCED_PRICES = PriceListBuilder.buildList(PriceListBuilder.COMPLEX_PRICES);
    private static final double ADV_EXP_VAR = -3.92;
    private static final double ADV_EXP_CVAR = -4.9;

    // Invalid prices list tested in MarketReturnsUnitTest

    @Test
    public void testRiskAnalysisConstructor_invalidPositionSize() {
        Assertions.assertThrows(new RiskAnalysis(SIMPLE_PRICE, -10, 1));
        Assertions.assertThrows(new RiskAnalysis(SIMPLE_PRICE, 0, 1));
    }

    @Test
    public void testRiskAnalysisConstructor_invalidConfidence() {
        Assertions.assertThrows(new RiskAnalysis(SIMPLE_PRICE, 100, -5));
        Assertions.assertThrows(new RiskAnalysis(SIMPLE_PRICE, 100, 0));
        Assertions.assertThrows(new RiskAnalysis(SIMPLE_PRICE, 100, 1.000001));
        Assertions.assertThrows(new RiskAnalysis(SIMPLE_PRICE, 100, 2));
    }

    @Test
    public void testRiskAnalysisGetters() {
        RiskAnalysis simple = new RiskAnalysis(SIMPLE_PRICE);
        Assertions.assertEquals(RiskAnalysis.DEFAULT_POSITIONSIZE, simple.getPositionSize());
        Assertions.assertEquals(RiskAnalysis.DEFAULT_CONFIDENCE, simple.getConfidence());
    }

    @Test
    public void testRiskAnalaysisAdvancedMetrics() {
        RiskAnalysis advanced = new RiskAnalysis(ADVANCED_PRICES);
        double returnAtConfidence = advanced.getReturnAtConfidence();
        double positionSize = advanced.getPositionSize();
        Assertions.assertEquals(ADV_EXP_VAR, returnAtConfidence);

        Assertions.assertEquals(ADV_EXP_VAR * positionSize, advanced.valueAtRisk());

        Assertions.assertEquals(ADV_EXP_CVAR * positionSize, advanced.conditionalValueAtRisk());
    }
}