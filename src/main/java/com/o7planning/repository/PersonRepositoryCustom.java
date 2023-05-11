package com.o7planning.repository;
import com.o7planning.entity.Person;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface PersonRepositoryCustom {
    @Query("SELECT * FROM Person WHERE namePerson LIKE '%keyword%' OR department LIKE '%keyword%';")
    List<Person> findByNamePersonOrDepartment(String namePerson, String department);

    // tao ham tim kiem nhan vien theo key ,Criteria API
    @Query("SELECT * FROM Person WHERE namePerson LIKE '%keyword%' OR department LIKE '%keyword%' OR id=keyword OR old=keyword;")
    List<Person> searchPerson(String keyword);
}
