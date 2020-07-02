package com.bridgelabz.censusanalyser.service;

import com.bridgelabz.censusanalyser.exception.CensusAnalyserException;
import com.bridgelabz.censusanalyser.model.IndiaCensusCSV;
import com.bridgelabz.censusanalyser.model.IndiaStateCodeCSV;
import com.bridgelabz.csvjar.utility.CSVBuilderException;
import com.bridgelabz.csvjar.utility.CSVBuilderFactory;
import com.bridgelabz.csvjar.utility.ICSVBuilder;
import com.google.gson.Gson;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class CensusAnalyser {
    List<IndiaCensusDAO> censusList;
    List<IndiaCensusDAO> stateCodeList;

    Map <String,IndiaCensusDAO> indiaCensusDAOMap;
    Map <String, IndiaCensusDAO> indiaStateDAOMap;


    public CensusAnalyser() {
        this.censusList = new ArrayList<>();
        this.stateCodeList = new ArrayList<>();
        this.indiaCensusDAOMap = new HashMap<>();
        this.indiaStateDAOMap = new HashMap<>();


    }
    /**
     * Method to load India Census data
     * @return number of records if the file
     */
    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        try {
            Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
            ICSVBuilder csvBuilder = CSVBuilderFactory.craeteCSVBuilder();
            Iterator<IndiaCensusCSV> censusList = csvBuilder.getCSVFileIterator(reader, IndiaCensusCSV.class);
            Iterable<IndiaCensusCSV> indiaCensusCSVS = () -> censusList;
            StreamSupport.stream(indiaCensusCSVS.spliterator(), false).
                    forEach(csvCensus -> this.indiaCensusDAOMap.put(csvCensus.state,new IndiaCensusDAO(csvCensus)));
            return this.indiaCensusDAOMap.size();
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

    /**
     * Method to load IndiaStateCode.csv file
     * @return number of records of he file.
     */
    public int loadIndiaStateCode(String csvFilePath) throws CensusAnalyserException {
        try {
            Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
            ICSVBuilder icsvBuilder = CSVBuilderFactory.craeteCSVBuilder();
            Iterator<IndiaStateCodeCSV> stateCodeCSVIterator = icsvBuilder.getCSVFileIterator(reader, IndiaStateCodeCSV.class);
            Iterable<IndiaStateCodeCSV> csvIterable = () -> stateCodeCSVIterator;
            StreamSupport.stream(csvIterable.spliterator(), false).
                    forEach(csvCensus -> this.indiaStateDAOMap.put(csvCensus.stateName,new IndiaCensusDAO(csvCensus)));
            return this.indiaStateDAOMap.size();
//            while (stateCodeCSVIterator.hasNext())
//            {
//                count++;
//                IndiaStateCodeCSV stateCodeCSV = stateCodeCSVIterator.next();
//                IndiaCensusDAO censusDAO = indiaCensusDAOMap.get(stateCodeCSV.stateName);
//                if (censusDAO == null){
//                    censusDAO.stateCode = stateCodeCSV.stateCode;
//                }
//                //this.stateCodeList.add(new IndiaCensusDAO(stateCodeCSVIterator.next()));
//            }
//            return count;
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

    /**
     * Method to sort census data state wise
     * @throws CensusAnalyserException
     */
    public String getStateWiseSortedCensusData() throws CensusAnalyserException {
        censusList = indiaCensusDAOMap.values().stream().collect(Collectors.toList());

        if (censusList == null || censusList.size() == 0) {
            throw new CensusAnalyserException("No Census Data", CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
        Comparator<IndiaCensusDAO> censusComparator = Comparator.comparing(census -> census.state);
        this.sort(censusComparator);
        String sortedStateCensusJson = new Gson().toJson(censusList);
        return sortedStateCensusJson;
    }

    /**
     * Method to get state Code wise sorted data from stateCode.csv file
     */
    public String getStateWiseSortedStateCodeData() {
        stateCodeList = indiaStateDAOMap.values().stream().collect(Collectors.toList());
        stateCodeList.
                sort(((Comparator<IndiaCensusDAO>) (code1, code2) -> code1.
                        stateCode.compareTo(code2.stateCode)));
        String sortedStateCensusJson = new Gson().toJson(stateCodeList);
        return sortedStateCensusJson;
    }

    // Method to sort census data according to population
    public List getPopulationWiseSortedCensusData(String jsonFilePath) throws CensusAnalyserException {
        censusList = indiaCensusDAOMap.values().stream().collect(Collectors.toList());
        Comparator<IndiaCensusDAO> censusComparator = Comparator.comparingInt(census -> census.population);
        this.sort(censusComparator.reversed());
        String sortedStateCensusJson = new Gson().toJson(censusList);
        this.jsonWriter(sortedStateCensusJson, jsonFilePath);
        List jsonList = this.jsonReader(jsonFilePath);
        return jsonList;
    }

    /**Method to sort census data according to population density.
     * @return sorted json format  density wise.
     */
    public String getDensityPerSqKmWiseSortedCensusData() {
        censusList = indiaCensusDAOMap.values().stream().collect(Collectors.toList());
        Comparator<IndiaCensusDAO> censusComparator = Comparator.comparingInt(census -> census.densityPerSqKm);
        this.sort(censusComparator.reversed());
        String sortedStateCensusJson = new Gson().toJson(censusList);
        return sortedStateCensusJson;
    }

    /** Method to sort census data according to State Area.
     * @return sorted data of census file.
     */
    public String getStateAreaWiseSortedCensusData() {
        censusList = indiaCensusDAOMap.values().stream().collect(Collectors.toList());
        Comparator<IndiaCensusDAO> censusComparator = Comparator.comparingInt(census -> census.areaInSqKm);
        this.sort(censusComparator.reversed());
        String sortedStateCensusJson = new Gson().toJson(censusList);
        return sortedStateCensusJson;
    }


    /**
     * Method to read from json file
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
     * @param censusComparator
     */
    private void sort(Comparator<IndiaCensusDAO> censusComparator) {
        for (int i = 0; i < censusList.size() - 1; i++) {
            for (int j = 0; j < censusList.size() - i - 1; j++) {
                IndiaCensusDAO census1 = censusList.get(j);
                IndiaCensusDAO census2 = censusList.get(j + 1);
                if (censusComparator.compare(census1, census2) > 0) {
                    censusList.set(j, census2);
                    censusList.set(j + 1, census1);
                }
            }
        }
    }

}
