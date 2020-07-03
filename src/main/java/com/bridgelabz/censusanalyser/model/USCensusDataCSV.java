package com.bridgelabz.censusanalyser.model;

import com.opencsv.bean.CsvBindByName;

public class USCensusDataCSV {
    @CsvBindByName (column = "State Id", required = true)
    public String stateId;

    @CsvBindByName (column = "State", required = true)
    public String state;

    @CsvBindByName (column = "Population", required = true)
    public int population;


    @CsvBindByName (column = "Total area", required = true)
    public double totalArea;


    @CsvBindByName (column = "Population Density", required = true)
    public double populationDensity;

    @Override
    public String toString() {
        return "USCensusDataCSV{" +
                "stateId='" + stateId + '\'' +
                ", state='" + state + '\'' +
                ", population=" + population +
                ", totalArea=" + totalArea +
                ", populationDensity=" + populationDensity +
                '}';
    }
}
