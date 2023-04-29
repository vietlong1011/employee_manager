package com.o7planning.controller;

import com.o7planning.entity.Person;
import com.o7planning.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@RestController
@RequestMapping("api")
public class PersonController {
    @Autowired
    private PersonService personService;

    @GetMapping("/list")
    public List<Person> getPerson() {
        return personService.getPerson();
    }


    @PostMapping("/save")
    public Person save(@RequestBody Person person) {
        personService.save(person);
        return person;
    }

//    @GetMapping("/findByName/{namePerson}")
//    public Person findByNamePerson(@PathVariable String namePerson) {
//        return personService.findByNamePerson(namePerson);
//    }


//    @GetMapping("/search/{keyword}")
//    public List<Person> searchPerson(@PathVariable String keyword) {
//        return personService.searchPerson(keyword);
//    }

    @DeleteMapping("/delete/{id}")
    public String deletePersonById(@PathVariable Long id) {
        personService.deletePersonById(id);
        return "OKE";
    }

    /** API: update/1?name=minh&department=KT **/
    @PutMapping("/update/{id}")
    public Long updatePersonById(@PathVariable("id") Long id, @RequestBody Person person) {

       return person.getId();
    }

/**API xoa thanh cong
 * sua ham update , ham them**/
}
