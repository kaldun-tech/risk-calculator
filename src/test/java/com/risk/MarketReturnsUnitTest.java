package test.java.com.risk;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class MarketReturnsUnitTest {


    @Test
    void testMarketReturnsConstructor_Null() {
        Assertions.assertThrows(IllegalArgumentException, new MarketReturns(null));
    }

    @Test
    void testMarketReturnsConstructor_Empty() {
        ArrayList<Double> prices = buildPriceList(new double[]);
        Assertions.assertThrows(IllegalArgumentException, new MarketReturns(null));
    }

    @Test
    void testMarketReturnsConstructor_Small() {
        ArrayList<Double> prices = buildPriceList(new double[]{0});
        Assertions.assertThrows(IllegalArgumentException, new MarketReturns(null));
    }

    @Test
    void testMarketReturnsConstructor_Normal() {
        ArrayList<Double> prices = buildPriceList(new double[]{100, 110});
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
    void testCalculateReturnPercent_NormalPrices() {
        Assertions.assertEquals(10.0, MarketReturns.calculateReturnPercent(100, 110));
        Assertions.assertEquals(-20.0, MarketReturns.calculateReturnPercent(100, 80));
    }

    @Test
    void testGetReturnPercentArr_Simple() {
        ArrayList<Double> simplePrices = buildPriceList(new double[]{100, 110});
        MarketReturns mr = new MarketReturns(simplePrices);
        double[] returnPercents = mr.getReturnPercents();
        Assertions.assertEquals(new double[]{10}, returnPercents);
    }

    @Test
    void testGetReturnPercentArr_Complex() {
        ArrayList<Double> simplePrices = buildPriceList(new double[]{100, 200, 50, 50});
        MarketReturns mr = new MarketReturns(simplePrices);
        double[] returnPercents = mr.getReturnPercents();
        Assertions.assertEquals(new double[]{-75, 0, 100}, returnPercents);
    }

    /** Test helper builds list of prices */
    private void buildPriceList(double[] prices) {
        return new ArrayList<Double>(prices);
    }
}
