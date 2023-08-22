package com.o7planning.service.impl;

import com.o7planning.converter.NewConvert;
import com.o7planning.dto.PersonDtoIn;
import com.o7planning.entity.employee.Person;
import com.o7planning.entity.employee.PersonValidator;
import com.o7planning.repository.ERepository.PersonRepository;
import com.o7planning.repository.ERepository.PersonRepositoryCustom;
import com.o7planning.service.ISPerson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
/**
 * Controller gọi đến dto ,
 * dto được các Service chuyển đổi qua thành các Entity rồi dùng nó để
 * truy xuất xuống Repository lấy data
 **/
@Service
public class ServiceImpl implements ISPerson {
    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PersonRepositoryCustom personRepositoryCustom;


    @Autowired
    private NewConvert newConvert;

    @Autowired
    @Qualifier("validator")
    private PersonValidator validator;

    /**
     * @PersistenceContext được sử dụng để inject một EntityManager vào trường entityManager.
     * Nó cho phép bạn sử dụng entityManager để thao tác với các đối tượng trong cơ sở dữ liệu
     * thông qua các phương thức của EntityManager.
     **/

    @Override
    public List<Person> getPerson(){
    return personRepository.findAll(Sort.by("namePerson").ascending());
    }

    @Override
    public PersonDtoIn save(PersonDtoIn personDtoIn) {
        Person person = new Person();
        person = newConvert.toEntity(personDtoIn);
        person = personRepository.save(person);
        return newConvert.toDTO(person);
    }




    // Criteria API
    @Override
    public List<PersonDtoIn> searchPerson(String keyword) {
        List<Person> persons = personRepositoryCustom.searchPerson(keyword);
        List<PersonDtoIn> dtos = new ArrayList<>();
        for (Person person : persons) {
            PersonDtoIn dto = new PersonDtoIn();
            dto.setId(person.getId());
            dto.setNamePerson(person.getNamePerson());
            dto.setOld(person.getOld());
            dto.setCountry(person.getCountry());
            dto.setGender(person.isGender());
            dto.setDepartment(person.getDepartment());
            // set other fields as needed
            dtos.add(dto);
        }
        return dtos;
    }

    @Override
    public void deletePersonById(Long id) {
        personRepository.deleteById(id);
    }


    @Override
    public PersonDtoIn updatePersonById(PersonDtoIn dto) {
        Person person = new Person();
        person.setId(dto.getId());
        person = newConvert.toEntity(dto);
        person = personRepository.save(person);
        return newConvert.toDTO(person);
    }


    @Override
    public List<PersonDtoIn> findByNamePersonOrDepartment(String namePerson, String department) {
        List<Person> persons = personRepository.findByNamePersonOrDepartment(namePerson, department);
        List<PersonDtoIn> dtos = new ArrayList<>();
        for (Person person : persons) {
            PersonDtoIn dto = new PersonDtoIn();
            dto.setId(person.getId());
            dto.setNamePerson(person.getNamePerson());
            dto.setOld(person.getOld());
            dto.setCountry(person.getCountry());
            dto.setGender(person.isGender());
            dto.setDepartment(person.getDepartment());
            // set other fields as needed
            dtos.add(dto);
        }
        return dtos;
    }

}

