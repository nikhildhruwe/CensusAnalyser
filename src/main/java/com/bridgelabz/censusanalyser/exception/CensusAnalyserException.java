package com.bridgelabz.censusanalyser.exception;

public class CensusAnalyserException extends Exception {
    public enum ExceptionType {
        CENSUS_FILE_PROBLEM, INCORRECT_CSV_INPUT,FILE_WRITER_ERROR,FILE_NOT_FOUND, INVALID_COUNTRY;
    }
    public ExceptionType type;

    public CensusAnalyserException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }

    public CensusAnalyserException(String message, String name) {
        super(message);
        this.type = ExceptionType.valueOf(name);
    }
}
