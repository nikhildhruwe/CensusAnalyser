package com.bridgelabz.censusanalyser.service;

import com.bridgelabz.censusanalyser.exception.CensusAnalyserException;
import com.bridgelabz.censusanalyser.dao.CensusDAO;
import com.bridgelabz.censusanalyser.model.IndiaCensusCSV;
import com.bridgelabz.censusanalyser.model.USCensusDataCSV;
import com.google.gson.Gson;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toCollection;

public class CensusAnalyser {
    public enum Country {INDIA, US}
    Map<String, CensusDAO> censusMap;
    private Country country;

    public CensusAnalyser(Country country) {
        this.country = country;
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
        if (censusMap == null || censusMap.size() == 0) {
            throw new CensusAnalyserException("No Census Data", CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
        Comparator<CensusDAO> censusComparator = Comparator.comparing(census -> census.state);
        ArrayList censusDTO = censusMap.values().stream().sorted(censusComparator)
                                .map(censusDAO -> censusDAO.getCensusDTO(country))
                                .collect(toCollection(ArrayList::new));
        String sortedStateCensusJson = new Gson().toJson(censusDTO);
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
        Comparator<CensusDAO> censusComparator = Comparator.comparing(census -> census.population);
        ArrayList censusDTO = censusMap.values().stream().sorted(censusComparator)
                .map(censusDAO -> censusDAO.getCensusDTO(country))
                .collect(toCollection(ArrayList::new));
        String sortedStateCensusJson = new Gson().toJson(censusDTO);
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
        Comparator<CensusDAO> censusComparator = Comparator.comparing(census -> census.populationDensity);
        ArrayList censusDTO = censusMap.values().stream().sorted(censusComparator.reversed())
                .map(censusDAO -> censusDAO.getCensusDTO(country))
                .collect(toCollection(ArrayList::new));
        String sortedStateCensusJson = new Gson().toJson(censusDTO);
        return sortedStateCensusJson;
    }

    /**
     * Method to sort census data according to State Area.
     *
     * @return sorted data of census file.
     */
    public String getStateAreaWiseSortedCensusData() {
        Comparator<CensusDAO> censusComparator = Comparator.comparing(census -> census.totalArea);
        ArrayList censusDTO = censusMap.values().stream().sorted(censusComparator.reversed())
                .map(censusDAO -> censusDAO.getCensusDTO(country))
                .collect(toCollection(ArrayList::new));
        String sortedStateCensusJson = new Gson().toJson(censusDTO);
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
        usCensusList.sort(((Comparator<CensusDAO>) (code1, code2) -> (int) (code1.totalArea - code2.totalArea))
                                                                                                        .reversed());
        String sortedStateCensusJson = new Gson().toJson(usCensusList);
        return sortedStateCensusJson;
    }

    /**
     * Method to analyse highest population density from us and india census file.
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
