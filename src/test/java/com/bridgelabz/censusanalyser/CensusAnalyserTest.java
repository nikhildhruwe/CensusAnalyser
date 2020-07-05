package com.bridgelabz.censusanalyser;

import com.bridgelabz.censusanalyser.exception.CensusAnalyserException;
import com.bridgelabz.censusanalyser.model.IndiaCensusCSV;
import com.bridgelabz.censusanalyser.model.IndiaStateCodeCSV;
import com.bridgelabz.censusanalyser.model.USCensusDataCSV;
import com.bridgelabz.censusanalyser.service.CensusAnalyser;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;

public class CensusAnalyserTest {

    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String WRONG_FILE_EXTENSION = "./src/test/resources/IndiaStateCensusData.jpg";
    private static final String INCORRECT_INPUT_CSV_FILE = "./src/test/resources/IndiaStateCensusDataIncorrect.csv";

    private static final String INDIA_STATE_CODE_CSV_FILE_PATH = "./src/test/resources/IndiaStateCode.csv";
    private static final String WRONG_STATE_CODE_CSV_FILE_PATH = "./src/main/resources/IndiaStateCodeData.csv";
    private static final String WRONG_STATE_CODE_FILE_EXTENSION = "./src/test/resources/IndiaStateCodeData.jpg";
    private static final String INCORRECT_INPUT_STATE_CODE_CSV_FILE = "./src/test/resources/IndiaStateCodeIn" +
            "correct.csv";
    private static final String JSON_POPULATION_FILE_PATH = "./censusPopulation.json";
    private static final String JSON_POPULATION_WRONG_FILE_PATH = "./src/main/resources/censusPopulation.json";
    private static final String JSON_POPULATION_DENSITY_FILE_PATH = "./censusPopulationDensity.json";
    private static final String JSON_STATE_AREA_FILE_PATH = "./censusStateAreaSorted.json";

    private static final String US_CENSUS_FILE_PATH = "./src/test/resources/USCensusData.csv";

    //Test cases for the file IndiaStateCensusData.csv
    @Test
    public void givenIndianCensusCSVFile_WhenProper_ShouldReturnCorrectRecordCount() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numberOfRecords = censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(29, numberOfRecords);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFile_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaCensusData(WRONG_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFileExtension_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaCensusData(WRONG_FILE_EXTENSION);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void givenIndiaCensusData_WithIncorrectDelimiter_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaCensusData(INCORRECT_INPUT_CSV_FILE);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.INCORRECT_CSV_INPUT, e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WithIncorrectHeader_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaCensusData(INCORRECT_INPUT_CSV_FILE);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.INCORRECT_CSV_INPUT, e.type);
            System.out.println(e.getMessage());
        }
    }

    //Test cases for IndiaStateCode.csv file
    @Test
    public void givenIndianStateCodeFile_WhenProper_ShouldReturnCorrectRecordsCount() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            int numberOfRecords = censusAnalyser.loadIndiaStateCode(INDIA_STATE_CODE_CSV_FILE_PATH);
            Assert.assertEquals(29, numberOfRecords);
        } catch (CensusAnalyserException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void givenIndianStateCodeFile_WithWrongFilePath_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaStateCode(WRONG_STATE_CODE_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void givenIndiaStateCodeData_WithWrongFileExtension_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaStateCode(WRONG_STATE_CODE_FILE_EXTENSION);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void givenIndiaStateCodeData_WithIncorrectDelimiter_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaStateCode(INCORRECT_INPUT_STATE_CODE_CSV_FILE);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.INCORRECT_CSV_INPUT, e.type);
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void givenIndiaStateCodeData_WithIncorrectHeader_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaStateCode(INCORRECT_INPUT_STATE_CODE_CSV_FILE);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.INCORRECT_CSV_INPUT, e.type);
            System.out.println(e.getMessage());
        }
    }

    // UC3
    @Test
    public void givenIndiaCensusData_WhenSortedByState_ShouldReturnSortedResult() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData();
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Andhra Pradesh", censusCSV[0].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    // UC4
    @Test
    public void givenIndiaStateCodeData_WhenSortedByStateCode_ShouldReturnSortedResult() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            censusAnalyser.loadIndiaStateCode(INDIA_STATE_CODE_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getStateWiseSortedStateCodeData();
            IndiaStateCodeCSV[] stateCodeCSV = new Gson().fromJson(sortedCensusData, IndiaStateCodeCSV[].class);
            Assert.assertEquals("AP", stateCodeCSV[0].stateCode);
        } catch (CensusAnalyserException e) {
            System.out.println(e.getMessage());
        }
    }

    //UC5
    @Test
    public void givenIndiaCensusData_WhenSortedByPopulation_IfProper_ShouldReturnNumberOfRecords() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            List sortedCensusData = censusAnalyser.getPopulationWiseSortedCensusData(JSON_POPULATION_FILE_PATH);
            Assert.assertEquals(29, sortedCensusData.size());
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndiaCensusData_WhenSortedByPopulation_IfNotProper_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            censusAnalyser.jsonReader(JSON_POPULATION_WRONG_FILE_PATH);
        } catch (CensusAnalyserException e) {
            System.out.println(e.getMessage());
        }
    }

    // UC6
    @Test
    public void givenIndiaCensusData_WhenSortedByPopulationDensity_ShouldReturnSortedResultInDescendingOrder() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getDensityPerSqKmWiseSortedCensusData();
            censusAnalyser.jsonWriter(sortedCensusData, JSON_POPULATION_DENSITY_FILE_PATH);
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Bihar", censusCSV[0].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    //UC7
    @Test
    public void givenIndiaCensusData_WhenSortedByStateArea_ShouldReturnSortedResultInDescendingOrder() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getStateAreaWiseSortedCensusData();
            censusAnalyser.jsonWriter(sortedCensusData, JSON_STATE_AREA_FILE_PATH);
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Rajasthan", censusCSV[0].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

