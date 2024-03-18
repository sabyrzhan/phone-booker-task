package com.phonebookertask.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "phone_makes")
public class PhoneMake {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;

    @OneToMany(mappedBy = "make")
    private List<PhoneModel> models = new ArrayList<>();

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

    public List<PhoneModel> getModels() {
        return models;
    }

    public void setModels(List<PhoneModel> models) {
        this.models = models;
    }

    public boolean hasModel(String modelName) {
        return !models.stream().filter(phoneModel -> phoneModel.getName().equals(modelName)).toList().isEmpty();
    }
}
