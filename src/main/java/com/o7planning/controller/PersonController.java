package com.o7planning.controller;

import com.o7planning.model.Person;
import com.o7planning.model.PersonForm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api")

public class PersonController {
    private static List<Person> persons = new ArrayList<>();

    static {
        persons.add(new Person("Bill", "Gates"));
        persons.add(new Person("Steve", "Jobs"));
    }

    @Value("${welcome.message}")
    private String welcomeMessage;
    @Value("${error.message}")
    private String errorMessage;

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("welcomeMessage", welcomeMessage);
        return "index";
    }

    // hien thi danh sach
    @GetMapping("/list")
    public String personList(Model model) {
        model.addAttribute("persons", persons);
        return "personList";
    }

    // them danh sach
    @GetMapping("/addPerson")
    public String showAddPerson(Model model) {
        PersonForm personForm = new PersonForm();
        model.addAttribute("personForm", personForm);
        return "addPerson";
    }

    @PostMapping("/addPerson")
    public String savePerson(Model model, @ModelAttribute("personForm") PersonForm personForm) {
        String firstName = personForm.getFirstName();
        String lastName = personForm.getLastName();
        if (firstName != null && firstName.length() > 0) {
            Person newPerson = new Person(firstName, lastName);
            persons.add(newPerson);
            return "redirect:/api/list";
        }
        model.addAttribute("errorMessage", errorMessage);
        return "addPerson";
    }

    // xoa theo first name
    @GetMapping("/deletePerson")
    public String showDeletePerson() {
        return "delete";
    }

    @PostMapping("/deletePerson")
    public String deletePerson(@ModelAttribute("nameDelete") String nameDelete, RedirectAttributes redirectAttributes) {
        boolean isDeleted = false;
        for (int i = 0; i < persons.size(); i++) {
            if (persons.get(i).getFirstName().equals(nameDelete)) {
                persons.remove(i);
                isDeleted = true;
                break;
            }
        }
        if (isDeleted) {
            redirectAttributes.addFlashAttribute("messageDelete", "Xóa người dùng thành công!");
        } else {
            redirectAttributes.addFlashAttribute("messageDelete", "Không tìm thấy người dùng cần xóa!");
        }
        return "redirect:/api/deletePerson";
    }

    // tim kiem
    @GetMapping("findPerson")
    public String ShowFindByName() {
        return "find";
    }

    @PostMapping("findPerson")
    public String findByName(@ModelAttribute("findName") String findName,RedirectAttributes redirectAttributes) {
        List<Person> findList = new ArrayList<>();
        for (int i = 0; i < persons.size(); i++) {
            if (persons.get(i).getFirstName().equals(findName)) {
                findList.add(persons.get(i));
               redirectAttributes.addFlashAttribute("findList", findList);
            }
        }
        redirectAttributes.addFlashAttribute("message", "Find successfully");
        return "redirect:/api/findPerson";
    }
}
