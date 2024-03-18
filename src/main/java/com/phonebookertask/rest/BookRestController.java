package com.phonebookertask.rest;

import com.phonebookertask.rest.request.PhoneBookRequest;
import com.phonebookertask.rest.response.SuccessResponse;
import com.phonebookertask.service.PhoneBookService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/book")
public class BookRestController {
    private final PhoneBookService phoneBookService;

    public BookRestController(PhoneBookService phoneBookService) {
        this.phoneBookService = phoneBookService;
    }

    @PostMapping
    public SuccessResponse bookPhone(@Valid @RequestBody PhoneBookRequest request) {
        phoneBookService.book(request.username(), request.modelId());
        return new SuccessResponse("Phone booked");
    }

    @PostMapping("/return")
    public SuccessResponse returnPhone(@Valid @RequestBody PhoneBookRequest request) {
        phoneBookService.returnPhone(request.username(), request.modelId());
        return new SuccessResponse("Phone returned");
    }
}
