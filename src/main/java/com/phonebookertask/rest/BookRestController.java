package com.phonebookertask.rest;

import com.phonebookertask.rest.request.PhoneBookRequest;
import com.phonebookertask.rest.response.ErrorResponse;
import com.phonebookertask.rest.response.PhoneModelResponse;
import com.phonebookertask.rest.response.SuccessResponse;
import com.phonebookertask.service.PhoneBookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Booking REST API", description = "Manage booking and return of the phone")
@RestController
@RequestMapping("/api/book")
public class BookRestController {
    private final PhoneBookService phoneBookService;

    public BookRestController(PhoneBookService phoneBookService) {
        this.phoneBookService = phoneBookService;
    }

    @Operation(
        summary = "Book the phone",
        description = "Book the phone by passing username and phone model ID."
    )
    @ApiResponse(
        responseCode = "200",
        description = "Success message",
        content = {@Content(schema = @Schema(implementation = SuccessResponse.class), mediaType = "application/json")}
    )
    @ApiResponse(
        responseCode = "404",
        description = "Phone not found with specified model id",
        content = {@Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")}
    )
    @ApiResponse(
        responseCode = "403",
        description = "Phone is unavailable to book",
        content = {@Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")}
    )
    @ApiResponse(
        responseCode = "400",
        description = "When username or modelID parameters are empty or invalid",
        content = {@Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")}
    )
    @PostMapping
    public SuccessResponse bookPhone(@Valid @RequestBody PhoneBookRequest request) {
        phoneBookService.book(request.username(), request.modelId());
        return new SuccessResponse("Phone booked");
    }

    @Operation(
        summary = "Return the phone",
        description = "Return the phone by passing username and phone model ID."
    )
    @ApiResponse(
        responseCode = "200",
        description = "Success message",
        content = {@Content(schema = @Schema(implementation = SuccessResponse.class), mediaType = "application/json")}
    )
    @ApiResponse(
        responseCode = "400",
        description = "When username or modelID parameters are empty or invalid",
        content = {@Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")}
    )
    @PostMapping("/return")
    public SuccessResponse returnPhone(@Valid @RequestBody PhoneBookRequest request) {
        phoneBookService.returnPhone(request.username(), request.modelId());
        return new SuccessResponse("Phone returned");
    }
}
