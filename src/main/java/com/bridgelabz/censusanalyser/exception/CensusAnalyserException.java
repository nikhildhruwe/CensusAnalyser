package com.bridgelabz.censusanalyser.exception;

public class CensusAnalyserException extends Exception {
    public enum ExceptionType {
        CENSUS_FILE_PROBLEM,UNABLE_TO_PARSE, INCORRECT_CSV_INPUT
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
