package com.phonebookertask.service;

import com.phonebookertask.service.dto.PhoneDTO;

import java.util.List;

public interface PhoneBookService {
    List<PhoneDTO> getAvailablePhones();
    void book(String username, int modelId);
    void returnPhone(String username, int modelId);
}
