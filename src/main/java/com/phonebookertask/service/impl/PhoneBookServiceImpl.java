package com.phonebookertask.service.impl;

import com.phonebookertask.exception.PhoneNotFoundException;
import com.phonebookertask.exception.PhoneUnavailableException;
import com.phonebookertask.model.Book;
import com.phonebookertask.repository.BookRepository;
import com.phonebookertask.repository.PhoneModelRepository;
import com.phonebookertask.service.PhoneBookService;
import com.phonebookertask.service.dto.PhoneDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class PhoneBookServiceImpl implements PhoneBookService {
    private final PhoneModelRepository phoneModelRepository;
    private final BookRepository bookRepository;

    public PhoneBookServiceImpl(PhoneModelRepository phoneModelRepository, BookRepository bookRepository) {
        this.phoneModelRepository = phoneModelRepository;
        this.bookRepository = bookRepository;
    }

    public List<PhoneDTO> getAvailablePhones() {
        var allModels = phoneModelRepository.findAll();
        var bookedPhones = new HashMap<Integer, List<Book>>();
        for(Book book: bookRepository.findAll()) {
            bookedPhones.compute(book.getPhoneModel().getId(), (integer, books) -> {
                if(books == null) {
                    books = new ArrayList<>();
                }
                books.add(book);
                return books;
            });
        }

        return StreamSupport.stream(allModels.spliterator(), false)
                .map(model -> new PhoneDTO(model, bookedPhones.getOrDefault(model.getId(), List.of()))).toList();
    }

    @Override
    public void book(String username, int modelId) {
        phoneModelRepository.findById(modelId).map(phoneModel -> {
            var bookedCount = bookRepository.countAllByPhoneModel(phoneModel);
            if(bookedCount >= phoneModel.getCount()) {
                throw new PhoneUnavailableException();
            }
            var book = new Book();
            book.setPhoneModel(phoneModel);
            book.setBookDate(Instant.now());
            book.setUsername(username);
            bookRepository.save(book);
            return book;
        }).orElseThrow(PhoneUnavailableException::new);
    }

    @Override
    @Transactional
    public void returnPhone(String username, int modelId) {
        var phoneModel = phoneModelRepository.findById(modelId);
        if (phoneModel.isEmpty()) {
            throw new PhoneNotFoundException();
        }
        var book = bookRepository.findFirstByUsernameAndPhoneModel(username, phoneModel.get());
        if (book != null) {
            bookRepository.delete(book);
        }
    }
}
