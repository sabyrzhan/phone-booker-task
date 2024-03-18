package com.phonebookertask.rest;

import com.phonebookertask.rest.response.PhoneModelResponse;
import com.phonebookertask.service.PhoneBookService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/phones")
public class PhoneRestController {
    private final PhoneBookService phoneBookService;


    public PhoneRestController(PhoneBookService phoneBookService) {
        this.phoneBookService = phoneBookService;
    }

    @GetMapping()
    public List<PhoneModelResponse> getPhones() {
        return phoneBookService.getAvailablePhones().stream().map(PhoneModelResponse::new).toList();
    }
}
