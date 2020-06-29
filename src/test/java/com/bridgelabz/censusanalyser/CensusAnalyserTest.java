package com.bridgelabz.censusanalyser;

import com.bridgelabz.censusanalyser.exception.CensusAnalyserException;
import com.bridgelabz.censusanalyser.service.CensusAnalyser;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

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

    //Test cases for the file IndiaStateCensusData.csv
    @Test
    public void givenIndianCensusCSVFile_WhenProper_ShouldReturnCorrectRecordCount() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numberOfRecords = censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(29,numberOfRecords);
        } catch (CensusAnalyserException e) {
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
             Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
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
            int numberOfRecords = censusAnalyser.loadIndiaStateCode(INDIA_STATE_CODE_CSV_FILE_PATH);
            Assert.assertEquals(37,numberOfRecords);
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
            censusAnalyser.loadIndiaCensusData(WRONG_STATE_CODE_CSV_FILE_PATH);
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
            censusAnalyser.loadIndiaCensusData(WRONG_STATE_CODE_FILE_EXTENSION);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void givenIndiaStateCodeData_WithIncorrectDelimiter_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaCensusData(INCORRECT_INPUT_STATE_CODE_CSV_FILE);
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
            censusAnalyser.loadIndiaCensusData(INCORRECT_INPUT_STATE_CODE_CSV_FILE);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.INCORRECT_CSV_INPUT, e.type);
            System.out.println(e.getMessage());
        }
    }
}
