package com.o7planning.controller;

import com.o7planning.entity.Person;
import com.o7planning.repository.PersonRepository;
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


/** Ham nay va ham duoi no tuong tu nhau**/
@GetMapping("findByNamePersonOrDepartment")
public  List<Person> findByNamePersonOrDepartment(@RequestParam(name = "namePerson", required = false) String namePerson, @RequestParam(name = "department", required = false) String department){

    return personService.findByNamePersonOrDepartment(namePerson,department);
    }


    @GetMapping("/search/{keyword}")
    public List<Person> searchPerson(@PathVariable String keyword) {
        return personService.searchPerson(keyword);
    }

    @DeleteMapping("/delete/{id}")
    public String deletePersonById(@PathVariable Long id) {
        personService.deletePersonById(id);
        return "OKE";
    }

    /** API: update/1 **/
    @PutMapping("/update/{id}")
    public Person updatePersonById(@PathVariable("id") Long id, @RequestBody Person person) {
      person.setId(id);
      personService.updatePersonById(person);
       return person;
    }

}
