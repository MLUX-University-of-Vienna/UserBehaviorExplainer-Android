package com.example.perceptron_app_bachelorarbeit.javafiles;

import java.util.HashMap;

/**
 * Perceptron Data is a Data format for the events and the single cases - only used for CSV import
 */

public class PerceptronData {
    HashMap<Integer, String> values = new HashMap<>();
    HashMap<Integer, HashMap<Integer,String>> check = new HashMap<>();
    public PerceptronData() {

    }

    public PerceptronData(HashMap<Integer, String> values) {
        this.values = values;
    }

    public HashMap<Integer, String> getValues() {
        return values;
    }

    public void setValues(HashMap<Integer, String> values) {
        this.values = values;
    }
}
