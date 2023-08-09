package com.o7planning.repository.ERepository;

import com.o7planning.entity.employee.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * class su dung cac phuong thuc duoc Spring Data JPA cung cap
 * neu cac phuoc thuc k co -> dinh nghia = JPQL hoac Criteria APi
 **/
@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    // Query bang JPQL
    @Query("SELECT p FROM Person p WHERE p.namePerson LIKE :keyword1 OR p.department LIKE :keyword2")
    List<Person> findByNamePersonOrDepartment(@Param("keyword1") String keyword1,@Param("keyword2") String keyword2);
}
