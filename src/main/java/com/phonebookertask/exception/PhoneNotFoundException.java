package com.phonebookertask.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class PhoneNotFoundException extends ResponseStatusException {
    public PhoneNotFoundException() {
        super(HttpStatus.NOT_FOUND, "Phone not found");
    }
}
