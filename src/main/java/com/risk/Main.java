package com.risk;

import java.io.IOException;
import java.util.List;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class Main {
    public static void main(String[] args) {
        Options options = new Options();
        buildOptions(options);
        CommandLineParser parser = new DefaultParser();

        try {
            CommandLine cmd = parser.parse(options, args);
            String inputFilePath = cmd.getOptionValue("i");
            double confidence = tryParseConfidence(cmd);
            double position = tryParsePosition(cmd);
            runRiskAnalysis(inputFilePath, position, confidence);
        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    private static void buildOptions(Options options) {
        options.addOption(new Option("i", "input", true, "Input file path"));
        options.addOption(new Option("c", "confidence", true, "Confidence level in range (0, 1]"));
        options.addOption(new Option("p", "position", true, "Position size in dollars"));
    }

    private static double tryParseConfidence(CommandLine cmd) {
        double confidence = RiskAnalysis.DEFAULT_CONFIDENCE;
        if (cmd.hasOption("c")) {
            String confStr = cmd.getOptionValue("c");
            try {
                confidence = Double.parseDouble(confStr);
            } catch (NumberFormatException e) {
                System.err.println("Failed to parse confidence from string: " + confStr);
            }
        }
        return confidence;
    }

    private static double tryParsePosition(CommandLine cmd) {
        double position = RiskAnalysis.DEFAULT_POSITIONSIZE;
        if (cmd.hasOption("p")) {
            String posStr = cmd.getOptionValue("p");
            try {
                position = Double.parseDouble(posStr);
            } catch (NumberFormatException e) {
                System.err.println("Failed to parse position from string: " + posStr);
            }
        }
        return position;
    }

    private static void runRiskAnalysis(String inputFilePath, double position, double confidence) throws IOException {
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
    }
}