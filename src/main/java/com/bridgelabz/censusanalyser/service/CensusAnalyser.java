package com.bridgelabz.censusanalyser.service;

import com.bridgelabz.censusanalyser.exception.CensusAnalyserException;
import com.bridgelabz.censusanalyser.dao.CensusDAO;
import com.bridgelabz.censusanalyser.model.IndiaCensusCSV;
import com.bridgelabz.censusanalyser.model.USCensusDataCSV;
import com.google.gson.Gson;

import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CensusAnalyser {
    public enum Country {INDIA, US}
    Map<String, CensusDAO> censusMap;

    public CensusAnalyser() {
    }

    /**
     * Method to load India Census data
     *
     * @return number of records if the file
     */
    public int loadCensusData(Country country, String... csvFilePath) throws CensusAnalyserException {
        censusMap = CensusAdapterFactory.getCensusData(country, csvFilePath);
        return censusMap.size();
    }

    /**
     * Method to sort census data state wise
     *
     * @throws CensusAnalyserException
     */
    public String getStateWiseSortedCensusData() throws CensusAnalyserException {
        List<CensusDAO> censusList = censusMap.values().stream().collect(Collectors.toList());
        if (censusList == null || censusList.size() == 0) {
            throw new CensusAnalyserException("No Census Data", CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
        Comparator<CensusDAO> censusComparator = Comparator.comparing(census -> census.state);
        this.sort(censusComparator, censusList);
        String sortedStateCensusJson = new Gson().toJson(censusList);
        return sortedStateCensusJson;
    }

    /**
     * Method to get state Code wise sorted data from stateCode.csv file
     */
    public String getStateWiseSortedStateCodeData() {
        List<CensusDAO> stateCodeList = censusMap.values().stream().collect(Collectors.toList());
        stateCodeList.
                sort(((Comparator<CensusDAO>) (code1, code2) -> code1.
                        stateCode.compareTo(code2.stateCode)));
        String sortedStateCensusJson = new Gson().toJson(stateCodeList);
        return sortedStateCensusJson;
    }

    // Method to sort census data according to population
    public List getPopulationWiseSortedCensusData(String jsonFilePath) throws CensusAnalyserException {
        List<CensusDAO> censusList = censusMap.values().stream().collect(Collectors.toList());
        Comparator<CensusDAO> censusComparator = Comparator.comparingInt(census -> census.population);
        this.sort(censusComparator.reversed(), censusList);
        String sortedStateCensusJson = new Gson().toJson(censusList);
        this.jsonWriter(sortedStateCensusJson, jsonFilePath);
        List jsonList = this.jsonReader(jsonFilePath);
        return jsonList;
    }

    /**
     * Method to sort census data according to population density.
     *
     * @return sorted json format  density wise.
     */
    public String getDensityPerSqKmWiseSortedCensusData() {
        List<CensusDAO> censusList = censusMap.values().stream().collect(Collectors.toList());
        Comparator<CensusDAO> censusComparator = Comparator.comparingInt(census -> (int) census.populationDensity);
        this.sort(censusComparator.reversed(), censusList);
        String sortedStateCensusJson = new Gson().toJson(censusList);
        return sortedStateCensusJson;
    }

    /**
     * Method to sort census data according to State Area.
     *
     * @return sorted data of census file.
     */
    public String getStateAreaWiseSortedCensusData() {
        List<CensusDAO> censusList = censusMap.values().stream().collect(Collectors.toList());
        Comparator<CensusDAO> censusComparator = Comparator.comparingInt(census -> (int) census.totalArea);
        this.sort(censusComparator.reversed(), censusList);
        String sortedStateCensusJson = new Gson().toJson(censusList);
        return sortedStateCensusJson;
    }


    /**
     * Method to read from json file
     *
     * @param jsonFilePath
     * @return list type from json file.
     * @throws CensusAnalyserException
     */
    public List jsonReader(String jsonFilePath) throws CensusAnalyserException {
        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new FileReader(jsonFilePath));
        } catch (FileNotFoundException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.FILE_NOT_FOUND);
        }
        IndiaCensusCSV[] users = new Gson().fromJson(bufferedReader, IndiaCensusCSV[].class);
        List<IndiaCensusCSV> csvUserList = Arrays.asList(users);
        return csvUserList;
    }

    /**
     * method to write in a json file.
     *
     * @param sortedStateCensusJson
     * @param jsonFilePath
     * @throws CensusAnalyserException
     */
    public void jsonWriter(String sortedStateCensusJson, String jsonFilePath) throws CensusAnalyserException {
        try (FileWriter writer = new FileWriter(jsonFilePath);) {
            writer.write(sortedStateCensusJson);
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.FILE_WRITER_ERROR);
        }
    }

    /**
     * bubble sort method.
     *
     * @param censusComparator
     */
    private void sort(Comparator<CensusDAO> censusComparator, List<CensusDAO> censusList) {
        for (int i = 0; i < censusList.size() - 1; i++) {
            for (int j = 0; j < censusList.size() - i - 1; j++) {
                CensusDAO census1 = censusList.get(j);
                CensusDAO census2 = censusList.get(j + 1);
                if (censusComparator.compare(census1, census2) > 0) {
                    censusList.set(j, census2);
                    censusList.set(j + 1, census1);
                }
            }
        }
    }

    /**
     * Method to sort by population from USCensusData.
     *
     * @return
     */
    public String getPopulationWiseSortedFromUSCensusData() {
        List<CensusDAO> usCensusList = censusMap.values().stream().collect(Collectors.toList());
        usCensusList.sort(((Comparator<CensusDAO>) (code1, code2) -> code1.
                population - code2.population).reversed());
        String sortedStateCensusJson = new Gson().toJson(usCensusList);
        return sortedStateCensusJson;
    }

    /**
     * Method to sort by population from USCensusData file.
     *
     * @return
     */
    public String getPopulationDensityWiseSortedFromUSCensusData() {
        List<CensusDAO> usCensusList = censusMap.values().stream().collect(Collectors.toList());
        usCensusList.sort(((Comparator<CensusDAO>) (code1, code2) -> (int) (code1.
                populationDensity - code2.populationDensity)).reversed());
        String sortedStateCensusJson = new Gson().toJson(usCensusList);
        return sortedStateCensusJson;
    }

    /**
     * Method to sort by total area wise from USCensusData file.
     *
     * @return
     */
    public String getAreaWiseSortedFromUSCensusData() {
        List<CensusDAO> usCensusList = censusMap.values().stream().collect(Collectors.toList());
        usCensusList.sort(((Comparator<CensusDAO>) (code1, code2) -> (int) (code1.totalArea - code2.totalArea)).reversed());
        String sortedStateCensusJson = new Gson().toJson(usCensusList);
        return sortedStateCensusJson;
    }

    /**
     * Method to analyse highest population density from us and india census file.
     *
     * @param sortedCensusData
     * @param sortedUSData
     * @return state with highest density
     */
    public String getMostPopulousStateWithDensityForIndiaAndUs(IndiaCensusCSV[] sortedCensusData
                                                                                , USCensusDataCSV[] sortedUSData) {
        if (sortedCensusData[0].densityPerSqKm > sortedUSData[0].populationDensity)
            return sortedCensusData[0].state;
        return sortedUSData[0].state;

    }
}
