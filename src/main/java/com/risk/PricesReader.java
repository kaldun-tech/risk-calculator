package com.risk;

import java.io.File;
import java.io.IOException;
import java.lang.IllegalArgumentException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.ArrayList;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class PricesReader {
    static String PRICE_HEADER = "price";

    /** Parses prices from a CSV file
     * @param fileName Name of file to read
     * @throws IOException if an I/O error occurs */
    public static List<Double> parsePricesFromCSV(String fileName) throws IOException {
        CSVParser parser = parseCSVFile(fileName);
        List<String> headerNames = parser.getHeaderNames();
        System.out.println("Header names: " + String.join(",", headerNames));
        int priceIndex = headerNames.indexOf("price");
        return getPricesFromRecords(parser.getRecords(), priceIndex);
    }

    /** Gets a list of prices from CSV records
     * @param csvRecords List of CSVRecords parsed from file - must be non-null with length greater than one
     * @param priceIndex Index of price field in the record, must be greater than zero
     * @throws java.lang.IllegalArgumentException For invalid input */
    public static List<Double> getPricesFromRecords(List<CSVRecord> csvRecords, int priceIndex) {
        if (csvRecords == null) {
            throw new IllegalArgumentException("csvRecords must be a valid list");
        } else if (csvRecords.size() < 2) {
            throw new IllegalArgumentException("csvRecords must have length greater than one");
        } else if (priceIndex < 0) {
            throw new IllegalArgumentException("priceIndex must be non-negative");
        }
        List<Double> priceList = new ArrayList(csvRecords.size());
        // Skip index zero for header
        for (int i = 1; i < csvRecords.size(); ++i) {
            CSVRecord next = csvRecords.get(i);
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
        System.out.println("Reading CSV file: " + fileName);
        return CSVParser.parse(new File(fileName), StandardCharsets.UTF_8, CSVFormat.DEFAULT
                .withFirstRecordAsHeader()
                .withIgnoreHeaderCase()
                .withTrim());
    }
}
