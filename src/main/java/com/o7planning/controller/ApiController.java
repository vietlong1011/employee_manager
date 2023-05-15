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
    @PostMapping("/department")
    public DepartmentDtoIn save(@RequestBody DepartmentDtoIn departmentDtoIn) {
        return iService.save(departmentDtoIn);
    }

    @GetMapping("/department")
    public List<Department> findAllDepartment() {
        return iService.findAll();
    }

    // thao tac voi obj person
    @GetMapping("/person")
    public List<Person> getPerson() {
        return iService.getPerson();
    }

    @PostMapping("/person")
    public PersonDtoIn save(@RequestBody PersonDtoIn person) {
         iService.save(person);
        return person;
    }


    /**
     * Ham nay va ham duoi no tuong tu nhau
     **/
    @GetMapping("findPerson")
    public List<PersonDtoIn> findByNamePersonOrDepartment(@RequestParam(name = "namePerson", required = false) String namePerson, @RequestParam(name = "department", required = false) String department) {
        return iService.findByNamePersonOrDepartment(namePerson, department);
    }

    @GetMapping("findDepartment/{department}")
    public List<DepartmentDtoIn> findByDepartment(@PathVariable("department") String department){
        return iService.findByDepartment(department);
    }

    @GetMapping("/findPerson/{keyword}")
    public List<PersonDtoIn> searchPerson(@PathVariable String keyword) {
        return iService.searchPerson(keyword);
    }

    @DeleteMapping("/person/{id}")
    public String deletePersonById(@PathVariable Long id) {
        iService.deletePersonById(id);
        return "OKE";
    }

    /**
     * API: update/1
     **/
    @PutMapping("/person/{id}")
    public PersonDtoIn updatePersonById(@PathVariable("id") Long id, @RequestBody PersonDtoIn dto) {
        dto.setId(id);
        iService.updatePersonById(dto);
        return dto;
    }

}
