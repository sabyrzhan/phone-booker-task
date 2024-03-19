package com.phonebookertask.rest;

import com.phonebookertask.repository.PhoneModelRepository;
import com.phonebookertask.rest.response.PhoneModelResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.StreamSupport;

@Tag(name = "Phones REST API", description = "List phones and their details (availability, holders)")
@RestController
@RequestMapping("/api/phones")
public class PhoneRestController {
    private final PhoneModelRepository phoneModelRepository;


    public PhoneRestController(PhoneModelRepository phoneModelRepository) {
        this.phoneModelRepository = phoneModelRepository;
    }

    @Operation(
        summary = "Retrieve list of phones",
        description = "Get the list of phones. Each phone contains details, availability and current holders."
    )
    @ApiResponse(
        responseCode = "200",
        content = {
            @Content(
                array = @ArraySchema(schema = @Schema(implementation = PhoneModelResponse.class)),
                mediaType = "application/json"
            )
        }
    )
    @GetMapping()
    public List<PhoneModelResponse> getPhones() {
        return StreamSupport.stream(phoneModelRepository.findAll().spliterator(), false).map(PhoneModelResponse::new).toList();
    }
}
