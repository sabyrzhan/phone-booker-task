package com.phonebookertask.rest.response;

import com.phonebookertask.service.dto.PhoneDTO;

import java.util.List;

public class PhoneModelResponse {
    private int id;
    private String name;
    private boolean available;
    private int totalCount;
    private List<BookResponse> holders;

    public PhoneModelResponse(PhoneDTO phoneDTO) {
        this.id = phoneDTO.model().getId();
        this.name = phoneDTO.model().getFullName();
        this.available = phoneDTO.model().getCount() - phoneDTO.books().size() > 0;
        this.totalCount = phoneDTO.model().getCount();
        this.holders = phoneDTO.books().stream().map(BookResponse::new).toList();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isAvailable() {
        return available;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public List<BookResponse> getHolders() {
        return holders;
    }
}
