package com.risk;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

public class PriceListBuilder {
    public static final double[] SIMPLE_PRICES = new double[]{100, 110};
    public static final double[] COMPLEX_PRICES = new double[]{100, 200, 50, 50};
    public static final double[] ADVANCED_PRICES = new double[]{100, 102, 98, 101, 103, 97, 99, 102, 105, 101,
            98, 100, 104, 102, 101, 99, 97, 96, 99, 101};

    /** Test helper builds list of prices */
    public static List<Double> buildList(double[] arr) {
        ArrayList<Double> list = new ArrayList(arr.length);
        for (double d : arr) {
            list.add(d);
        }
        return list;
    }
}