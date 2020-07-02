package com.bridgelabz.censusanalyser.service;

import com.bridgelabz.censusanalyser.model.IndiaCensusCSV;
import com.bridgelabz.censusanalyser.model.IndiaStateCodeCSV;

public class IndiaCensusDAO {
    public String state;
    public int areaInSqKm;
    public int population;
    public int densityPerSqKm;

    public String stateCode;
    public int srNo;
    public String stateName;
    public int tin;

    public IndiaCensusDAO(IndiaCensusCSV indiaCensusCSV) {
        state = indiaCensusCSV.state;
        areaInSqKm = indiaCensusCSV.areaInSqKm;
        population = indiaCensusCSV.population;
        densityPerSqKm = indiaCensusCSV.densityPerSqKm;
    }

   public IndiaCensusDAO(IndiaStateCodeCSV indiaStateCodeCSV) {
         stateCode = indiaStateCodeCSV.stateCode;
         srNo = indiaStateCodeCSV.srNo;
         stateName = indiaStateCodeCSV.stateName;
         tin = indiaStateCodeCSV.tin;
    }

//    public IndiaCensusDAO1(IndiaStateCodeCSV indiaStateCodeCSV) {
//
//    }
}
