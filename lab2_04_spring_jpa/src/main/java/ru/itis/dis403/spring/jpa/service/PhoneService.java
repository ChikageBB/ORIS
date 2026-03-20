package ru.itis.dis403.spring.jpa.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.dis403.spring.jpa.entity.Phone;
import ru.itis.dis403.spring.jpa.repository.PhoneRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PhoneService {

    private final PhoneRepository phoneRepository;

    @Transactional
    public Phone save(Phone phone) {
        return phoneRepository.save(phone);
    }

    public List<Phone> findAll() {
        return phoneRepository.findAll();
    }

    public List<Phone> getPhoneLike(String num) {
        return phoneRepository.findByNumberLike(num);
    }

}
