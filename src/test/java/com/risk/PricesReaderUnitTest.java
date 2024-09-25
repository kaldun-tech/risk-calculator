package com.risk;

import java.util.List;
import java.util.ArrayList;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class PricesReaderUnitTest {
    private CSVParser simpleParser = CSVParser.parse("price\n100\n", CSVFormat.DEFAULT);

    @Test
    public void testGetPricesFromRecords_InvalidRecords() {
        Assertions.assertThrows(IllegalArgumentException, PricesReader.getPricesFromRecords(null, 0));
    }

    @Test
    public void testGetPricesFromRecords_InvalidIndex() {
        ArrayList<CSVRecord> empty = new ArrayList<>();
        Assertions.assertThrows(IllegalArgumentException, PricesReader.getPricesFromRecords(empty, -1));
    }

    @Test
    public void testGetPricesFromRecords_OutOfBounds() {
        List<CSVRecord> simple = simpleParser.getRecords();
        Assertion.assertEquals(1, simple.size());
        Assertions.assertThrows(IndexOutOfBoundsException, PricesReader.getPricesFromRecords(simple, 5));
    }

    @Test
    public void testGetPricesFromRecords_Simple() {
        List<CSVRecord> simple = simpleParser.getRecords();
        Assertion.assertEquals(1, simple.size());
        List<Double> prices = PricesReader.getPricesFromRecords(simple, 0);
        Assertion.assertEquals(1, prices.size());
        Assertion.assertEquals(100, prices.get(0));
    }


}