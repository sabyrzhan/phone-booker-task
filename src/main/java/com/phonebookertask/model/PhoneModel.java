package com.phonebookertask.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "phone_models")
public class PhoneModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column
    private String name;

    @ManyToOne
    @JoinColumn(name = "make_id")
    private PhoneMake make;

    @Column
    private int count;

    @OneToMany(mappedBy = "phoneModel")
    private List<Book> books;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PhoneMake getMake() {
        return make;
    }

    public void setMake(PhoneMake make) {
        this.make = make;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public boolean isAvailable() {
        return getCount() - books.size() > 0;
    }

    public String getFullName() {
        String result = make != null ? make.getName() : "Noname";
        result += " " + name;

        return result;
    }
}
