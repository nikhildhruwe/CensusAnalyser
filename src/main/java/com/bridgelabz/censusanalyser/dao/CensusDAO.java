package com.bridgelabz.censusanalyser.dao;

import com.bridgelabz.censusanalyser.model.IndiaCensusCSV;
import com.bridgelabz.censusanalyser.model.USCensusDataCSV;

public class CensusDAO {

    public double totalArea;
    public double populationDensity;
    public int population;
    public String state;
    public String stateCode;

    public CensusDAO(IndiaCensusCSV indiaCensusCSV) {
        state = indiaCensusCSV.state;
        totalArea = indiaCensusCSV.areaInSqKm;
        population = indiaCensusCSV.population;
        populationDensity = indiaCensusCSV.densityPerSqKm;
    }

    public CensusDAO(USCensusDataCSV usCensusDataCSV) {
        stateCode = usCensusDataCSV.stateId;
        state = usCensusDataCSV.state;
        population = usCensusDataCSV.population;
        totalArea = usCensusDataCSV.totalArea;
        populationDensity = usCensusDataCSV.populationDensity;
    }
}
