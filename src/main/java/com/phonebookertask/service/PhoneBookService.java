package com.phonebookertask.service;

public interface PhoneBookService {
    void book(String username, int modelId);
    void returnPhone(String username, int modelId);
}
