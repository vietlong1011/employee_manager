package com.o7planning.service;

import com.o7planning.converter.NewConvert;
import com.o7planning.dto.DepartmentDtoIn;
import com.o7planning.entity.Department;
import com.o7planning.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DepartmentImpl implements ISDepartment {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private NewConvert newConvert;

    @Override
    public DepartmentDtoIn save(DepartmentDtoIn departmentDtoIn) {
        //todo
        Department department = new Department();
        department = newConvert.userToEntity(departmentDtoIn);
        // department = newConvert.toEntity(departmentDtoIn);
        department = departmentRepository.save(department);
        return newConvert.toDTO(department);
    }

    @Override
    public List<DepartmentDtoIn> findAll() {
        List<Department> userList =  departmentRepository.findAll();
        List<DepartmentDtoIn> userDtoInList = new ArrayList<>();
        for (Department user : userList){
            DepartmentDtoIn userDtoIn = new DepartmentDtoIn();
            userDtoIn =   newConvert.toDTO(user);
            userDtoInList.add(userDtoIn);
        }
        return userDtoInList;
    }

    @Override
    public List<DepartmentDtoIn> findByDepartment(String department) {
        List<Department> departments = departmentRepository.findAllByDepartment(department);
        List<DepartmentDtoIn> dtoInList = new ArrayList<>();
        for(Department d : departments){
            DepartmentDtoIn dto = new DepartmentDtoIn();
            dto = newConvert.toDTO(d);
            dtoInList.add(dto);
        }
        return dtoInList;
    }

}
