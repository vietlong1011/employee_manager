package com.o7planning.service;

import com.o7planning.converter.NewConvert;
import com.o7planning.dto.DepartmentDtoIn;
import com.o7planning.dto.PersonDtoIn;
import com.o7planning.entity.Department;
import com.o7planning.entity.Person;

import com.o7planning.entity.PersonValidator;
import com.o7planning.repository.DepartmentRepository;
import com.o7planning.repository.PersonRepository;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServiceImpl implements IService {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
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
    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<Person> getPerson() {
        return personRepository.findAll(Sort.by("namePerson").ascending());
    }

    @Override
    public DepartmentDtoIn save(DepartmentDtoIn departmentDtoIn) {
        Department department = new Department();
        department = newConvert.toEntity(departmentDtoIn);
        department = departmentRepository.save(department);
        return newConvert.toDTO(department);
    }

    /**
     * Controller gọi đến dto ,
     * dto được các Service chuyển đổi qua thành các Entity rồi dùng nó để
     * truy xuất xuống Repository lấy data
     **/
    @Override
    public PersonDtoIn save(PersonDtoIn personDtoIn) {
        /**todo sau khi duoc khai sang */
        Person person = new Person();
        person = newConvert.toEntity(personDtoIn);
        person = personRepository.save(person);
        return newConvert.toDTO(person);
    }

    @Override
    public List<Department> findAll() {
        return departmentRepository.findAll();
    }

    /**
     * todo
     **/
    // Criteria API
    @Override
    public List<Person> searchPerson(String keyword) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Person> query = builder.createQuery(Person.class);
        Root<Person> root = query.from(Person.class);
        query.select(root).where(builder.or(
                builder.like(root.get("namePerson"), "%" + keyword + "%"),
                builder.like(root.get("department"), "%" + keyword + "%")
        ));
        TypedQuery<Person> typedQuery = entityManager.createQuery(query);
        return typedQuery.getResultList();
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

    /**todo**/
    @Override
    public List<PersonDtoIn> findByNamePersonOrDepartment(String namePerson,String department) {
        try {
//            String sql = "SELECT p FROM Person p WHERE p.namePerson LIKE ?1 OR p.department LIKE ?2";
//            TypedQuery<PersonDtoIn> query = entityManager.createQuery(sql, PersonDtoIn.class);
//            query.setParameter(1, namePerson);
//            query.setParameter(2, department);
      //      return query.getResultList();
            return   personRepository.findByNamePersonOrDepartment(namePerson,department);
        } catch (NoResultException e) {
            return new ArrayList<>();
        }
    }

    /**todo**/
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

