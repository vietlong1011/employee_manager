package com.o7planning.service;

import com.o7planning.entity.Person;

import java.util.List;
/** yeu cau :
 * Thêm mới nhân viên
 *Hiển thị danh sách nhân viêm
 *Chỉnh sửa thông tin nhân viên
 *Xóa nhân viên
 *Tìm kiếm nhân viên**/
public interface PersonService {

     List<Person> getPerson();
     Person save(Person person);
//     Person findByNamePerson(String namePerson);
     // tao ham tim kiem nhan vien theo key ,Criteria API
     List<Person> searchPerson(String keyword);
     void deletePersonById(Long id);
     // sửa = tìm kiếm + sửa thuần +
     Long updatePersonById(Long id ,Person person);
}
