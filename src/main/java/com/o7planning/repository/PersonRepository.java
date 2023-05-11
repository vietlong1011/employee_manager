package com.o7planning.repository;

import com.o7planning.dto.PersonDtoIn;
import com.o7planning.entity.Department;
import com.o7planning.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * class su dung cac phuong thuc duoc Spring Data JPA cung cap
 * neu cac phuoc thuc k co -> dinh nghia = JPQL hoac Criteria APi
 **/
@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    @Query("SELECT * FROM Person WHERE namePerson LIKE '%keyword%' OR department LIKE '%keyword%';")
    List<PersonDtoIn> findByNamePersonOrDepartment(String namePerson,String department);

    // tao ham tim kiem nhan vien theo key ,Criteria API
    @Query("SELECT * FROM Person WHERE namePerson LIKE '%keyword%' OR department LIKE '%keyword%';")
    List<PersonDtoIn> searchPerson(String keyword);
}
