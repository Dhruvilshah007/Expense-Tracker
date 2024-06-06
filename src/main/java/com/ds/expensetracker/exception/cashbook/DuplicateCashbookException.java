package com.ds.expensetracker.exception.cashbook;


public class DuplicateCashbookException extends RuntimeException {

    public DuplicateCashbookException(String message) {
        super(message);
    }
}