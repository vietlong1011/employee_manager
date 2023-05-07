package com.o7planning.repository;

import com.o7planning.entity.Person;
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


}
