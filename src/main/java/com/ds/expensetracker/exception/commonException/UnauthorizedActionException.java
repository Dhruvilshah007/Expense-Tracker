package com.ds.expensetracker.exception.commonException;

public class UnauthorizedActionException extends RuntimeException {

    public UnauthorizedActionException(String message) {
        super(message);
    }

    public static UnauthorizedActionException forEntity(String actionType, String entityName) {
        return new UnauthorizedActionException(
                String.format("You are Unathorized to %s %s", actionType, entityName)
        );
    }
}
