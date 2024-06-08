package com.ds.expensetracker.exception.commonException;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;


@Getter
public class ApplicationException extends RuntimeException {
    private final HttpStatusCode status;

    private final String errorTitle;
    private final String description;

    public ApplicationException(HttpStatusCode status, String errorTitle, String description) {
        super();
        this.status = status;
        this.errorTitle = errorTitle;
        this.description = description;
    }

}
