package com.phonebookertask.service.dto;

import com.phonebookertask.model.Book;
import com.phonebookertask.model.PhoneModel;

import java.util.List;

public record PhoneDTO(PhoneModel model, List<Book> books) {
}
