package com.o7planning.controller;

import com.o7planning.dto.DepartmentDtoIn;
import com.o7planning.service.ISDepartment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiControllerD {

    @Autowired
    private ISDepartment iService;

    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/department")
    public List<DepartmentDtoIn> findAllDepartment() {
        return iService.findAll();
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("department/{department}")
    public List<DepartmentDtoIn> findByDepartment(@PathVariable("department") String department) {
        return iService.findByDepartment(department);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    // thao tac luu va hien thi obj department
    @PostMapping("/department")
    public DepartmentDtoIn save(@RequestBody DepartmentDtoIn departmentDtoIn) {
        return iService.save(departmentDtoIn);
    }


}
