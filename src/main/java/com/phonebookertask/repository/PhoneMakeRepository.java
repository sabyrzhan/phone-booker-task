package com.phonebookertask.repository;

import com.phonebookertask.model.PhoneMake;
import org.springframework.data.repository.CrudRepository;

public interface PhoneMakeRepository extends CrudRepository<PhoneMake, Integer> {
    PhoneMake findByName(String name);
}
