package com.phonebookertask.service.impl;

import com.phonebookertask.exception.PhoneNotFoundException;
import com.phonebookertask.exception.PhoneUnavailableException;
import com.phonebookertask.model.Book;
import com.phonebookertask.repository.BookRepository;
import com.phonebookertask.repository.PhoneModelRepository;
import com.phonebookertask.service.PhoneBookService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class PhoneBookServiceImpl implements PhoneBookService {
    private final PhoneModelRepository phoneModelRepository;
    private final BookRepository bookRepository;

    public PhoneBookServiceImpl(PhoneModelRepository phoneModelRepository, BookRepository bookRepository) {
        this.phoneModelRepository = phoneModelRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    @Transactional
    public void book(String username, int modelId) {
        var phoneModel = phoneModelRepository.findById(modelId);
        if (phoneModel.isEmpty()) {
            throw new PhoneNotFoundException();
        }

        var bookedCount = bookRepository.countAllByPhoneModel(phoneModel.get());
        if(bookedCount >= phoneModel.get().getCount()) {
            throw new PhoneUnavailableException();
        }

        var book = new Book();
        book.setPhoneModel(phoneModel.get());
        book.setBookDate(Instant.now());
        book.setUsername(username);
        bookRepository.save(book);
    }

    @Override
    @Transactional
    public void returnPhone(String username, int modelId) {
        var book = bookRepository.findFirstByUsernameAndPhoneModelId(username, modelId);
        if (book != null) {
            bookRepository.delete(book);
        }
    }
}
