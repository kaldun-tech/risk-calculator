import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Fetch Bitcoin data
        List<Double> bitcoinPrices = fetchBitcoinPrices();
        RiskAnalysis bitcoinRisk = new RiskAnalysis(bitcoinPrices);

        // Fetch Gold data
        List<Double> goldPrices = fetchGoldPrices();
        RiskAnalysis goldRisk = new RiskAnalysis(goldPrices);

        // Calculate and compare risks
        double bitcoinVaR = bitcoinRisk.calculateVaR(0.95);
        double goldVaR = goldRisk.calculateVaR(0.95);

        System.out.println("Bitcoin 95% VaR: " + bitcoinVaR);
        System.out.println("Gold 95% VaR: " + goldVaR);

        // Similar for CVaR
    }

    private static List<Double> fetchBitcoinPrices() {
        // Implement API call to fetch Bitcoin prices
    }

    private static List<Double> fetchGoldPrices() {
        // Implement API call to fetch Gold prices
    }
}