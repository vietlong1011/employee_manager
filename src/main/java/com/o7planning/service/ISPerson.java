package com.o7planning.service;

import com.o7planning.dto.PersonDtoIn;
import com.o7planning.entity.employee.Person;

import java.util.List;

/**
 * yeu cau :
 * Thêm mới nhân viên
 * Hiển thị danh sách nhân viêm
 * Chỉnh sửa thông tin nhân viên
 * Xóa nhân viên
 * Tìm kiếm nhân viên
 **/
public interface ISPerson {
    PersonDtoIn save(PersonDtoIn person);


    List<Person> getPerson();


    void deletePersonById(Long id);

    // update = search + set + save
    PersonDtoIn updatePersonById(PersonDtoIn person);
    List<PersonDtoIn> findByNamePersonOrDepartment(String namePerson,String department);
    List<PersonDtoIn> searchPerson(String keyword);
}
