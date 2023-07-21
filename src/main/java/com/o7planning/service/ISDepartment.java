package com.o7planning.service;

import com.o7planning.dto.DepartmentDtoIn;

import java.util.List;

public interface ISDepartment {

    List<DepartmentDtoIn> findAll();

    DepartmentDtoIn save(DepartmentDtoIn departmentDtoIn);

    List<DepartmentDtoIn> findByDepartment(String department);




}
