package com.risk;

import java.lang.IllegalArgumentException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

class MarketReturnsUnitTest {

    @Test
    void testMarketReturnsConstructor_null() {
        assertThrows(IllegalArgumentException.class, () -> new MarketReturns(null));
    }

    @Test
    void testMarketReturnsConstructor_empty() {
        List<Double> prices = PriceListBuilder.buildList(new double[0]);
        assertThrows(IllegalArgumentException.class, () -> new MarketReturns(prices));
    }

    @Test
    void testMarketReturnsConstructor_small() {
        List<Double> prices = PriceListBuilder.buildList(new double[]{0});
        assertThrows(IllegalArgumentException.class, () -> new MarketReturns(prices));
    }

    @Test
    void testCalculateReturnPercent_zeroPreviousPrice() {
        assertThrows(IllegalArgumentException.class, () -> MarketReturns.calculateReturnPercent(0, 100));
    }

    @Test
    void testCalculateReturnPercent_zeroCurrentPrice() {
        assertThrows(IllegalArgumentException.class, () -> MarketReturns.calculateReturnPercent(100, 0));
    }

    @Test
    void testCalculateReturnPercent_normalPrices() {
        assertEquals(0.1, MarketReturns.calculateReturnPercent(100, 110));
        assertEquals(-0.2, MarketReturns.calculateReturnPercent(100, 80));
    }

    @Test
    void testGetReturnPercentArr_simple() {
        List<Double> simplePrices = PriceListBuilder.buildList(PriceListBuilder.SIMPLE_PRICES);
        MarketReturns mr = new MarketReturns(simplePrices);
        double[] returnPercents = mr.getReturnPercents();
        assertEquals(1, returnPercents.length);
        assertEquals(0.1, returnPercents[0]);
    }

    @Test
    void testGetReturnPercentArr_Complex() {
        List<Double> complexPrices = PriceListBuilder.buildList(PriceListBuilder.COMPLEX_PRICES);
        MarketReturns mr = new MarketReturns(complexPrices);
        double[] returnPercents = mr.getReturnPercents();
        double[] expectedReturns = {-.75, 0, 1};
        assertEquals(3, returnPercents.length);
        for (int i = 0; i < returnPercents.length; ++i) {
            assertEquals(expectedReturns[i], returnPercents[i]);
        }
    }
}
