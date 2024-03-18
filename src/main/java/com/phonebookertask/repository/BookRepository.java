package com.phonebookertask.repository;

import com.phonebookertask.model.Book;
import com.phonebookertask.model.PhoneModel;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Integer> {
    int countAllByPhoneModel(PhoneModel phoneModel);
    void deleteByUsernameAndPhoneModel(String username, PhoneModel model);
    Book findFirstByUsernameAndPhoneModel(String username, PhoneModel phoneModel);
}
