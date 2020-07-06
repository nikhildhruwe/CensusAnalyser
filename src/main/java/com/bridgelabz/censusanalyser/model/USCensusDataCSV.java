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

    public USCensusDataCSV() {
    }

    public USCensusDataCSV(String stateId, String state, int population, double totalArea, double populationDensity) {
        this.stateId = stateId;
        this.state = state;
        this.population = population;
        this.totalArea = totalArea;
        this.populationDensity = populationDensity;
    }

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
