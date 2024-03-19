package com.phonebookertask.rest.response;

import java.util.List;

public record ErrorResponse(String error, List<String> errors) {
}
