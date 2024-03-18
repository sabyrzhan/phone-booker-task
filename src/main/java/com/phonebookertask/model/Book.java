package com.phonebookertask.model;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String username;

    @ManyToOne
    @JoinColumn(name = "model_id")
    private PhoneModel phoneModel;

    @Column(name = "book_date")
    private Instant bookDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PhoneModel getPhoneModel() {
        return phoneModel;
    }

    public void setPhoneModel(PhoneModel phoneModel) {
        this.phoneModel = phoneModel;
    }

    public Instant getBookDate() {
        return bookDate;
    }

    public void setBookDate(Instant bookDate) {
        this.bookDate = bookDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
