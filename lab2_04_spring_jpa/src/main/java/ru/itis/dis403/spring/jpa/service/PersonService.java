package ru.itis.dis403.spring.jpa.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.dis403.spring.jpa.entity.Admin;
import ru.itis.dis403.spring.jpa.entity.Client;
import ru.itis.dis403.spring.jpa.entity.Person;
import ru.itis.dis403.spring.jpa.entity.Phone;
import ru.itis.dis403.spring.jpa.repository.PersonRepository;
import ru.itis.dis403.spring.jpa.repository.PhoneRepository;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PhoneRepository phoneRepository;
    private final PersonRepository personRepository;

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public List<Phone> findAllPhones() {
        return phoneRepository.findAll();
    }

    public void save(String name, String type, String email, String address, String phoneNumber) {

        Phone phone = null;
        if (phoneNumber != null && !phoneNumber.isBlank()) {
            phone = phoneRepository.save(
                    Phone.builder()
                            .number(phoneNumber)
                            .build()
            );}

        if ("ADMIN".equals(type)) {
            Admin admin = new Admin();
            admin.setName(name);
            admin.setEmail(email);
            admin.setPhone(phone);
            admin.setPhones(Set.of(phone));
            personRepository.save(admin);
        } else {
            Client client = new Client();
            client.setName(name);
            client.setAddress(address);
            client.setPhone(phone);
            client.setPhones(Set.of(phone));
            personRepository.save(client);
        }
    }
}
