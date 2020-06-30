package com.bridgelabz.censusanalyser.service;

import com.bridgelabz.censusanalyser.exception.CensusAnalyserException;
import com.bridgelabz.censusanalyser.model.IndiaCensusCSV;
import com.bridgelabz.censusanalyser.model.IndiaStateCodeCSV;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.StreamSupport;

public class CensusAnalyser {
    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        try {
            Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
            Iterator<IndiaCensusCSV> censusCSVIterator = this.getCSVFileIterator(reader);
            Iterable<IndiaCensusCSV> csvIterable = () -> censusCSVIterator;
            int numberOfEntries = (int) StreamSupport.stream(csvIterable.spliterator(), false).count();
            return numberOfEntries;
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.INCORRECT_CSV_INPUT);
        }
    }

    public int loadIndiaStateCode(String csvFilePath) throws CensusAnalyserException {
        try {
            Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
            Iterator<IndiaStateCodeCSV> stateCodeCSVIterator = this.getStateCodeCSVFileIterator(reader);
            Iterable<IndiaStateCodeCSV> csvStateIterable = () -> stateCodeCSVIterator;
            int numberOfEntries = (int) StreamSupport.stream(csvStateIterable.spliterator(), false).count();
            return numberOfEntries;
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (IllegalStateException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE);
        } catch (RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.INCORRECT_CSV_INPUT);
        }
    }

   private Iterator<IndiaCensusCSV> getCSVFileIterator(Reader reader) throws CensusAnalyserException {
       try {
           CsvToBeanBuilder<IndiaCensusCSV> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
           csvToBeanBuilder.withType(IndiaCensusCSV.class);
           csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
           CsvToBean<IndiaCensusCSV> csvToBean = csvToBeanBuilder.build();
           return csvToBean.iterator();
       } catch (IllegalStateException e) {
           throw new CensusAnalyserException(e.getMessage(),
                   CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE);
       }
   }

    private Iterator<IndiaStateCodeCSV> getStateCodeCSVFileIterator(Reader reader) throws CensusAnalyserException {
        try {
            CsvToBeanBuilder<IndiaStateCodeCSV> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
            csvToBeanBuilder.withType(IndiaStateCodeCSV.class);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            CsvToBean<IndiaStateCodeCSV> csvToBean = csvToBeanBuilder.build();
            return csvToBean.iterator();
        } catch (IllegalStateException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE);
        }
    }
}
