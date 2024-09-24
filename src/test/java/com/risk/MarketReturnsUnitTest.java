package test.java.com.risk;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class MarketReturnsUnitTest {

    @Test
    void testMarketReturnsConstructor_null() {
        Assertions.assertThrows(IllegalArgumentException, new MarketReturns(null));
    }

    @Test
    void testMarketReturnsConstructor_empty() {
        ArrayList<Double> prices = PriceListBuilder.buildList(new double[]);
        Assertions.assertThrows(IllegalArgumentException, new MarketReturns(null));
    }

    @Test
    void testMarketReturnsConstructor_small() {
        ArrayList<Double> prices = PriceListBuilder.buildList(new double[]{0});
        Assertions.assertThrows(IllegalArgumentException, new MarketReturns(null));
    }

    @Test
    void testMarketReturnsConstructor_normal() {
        ArrayList<Double> prices = PriceListBuilder.buildList(new double[]{100, 110});
        Assertions.assertThrows(IllegalArgumentException, new MarketReturns(null));
    }

    @Test
    void testCalculateReturnPercent_zeroPreviousPrice() {
        Assertions.assertThrows(IllegalArgumentException, MarketReturns.calculateReturnPercent(0, 100));
    }

    @Test
    void testCalculateReturnPercent_zeroCurrentPrice() {
        Assertions.assertThrows(IllegalArgumentException, MarketReturns.calculateReturnPercent(100, 0));
    }

    @Test
    void testCalculateReturnPercent_normalPrices() {
        Assertions.assertEquals(10.0, MarketReturns.calculateReturnPercent(100, 110));
        Assertions.assertEquals(-20.0, MarketReturns.calculateReturnPercent(100, 80));
    }

    @Test
    void testGetReturnPercentArr_simple() {
        ArrayList<Double> simplePrices = PriceListBuilder.buildList(PriceListBuilder.SIMPLE_PRICE);
        MarketReturns mr = new MarketReturns(simplePrices);
        double[] returnPercents = mr.getReturnPercents();
        Assertions.assertEquals(new double[]{10}, returnPercents);
    }

    @Test
    void testGetReturnPercentArr_Complex() {
        ArrayList<Double> simplePrices = PriceListBuilder.buildList(PriceListBuilder.COMPLEX_PRICES);
        MarketReturns mr = new MarketReturns(simplePrices);
        double[] returnPercents = mr.getReturnPercents();
        Assertions.assertEquals(new double[]{-75, 0, 100}, returnPercents);
    }
}
