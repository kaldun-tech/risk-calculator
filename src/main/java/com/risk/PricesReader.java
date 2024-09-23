import java.util.List;
import java.util.ArrayList;
import java.util.File;
import java.nio.charset.StandardCharsets;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import java.util.NoSuchElementException;
import java.lang.IllegalArgumentException;

public static class PricesReader {
    static String PRICE_HEADER = "price";

    /** Parses BTC prices from a file
     * @param fileName Name of file to read
     * @throws IOException if an I/O error occurs
     * @throws NoSuchElementException if price header is not found in file */
    public static List<Double> parseBTCPrices(String fileName) throws IOException, NoSuchElementException {
        CSVParser parser = parseCSVFile(fileName);
        List<String> headerNames = parser.getHeaderNames();
        int priceFieldIndex = headerNames.indexOf("price");
        return getPricesFromRecords(parser.getRecords(), priceIndex);
    }

    /** Gets a list of prices from CSV records
     * @param csvRecords List of CSVRecords parsed from file - must be non-null
     * @param priceIndex Index of price field in the record, must be greater than zero
     * @throws java.lang.IllegalArgumentException For invalid input */
    public static List<Double> getPricesFromRecords(List<CSVRecord> csvRecords, int priceIndex) {
        if (csvRecords == null) {
            throw new IllegalArgumentException("csvRecords must be a valid list");
        } else if (priceIndex < 0) {
            throw new IllegalArgumentException("priceIndex must be non-negative");
        }
        List<Double> priceList = new ArrayList(csvRecords.size());
        for (CSVRecord next : csvRecords) {
            String priceField = next.get(priceIndex);
            try {
                double price = Double.parseDouble(priceField);
                priceList.add(price);
            } catch (NumberFormatException e) {
                System.err.println("ERROR: Failed to parse price from " + priceField);
            }
        }
        return priceList;
    }

    /** Parse CSV file assuming UTF-8 encoding
     * @param fileName Name of file to parse
     * @throws java.lang.IllegalArgumentException for invalid input
     * @throws IOException if an I/O error occurs */
    private static CSVParser parseCSVFile(String fileName) throws IOException {
        return CSVParser.parse(new File(fileName), StandardCharsets.UTF_8, CSVFormat.DEFAULT);
    }
}