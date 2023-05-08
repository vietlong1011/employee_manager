package com.o7planning.service;

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
public interface PersonService {

    List<Person> getPerson();

    Person save(Person person);

    //     Person findByNamePerson(String namePerson);
    // tao ham tim kiem nhan vien theo key ,Criteria API
   @Query("SELECT * FROM Person WHERE namePerson LIKE '%keyword%' OR department LIKE '%keyword%';")
    List<Person> searchPerson(String keyword);

    void deletePersonById(Long id);

    // sửa = tìm kiếm + sửa thuần +
    Person updatePersonById(Person person);
//    @Query("SELECT * FROM Person WHERE namePerson LIKE '%keyword%' OR department LIKE '%keyword%';")
    List<Person> findByNamePersonOrDepartment(String namePerson,String department);



}
