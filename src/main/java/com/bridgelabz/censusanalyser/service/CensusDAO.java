package com.bridgelabz.censusanalyser.service;

import com.bridgelabz.censusanalyser.model.IndiaCensusCSV;
import com.bridgelabz.censusanalyser.model.IndiaStateCodeCSV;
import com.bridgelabz.censusanalyser.model.USCensusDataCSV;
import com.opencsv.bean.CsvBindByName;

public class CensusDAO {
    public Integer housingUnits;
    public Double waterArea;
    public Double totalArea;
    public Double populationDensity;
    public Double landArea;
    public Integer hosingDensity;
    public String stateId;

    public String state;
    public int areaInSqKm;
    public int population;
    public int densityPerSqKm;

    public String stateCode;
    public int srNo;
    public String stateName;
    public int tin;

    public CensusDAO(IndiaCensusCSV indiaCensusCSV) {
        state = indiaCensusCSV.state;
        areaInSqKm = indiaCensusCSV.areaInSqKm;
        population = indiaCensusCSV.population;
        densityPerSqKm = indiaCensusCSV.densityPerSqKm;
    }

   public CensusDAO(IndiaStateCodeCSV indiaStateCodeCSV) {
         stateCode = indiaStateCodeCSV.stateCode;
         srNo = indiaStateCodeCSV.srNo;
         stateName = indiaStateCodeCSV.stateName;
         tin = indiaStateCodeCSV.tin;
    }

    public CensusDAO(USCensusDataCSV usCensusDataCSV) {
        stateId = usCensusDataCSV.stateId;
        state = usCensusDataCSV.state;
        population = usCensusDataCSV.population;
        housingUnits = usCensusDataCSV.housingUnits;
        totalArea = usCensusDataCSV.totalArea;
        waterArea = usCensusDataCSV.waterArea;
        landArea = usCensusDataCSV.landArea;
        populationDensity = usCensusDataCSV.populationDensity;
        hosingDensity = usCensusDataCSV.housingUnits;

    }
}
