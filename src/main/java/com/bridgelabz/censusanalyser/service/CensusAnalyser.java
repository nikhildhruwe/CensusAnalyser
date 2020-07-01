package com.bridgelabz.censusanalyser.service;

import com.bridgelabz.censusanalyser.exception.CensusAnalyserException;
import com.bridgelabz.censusanalyser.model.IndiaCensusCSV;
import com.bridgelabz.censusanalyser.model.IndiaStateCodeCSV;
import com.bridgelabz.csvjar.utility.CSVBuilderException;
import com.bridgelabz.csvjar.utility.CSVBuilderFactory;
import com.bridgelabz.csvjar.utility.ICSVBuilder;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

public class CensusAnalyser {
    List<IndiaCensusCSV> censusCSVList = null;
    List<IndiaStateCodeCSV> stateCodeCSVList = null;
    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        try {
            Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
            ICSVBuilder icsvBuilder = CSVBuilderFactory.craeteCSVBuilder();
            censusCSVList = icsvBuilder.getCSVFileList(reader, IndiaCensusCSV.class);
            return (censusCSVList.size());
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.INCORRECT_CSV_INPUT);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(), e.type.name());
        }
    }

    public int loadIndiaStateCode(String csvFilePath) throws CensusAnalyserException {
        try {
            Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
            ICSVBuilder icsvBuilder = CSVBuilderFactory.craeteCSVBuilder();
            stateCodeCSVList = icsvBuilder.getCSVFileList(reader, IndiaStateCodeCSV.class);
            return (stateCodeCSVList.size());
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.INCORRECT_CSV_INPUT);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(), e.type.name());
        }
    }

    private <E> int getCount(Iterator<E> iterator) {
        Iterable<E> csvStateIterable = () -> iterator;
        int numberOfEntries = (int) StreamSupport.stream(csvStateIterable.spliterator(), false).count();
        return numberOfEntries;
    }

    public String getStateWiseSortedCensusData() throws CensusAnalyserException {
      if (censusCSVList == null || censusCSVList.size() == 0){
          throw new CensusAnalyserException("No Census Data",CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
      }
        Comparator<IndiaCensusCSV> censusComparator = Comparator.comparing(census->census.state);
        this.sort(censusComparator);
        String sortedStateCensusJson = new Gson().toJson(censusCSVList);
        return sortedStateCensusJson;
    }

    public String getStateWiseSortedStateCodeData() {
        stateCodeCSVList.
                        sort(((Comparator<IndiaStateCodeCSV>)(code1, code2)->code1.
                                                                    stateCode.compareTo(code2.stateCode)));
        String sortedStateCensusJson = new Gson().toJson(stateCodeCSVList);
        return sortedStateCensusJson;
    }

    public String getPopulationWiseSortedCensusData() {
        Comparator<IndiaCensusCSV> censusComparator = Comparator.comparingInt(census->census.population);
        this.sort(censusComparator.reversed());
        String sortedStateCensusJson = new Gson().toJson(censusCSVList);
        return sortedStateCensusJson;
    }

    public String getDensityPerSqKmWiseSortedCensusData() {
        Comparator<IndiaCensusCSV> censusComparator = Comparator.comparingInt(census->census.densityPerSqKm);
        this.sort(censusComparator.reversed());
        String sortedStateCensusJson = new Gson().toJson(censusCSVList);
        return sortedStateCensusJson;
    }

    private void sort(Comparator<IndiaCensusCSV> censusComparator) {
        for (int i = 0; i < censusCSVList.size() - 1; i++){
            for (int j = 0; j < censusCSVList.size() -i -1 ;j++){
                IndiaCensusCSV census1 = censusCSVList.get(j);
                IndiaCensusCSV census2 = censusCSVList.get(j+1);
                if (censusComparator.compare(census1, census2) > 0){
                    censusCSVList.set(j, census2);
                    censusCSVList.set(j+1, census1);
                }
            }
        }
    }
}
