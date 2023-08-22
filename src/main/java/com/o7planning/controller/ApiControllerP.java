package com.o7planning.controller;

import com.o7planning.dto.PersonDtoIn;
import com.o7planning.entity.employee.Person;
import com.o7planning.service.ISPerson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
//@PreAuthorize("hasAuthority('USER')")
public class ApiControllerP {

    @Autowired
    private ISPerson iService;


    @GetMapping("hello")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String hello(){
        return "index.html";
    }

    @GetMapping("hello2")
    @PreAuthorize("hasRole('USER')")
    public String hello2(){
        return "index2.html";
    }


    // thao tac voi obj person
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping("/person")
    public List<Person> getPerson() {
        return iService.getPerson();
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/person")
    public PersonDtoIn save(@RequestBody PersonDtoIn person) {
        iService.save(person);
        return person;
    }



     // Ham nay va ham duoi no tuong tu nhau
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    @GetMapping("/find-person")
    public List<PersonDtoIn> findByNamePersonOrDepartment(@RequestParam(name = "namePerson", required = false) String namePerson, @RequestParam(name = "department", required = false) String department) {
        return iService.findByNamePersonOrDepartment(namePerson, department);
    }


    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/person/{keyword}")
    public List<PersonDtoIn> searchPerson(@PathVariable String keyword) {
        return iService.searchPerson(keyword);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/person/{id}")
    public String deletePersonById(@PathVariable Long id) {
        iService.deletePersonById(id);
        return "OKE";
    }


    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/person/{id}")
    public PersonDtoIn updatePersonById(@PathVariable("id") Long id, @RequestBody PersonDtoIn dto) {
        dto.setId(id);
        iService.updatePersonById(dto);
        return dto;
    }

}
