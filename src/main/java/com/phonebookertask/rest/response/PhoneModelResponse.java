package com.phonebookertask.rest.response;

import com.phonebookertask.model.PhoneModel;

import java.util.List;

public class PhoneModelResponse {
    private int id;
    private String name;
    private boolean available;
    private int totalCount;
    private List<BookResponse> holders;

    public PhoneModelResponse(PhoneModel phoneModel) {
        this.id = phoneModel.getId();
        this.name = phoneModel.getFullName();
        this.available = phoneModel.isAvailable();
        this.totalCount = phoneModel.getCount();
        this.holders = phoneModel.getBooks().stream().map(BookResponse::new).toList();
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
