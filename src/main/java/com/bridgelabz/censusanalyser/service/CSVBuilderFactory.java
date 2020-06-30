package com.bridgelabz.censusanalyser.service;

public class CSVBuilderFactory {
    public static ICSVBuilder craeteCSVBuilder() {
       return new OpenCSVBuilder();
    }
}
