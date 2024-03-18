package com.phonebookertask;

import com.phonebookertask.model.PhoneMake;
import com.phonebookertask.model.PhoneModel;
import com.phonebookertask.repository.PhoneMakeRepository;
import com.phonebookertask.repository.PhoneModelRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public class InitDBRunner implements CommandLineRunner {

    private final PhoneMakeRepository phoneMakeRepository;
    private final PhoneModelRepository phoneModelRepository;

    public InitDBRunner(PhoneMakeRepository phoneMakeRepository, PhoneModelRepository phoneModelRepository) {
        this.phoneMakeRepository = phoneMakeRepository;
        this.phoneModelRepository = phoneModelRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        upsertPhoneModels();
    }

    void upsertPhoneModels() {
        record Model(String name, int count) {}
        var phones = new HashMap<String, List<Model>>();
        phones.put("Samsung", List.of(
                new Model("Galaxy S9", 1),
                new Model("Galaxy S8", 2)
        ));
        phones.put("Motorola", List.of(
                new Model("Nexus 6", 1)
        ));
        phones.put("Oneplus", List.of(new Model("9", 1)));
        phones.put("Apple", List.of(
                new Model("iPhone 13", 1),
                new Model("iPhone 12", 1),
                new Model("iPhone 11", 1),
                new Model("iPhone X", 1)
        ));
        phones.put("Nokia", List.of(new Model("3310", 1)));

        for(String phoneMakeName: phones.keySet()) {
            var phoneMake = phoneMakeRepository.findByName(phoneMakeName);
            if (phoneMake == null) {
                phoneMake = new PhoneMake();
                phoneMake.setName(phoneMakeName);
                phoneMake = phoneMakeRepository.save(phoneMake);
            }

            for(Model model: phones.get(phoneMakeName)) {
                if(!phoneMake.hasModel(model.name)) {
                    var phoneModel = new PhoneModel();
                    phoneModel.setName(model.name);
                    phoneModel.setCount(model.count);
                    phoneModel.setMake(phoneMake);
                    phoneModelRepository.save(phoneModel);
                }
            }
        }
    }
}
