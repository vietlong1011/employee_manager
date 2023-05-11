package com.o7planning.controller;

import com.o7planning.dto.DepartmentDtoIn;
import com.o7planning.dto.PersonDtoIn;
import com.o7planning.entity.Department;
import com.o7planning.entity.Person;
import com.o7planning.repository.DepartmentRepository;
import com.o7planning.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
public class ApiController {
    @Autowired
    private IService iService;

    @Autowired
    private DepartmentRepository departmentRepository;

    // thao tac luu va hien thi obj department
    @PostMapping("/saveDepartment")
    public DepartmentDtoIn save(@RequestBody DepartmentDtoIn departmentDtoIn) {
        return iService.save(departmentDtoIn);
    }

    @GetMapping("/findAll")
    public List<Department> findAllDepartment() {
        return iService.findAll();
    }

    // thao tac voi obj person
    @GetMapping("/list")
    public List<Person> getPerson() {
        return iService.getPerson();
    }

    @PostMapping("/save")
    public PersonDtoIn save(@RequestBody PersonDtoIn person) {
        return iService.save(person);
    }


    /**
     * Ham nay va ham duoi no tuong tu nhau
     **/
@GetMapping("findByNamePersonOrDepartment")
public  List<PersonDtoIn> findByNamePersonOrDepartment(@RequestParam(name = "namePerson", required = false) String namePerson, @RequestParam(name = "department", required = false) String department){

        return iService.findByNamePersonOrDepartment(namePerson,department);
    }


    @GetMapping("/search/{keyword}")
    public List<Person> searchPerson(@PathVariable String keyword) {
        return iService.searchPerson(keyword);
    }
    @DeleteMapping("/delete/{id}")
    public String deletePersonById(@PathVariable Long id) {
        iService.deletePersonById(id);
        return "OKE";
    }

    /**
     * API: update/1
     **/
    @PutMapping("/update/{id}")
    public PersonDtoIn updatePersonById(@PathVariable("id") Long id, @RequestBody PersonDtoIn dto) {
        dto.setId(id);
        iService.updatePersonById(dto);
        return dto;
    }

}
