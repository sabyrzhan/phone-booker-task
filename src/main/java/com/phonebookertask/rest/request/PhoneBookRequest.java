package com.phonebookertask.rest.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

public record PhoneBookRequest(@NotEmpty(message = "Username is empty") String username,
                               @Min(value = 1, message = "Model ID not set") int modelId) {
}
