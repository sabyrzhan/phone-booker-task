package com.phonebookertask.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class PhoneUnavailableException extends ResponseStatusException {
    public PhoneUnavailableException() {
        super(HttpStatus.FORBIDDEN, "Phone is unavailable to book");
    }
}
