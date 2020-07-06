package com.bridgelabz.censusanalyser.service;

import com.bridgelabz.censusanalyser.dao.CensusDAO;
import com.bridgelabz.censusanalyser.exception.CensusAnalyserException;
import com.bridgelabz.censusanalyser.model.IndiaCensusCSV;
import com.bridgelabz.censusanalyser.model.USCensusDataCSV;

import java.util.Map;

public class USCensusAdapter  extends CensusAdapter{

    @Override
    public Map<String, CensusDAO> loadCensusData(String... csvFilePath) throws CensusAnalyserException {
        return super.loadCensusData(USCensusDataCSV.class, csvFilePath[0]);
    }
}
