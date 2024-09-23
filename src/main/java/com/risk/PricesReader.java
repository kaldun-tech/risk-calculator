import java.util.List;
import java.util.ArrayList;
import java.util.File;

public static class PricesReader {
    // Read CSV into file using Apache Commons CSV
    public static List<double> readPrices(String fileName) {
        CSVParser parser = CSVParser.parse(new File(fileName), CSVFormat.DEFAULT);
        List<double> prices = parser.getRecords;
        return prices;
    }
}