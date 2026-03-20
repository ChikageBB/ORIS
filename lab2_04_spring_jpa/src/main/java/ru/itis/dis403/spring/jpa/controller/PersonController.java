package ru.itis.dis403.spring.jpa.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itis.dis403.spring.jpa.service.PersonService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/persons")
public class PersonController {

    private final PersonService personService;


    @GetMapping
    public String list(Model model) {
        model.addAttribute("persons", personService.findAll());
        return "persons";
    }

    @GetMapping("/add")
    public String addForm() {
        return "person-form";
    }

    @PostMapping
    public String save(
            @RequestParam String name,
            @RequestParam String type,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) String phoneNumber
    ) {

        personService.save(name, type, email, address, phoneNumber);
        return "redirect:/persons";
    }
}
