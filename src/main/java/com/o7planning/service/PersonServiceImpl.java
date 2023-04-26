package com.o7planning.service;

import com.o7planning.entity.Person;
import com.o7planning.entity.PersonValidator;
import com.o7planning.repository.PersonRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {
    @Autowired
    private PersonRepository personRepository;

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
    public Person save(Person person) {
        if (validator.isValid(person)) {
            return personRepository.save(person);
        }
        return null;
    }

//    @Override
//    public Person findByNamePerson(String namePerson) {
//        return personRepository.findByNameLike(namePerson);
//    }

    // Criteria API
    @Override
    public List<Person> searchPerson(String keyword) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Person> query = builder.createQuery(Person.class);
        Root<Person> root = query.from(Person.class);
        query.select(root).where(builder.or(
                builder.like(root.get("name"), "%" + keyword + "%"),
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
    public Long updatePersonById(Long id, Person newPerson) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Person person = entityManager.find(Person.class, id);
            if (person == null) {
                return 0L;
            }
            person.setNamePerson(newPerson.getNamePerson());
            person.setOld(newPerson.getOld());
            person.setGender(newPerson.isGender());
            person.setCountry(newPerson.getCountry());
            person.setDepartment(newPerson.getDepartment());
            entityManager.merge(person);
            entityManager.flush();
            transaction.commit();
            return 1L;
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }


}
