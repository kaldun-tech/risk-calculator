package com.risk;

import java.io.IOException;
import java.lang.IllegalArgumentException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

class PricesReaderUnitTest {
    private CSVParser simpleParser = null;
    static {
        try {
            CSVParser.parse("price\n100\n", CSVFormat.DEFAULT);
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    public void testGetPricesFromRecords_InvalidRecords() {
        assertThrows(IllegalArgumentException.class, () -> PricesReader.getPricesFromRecords(null, 0));
    }

    @Test
    public void testGetPricesFromRecords_InvalidIndex() {
        List<CSVRecord> empty = new ArrayList<>();
        assertThrows(IllegalArgumentException.class, () -> PricesReader.getPricesFromRecords(empty, -1));
    }

    @Test
    public void testGetPricesFromRecords_OutOfBounds() {
        List<CSVRecord> simple = simpleParser.getRecords();
        assertEquals(1, simple.size());
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> PricesReader.getPricesFromRecords(simple, 5));
    }

    @Test
    public void testGetPricesFromRecords_Simple() {
        List<CSVRecord> simple = simpleParser.getRecords();
        assertEquals(1, simple.size());
        List<Double> prices = PricesReader.getPricesFromRecords(simple, 0);
        assertEquals(1, prices.size());
        assertEquals(100, prices.get(0));
    }
}