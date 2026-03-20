package ru.itis.dis403.spring.jpa.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.itis.dis403.spring.jpa.entity.Phone;
import ru.itis.dis403.spring.jpa.service.PhoneService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final PhoneService phoneService;

    @GetMapping("/")
    public String index(Model model) {
        List<Phone> phones = phoneService.findAll();
        model.addAttribute("phones", phones);

        return "index";
    }
}
