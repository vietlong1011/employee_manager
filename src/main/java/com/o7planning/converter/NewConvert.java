package com.o7planning.converter;

import com.o7planning.dto.DepartmentDtoIn;
import com.o7planning.dto.PersonDtoIn;
import com.o7planning.entity.Department;
import com.o7planning.entity.Person;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class NewConvert {
    @Autowired
    private ModelMapper modelMapper;

    // chuyen du lieu tu dto sang entity de dung trong noi bo ung dung
    public Person toEntity(PersonDtoIn personDtoIn) {
        Person person = new Person();
        person.setId(personDtoIn.getId());
        person.setNamePerson(personDtoIn.getNamePerson());
        person.setOld(personDtoIn.getOld());
        person.setGender(personDtoIn.isGender());
        person.setCountry(personDtoIn.getCountry());
        person.setDepartment(personDtoIn.getDepartment());
        return person;
    }

    // chuyen du lieu tu entity sang dto
    // chia sẻ với bên ngoài qua REST API hoặc giao tiếp với các service khác trong microservic
    public PersonDtoIn toDTO(Person person) {
        PersonDtoIn personDtoIn = new PersonDtoIn();
        if (personDtoIn.getId() != null) {
            personDtoIn.setId(person.getId());
        }
        return personDtoIn;
    }

    public Department toEntity(DepartmentDtoIn departmentDtoIn) {
        Department department = new Department();
        department.setIdDepartment(departmentDtoIn.getDepartment().getIdDepartment());
        department.setDepartment(departmentDtoIn.getDepartment().getDepartment());
        department.setTeacher(departmentDtoIn.getDepartment().getTeacher());
        department.setQuantity_demanded(departmentDtoIn.getDepartment().getQuantity_demanded());
        department.setPersonList(departmentDtoIn.getDepartment().getPersonList());
        return department;
    }

    public DepartmentDtoIn toDTO(Department department) {
        DepartmentDtoIn departmentDtoIn = new DepartmentDtoIn();
        if (department != null) {
            departmentDtoIn.setDepartment(department);
        }
        return departmentDtoIn;
    }

    // test
    public Department userToEntity(DepartmentDtoIn userDtoIn){
        Department user = new Department();
        user = modelMapper.map(userDtoIn, Department.class);
        return user;
    }
}
