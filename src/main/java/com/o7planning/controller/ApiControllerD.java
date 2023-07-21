package com.o7planning.controller;

import com.o7planning.dto.DepartmentDtoIn;
import com.o7planning.service.ISDepartment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiControllerD {

    @Autowired
    private ISDepartment iService;

    @GetMapping("/department")
    public List<DepartmentDtoIn> findAllDepartment() {
        return iService.findAll();
    }

    @GetMapping("department/{department}")
    public List<DepartmentDtoIn> findByDepartment(@PathVariable("department") String department) {
        return iService.findByDepartment(department);
    }

    // thao tac luu va hien thi obj department
    @PostMapping("/department")
    public DepartmentDtoIn save(@RequestBody DepartmentDtoIn departmentDtoIn) {
        return iService.save(departmentDtoIn);
    }


}
