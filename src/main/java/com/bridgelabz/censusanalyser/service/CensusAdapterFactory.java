package com.bridgelabz.censusanalyser.service;

import com.bridgelabz.censusanalyser.dao.CensusDAO;
import com.bridgelabz.censusanalyser.exception.CensusAnalyserException;

import java.util.Map;

public class CensusAdapterFactory {
   public static Map<String, CensusDAO> getCensusData(CensusAnalyser.Country country, String... csvFilePath)
                                                                                        throws CensusAnalyserException{
       if (country.equals(CensusAnalyser.Country.INDIA))
           return new IndiaCensusAdapter().loadCensusData(csvFilePath);
       if (country.equals(CensusAnalyser.Country.US))
           return new USCensusAdapter().loadCensusData(csvFilePath);
       throw new CensusAnalyserException("Unknown Country", CensusAnalyserException.ExceptionType.INVALID_COUNTRY);

   }
}
