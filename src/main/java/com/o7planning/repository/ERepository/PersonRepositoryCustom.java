package com.o7planning.repository.ERepository;
import com.o7planning.entity.employee.Person;

import java.util.List;


public interface PersonRepositoryCustom  {

    // tao ham tim kiem nhan vien theo key ,Criteria API

    List<Person> searchPerson(String keyword);
}
