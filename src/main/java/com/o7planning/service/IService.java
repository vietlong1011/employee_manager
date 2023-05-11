package com.o7planning.service;

import com.o7planning.dto.DepartmentDtoIn;
import com.o7planning.dto.PersonDtoIn;
import com.o7planning.entity.Department;
import com.o7planning.entity.Person;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * yeu cau :
 * Thêm mới nhân viên
 * Hiển thị danh sách nhân viêm
 * Chỉnh sửa thông tin nhân viên
 * Xóa nhân viên
 * Tìm kiếm nhân viên
 **/
public interface IService {
    PersonDtoIn save(PersonDtoIn person);

    List<Department> findAll();

    List<Person> getPerson();

    DepartmentDtoIn save(DepartmentDtoIn departmentDtoIn);

    void deletePersonById(Long id);

    // update = search + set + save

    PersonDtoIn updatePersonById(PersonDtoIn person);
    List<PersonDtoIn> findByNamePersonOrDepartment(String namePerson,String department);
    List<Person> searchPerson(String keyword);
}
