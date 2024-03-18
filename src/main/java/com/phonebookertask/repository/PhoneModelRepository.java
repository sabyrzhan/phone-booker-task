package com.phonebookertask.repository;

import com.phonebookertask.model.PhoneModel;
import org.springframework.data.repository.CrudRepository;

public interface PhoneModelRepository extends CrudRepository<PhoneModel, Integer> {
}
