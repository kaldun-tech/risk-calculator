package com.risk;

import java.util.List;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class Main {
    public static void main(String[] args) {
        Options options = new Options();
        options.addOption(new Option("i", "input", true, "Input file path"));
        options.addOption(new Option("c", "confidence", true, "Confidence level in range (0, 1]"));
        options.addOption(new Option("p", "position", true, "Position size in dollars"));

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);

        String inputFilePath = cmd.getOptionValue("i");
        double confidence = cmd.hasOption("c") ? cmd.getOptionValue("c") : RiskAnalysis.DEFAULT_CONFIDENCE;
        double position = cmd.hasOption("p") ? cmd.getOptionValue("p") : RiskAnalysis.DEFAULT_POSITIONSIZE;

        try {
            List<Double> prices = PricesReader.parsePricesFromCSV(inputFilePath);
            RiskAnalysis ra = new RiskAnalysis(prices, position, confidence);
            StringBuilder sb = new StringBuilder("Computing risk analysis for position ")
                    .append(position)
                    .append(" and confidence ")
                    .append(confidence);
            System.out.println(sb.toString());
            System.out.println("Value at risk: " + ra.valueAtRisk());
            System.out.println("Conditional VaR: " + ra.conditionalValueAtRisk());
            System.out.println("Great success, high five!");
        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}