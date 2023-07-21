package com.o7planning.repository;
import com.o7planning.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface PersonRepositoryCustom  {

    // tao ham tim kiem nhan vien theo key ,Criteria API

    List<Person> searchPerson(String keyword);
}
