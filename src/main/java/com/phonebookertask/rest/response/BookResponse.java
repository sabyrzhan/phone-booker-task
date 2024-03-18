package com.phonebookertask.rest.response;

import com.phonebookertask.model.Book;

import java.time.Instant;

public class BookResponse {
    private String username;

    private Instant bookDate;

    public BookResponse(Book book) {
        this.username = book.getUsername();
        this.bookDate = book.getBookDate();
    }

    public String getUsername() {
        return username;
    }

    public Instant getBookDate() {
        return bookDate;
    }
}