// USCensusData test cases

    @Test
    public void givenUSCensusCSVFile_WhenProper_ShouldReturnCorrectRecordCount() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numberOfRecords = censusAnalyser.loadUSCensusData(US_CENSUS_FILE_PATH);
            Assert.assertEquals(51, numberOfRecords);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    // UC9
    @Test
    public void givenUSCensusData_WhenSortedByPopulation_ShouldReturnSortedResultInDescendingOrder() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadUSCensusData(US_CENSUS_FILE_PATH);
            String sortedCensusData = censusAnalyser.getPopulationWiseSortedFromUSCensusData();
            USCensusDataCSV[] usCensusDataCSVS = new Gson().fromJson(sortedCensusData, USCensusDataCSV[].class);
            Assert.assertEquals("California", usCensusDataCSVS[0].state);
        } catch (CensusAnalyserException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void givenUSCensusData_WhenSortedByPopulationDensity_ShouldReturnSortedResultInDescendingOrder() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadUSCensusData(US_CENSUS_FILE_PATH);
            String usCensusData = censusAnalyser.getPopulationDensityWiseSortedFromUSCensusData();
            USCensusDataCSV[] usCensusDataCSV = new Gson().fromJson(usCensusData, USCensusDataCSV[].class);
            Assert.assertEquals("District of Columbia", usCensusDataCSV[0].state);
        } catch (CensusAnalyserException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void givenUSCensusData_WhenSortedByTotalArea_ShouldReturnSortedResultInDescendingOrder() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadUSCensusData(US_CENSUS_FILE_PATH);
            String sortedCensusData = censusAnalyser.getAreaWiseSortedFromUSCensusData();
            USCensusDataCSV[] usCensusDataCSVS = new Gson().fromJson(sortedCensusData, USCensusDataCSV[].class);
            Assert.assertEquals("Alaska", usCensusDataCSVS[0].state);
        } catch (CensusAnalyserException e) {
            System.out.println(e.getMessage());
        }
    }
    
    //UC11

    @Test
    public void givenIndiaAndUSCensusData_WhenSortedByPopulation_ShouldReturnHighestDensityState() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            censusAnalyser.loadUSCensusData(US_CENSUS_FILE_PATH);
            String sortedCensusData = censusAnalyser.getDensityPerSqKmWiseSortedCensusData();
            String sortedUSData = censusAnalyser.getPopulationDensityWiseSortedFromUSCensusData();
            String state = censusAnalyser
                                .getMostPopulousStateWithDensityForIndiaAndUs(sortedCensusData, sortedUSData);
            Assert.assertEquals("District of Columbia",state);
        }catch (CensusAnalyserException e){
            e.printStackTrace();
        }
    }
}
