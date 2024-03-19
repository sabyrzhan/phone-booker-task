package com.phonebookertask.service;

import com.phonebookertask.exception.PhoneNotFoundException;
import com.phonebookertask.exception.PhoneUnavailableException;
import com.phonebookertask.model.Book;
import com.phonebookertask.model.PhoneMake;
import com.phonebookertask.model.PhoneModel;
import com.phonebookertask.repository.BookRepository;
import com.phonebookertask.repository.PhoneModelRepository;
import com.phonebookertask.service.impl.PhoneBookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PhoneBookServiceTest {
    PhoneBookService phoneBookService;

    @Mock
    BookRepository bookRepository;

    @Mock
    PhoneModelRepository phoneModelRepository;

    @BeforeEach
    void setUp() {
        phoneBookService = new PhoneBookServiceImpl(phoneModelRepository, bookRepository);
    }

    @Test
    void testBook_success() {
        var model = createModels().iterator().next();
        var modelId = model.getId();
        var username = "test";
        when(phoneModelRepository.findById(modelId)).thenReturn(Optional.of(model));
        when(bookRepository.countAllByPhoneModel(model)).thenReturn(0);

        phoneBookService.book(username, modelId);

        verify(phoneModelRepository).findById(modelId);
        verify(bookRepository).save(any());
        var bookArg = ArgumentCaptor.forClass(Book.class);
        verify(bookRepository).save(bookArg.capture());
        var savedBook = bookArg.getValue();
        assertEquals(username, savedBook.getUsername());
        assertEquals(modelId, savedBook.getPhoneModel().getId());
        assertNotNull(savedBook.getBookDate());
    }

    @Test
    void testBook_phoneNotFound() {
        var username = "test";
        when(phoneModelRepository.findById(any())).thenReturn(Optional.empty());

        try {
            phoneBookService.book(username, 111);
            fail("Fail");
        } catch (PhoneNotFoundException e) {}

        verify(phoneModelRepository).findById(111);
        verifyNoInteractions(bookRepository);
    }

    @Test
    void testBook_noAvailablePhones() {
        var model = createModels().iterator().next();
        var modelId = model.getId();
        var username = "test";
        when(phoneModelRepository.findById(any())).thenReturn(Optional.of(model));
        when(bookRepository.countAllByPhoneModel(model)).thenReturn(model.getCount());

        try {
            phoneBookService.book(username, modelId);
            fail("Fail");
        } catch (PhoneUnavailableException e) {}

        verify(phoneModelRepository).findById(modelId);
        verify(bookRepository).countAllByPhoneModel(model);
        verify(bookRepository, times(0)).save(any());
    }

    @Test
    void testReturn_success() {
        var model = createModels().iterator().next();
        var book = new Book();
        var username = "test";
        when(bookRepository.findFirstByUsernameAndPhoneModelId(username, model.getId())).thenReturn(book);

        phoneBookService.returnPhone(username, model.getId());

        verify(bookRepository).findFirstByUsernameAndPhoneModelId(username, model.getId());
        verify(bookRepository).delete(book);
    }

    private Iterable<PhoneModel> createModels() {
        var models = new ArrayList<PhoneModel>();
        var make = new PhoneMake();
        make.setName("Samsung");

        var model = new PhoneModel();
        model.setId(1);
        model.setName("S8");
        model.setCount(1);
        model.setMake(make);
        models.add(model);

        model = new PhoneModel();
        model.setId(2);
        model.setName("S9");
        model.setCount(1);
        model.setMake(make);
        models.add(model);

        return models;
    }
}
