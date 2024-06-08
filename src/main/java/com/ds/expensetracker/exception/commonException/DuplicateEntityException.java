package com.ds.expensetracker.exception.commonException;

public class DuplicateEntityException extends RuntimeException {

    public DuplicateEntityException(String message) {
        super(message);
    }

    public static DuplicateEntityException forEntity(String entityName, String fieldName, String fieldValue) {
        return new DuplicateEntityException(
                String.format("%s with %s '%s' already exists", entityName, fieldName, fieldValue)
        );
    }
}