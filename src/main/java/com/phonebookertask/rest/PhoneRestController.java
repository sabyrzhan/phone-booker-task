package com.phonebookertask.rest;

import com.phonebookertask.repository.PhoneModelRepository;
import com.phonebookertask.rest.response.PhoneModelResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api/phones")
public class PhoneRestController {
    private final PhoneModelRepository phoneModelRepository;


    public PhoneRestController(PhoneModelRepository phoneModelRepository) {
        this.phoneModelRepository = phoneModelRepository;
    }

    @GetMapping()
    public List<PhoneModelResponse> getPhones() {
        return StreamSupport.stream(phoneModelRepository.findAll().spliterator(), false).map(PhoneModelResponse::new).toList();
    }
}
