package com.phonebookertask.repository;

import com.phonebookertask.model.Book;
import com.phonebookertask.model.PhoneModel;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Integer> {
    int countAllByPhoneModel(PhoneModel phoneModel);
    Book findFirstByUsernameAndPhoneModelId(String username, int phoneModelId);
}
