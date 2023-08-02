package com.o7planning.service.impl;

import com.o7planning.converter.NewConvert;
import com.o7planning.dto.PersonDtoIn;
import com.o7planning.entity.Person;
import com.o7planning.entity.PersonValidator;
import com.o7planning.repository.PersonRepository;
import com.o7planning.repository.PersonRepositoryCustom;
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


        //return departmentRepository.findAll();


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


    /**ham nay y tuong thao tac voi JDBC voi cac lenh cua no**/
//    @Override
//    public Long updatePersonById(Long id, Person newPerson) {
//        EntityTransaction transaction = entityManager.getTransaction();
//        try {
//            transaction.begin();
//            Person person = entityManager.find(Person.class, id);
//            if (person == null) {
//                return 0L;
//            }
//            person = new Person();
//            person.setNamePerson(newPerson.getNamePerson());
//            person.setOld(newPerson.getOld());
//            person.setGender(newPerson.isGender());
//            person.setCountry(newPerson.getCountry());
//            person.setDepartment(newPerson.getDepartment());
//            //entityManager.merge(person);
//            entityManager.flush();
//            //transaction.commit();
//            return 1L;
//        } catch (Exception e) {
//            transaction.rollback();
//            throw e;
//        }
}

