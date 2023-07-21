package com.o7planning.repository.impl;

import com.o7planning.dto.PersonDtoIn;
import com.o7planning.entity.Person;
import com.o7planning.repository.PersonRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PersonRepositoryCustomImpl implements PersonRepositoryCustom {


    @PersistenceContext
    private EntityManager entityManager;
//    @Override
//    public List<Person> findByNamePersonOrDepartment(String namePerson, String department) {
//        try {
//            String sql = "SELECT p FROM Person p WHERE p.namePerson LIKE ?1 OR p.department LIKE ?2";
//            TypedQuery<Person> query = entityManager.createQuery(sql, Person.class);
//            query.setParameter(1, namePerson);
//            query.setParameter(2, department);
//            return query.getResultList();
//            //   return   personRepository.findByNamePersonOrDepartment(namePerson,department);
//        } catch (NoResultException e) {
//            return new ArrayList<>();
//        }
//    }
//
    @Override
    public List<Person> searchPerson(String keyword) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Person> query = builder.createQuery(Person.class);
        Root<Person> root = query.from(Person.class);
            query.select(root).where(builder.or(
                builder.like(root.get("namePerson"), "%" + keyword + "%"),
                builder.like(root.get("department"), "%" + keyword + "%"),
                builder.like(root.get("country"), "%" + keyword + "%")
        ));
            try{
                query.select(root).where(builder.or(
                        builder.equal(root.get("id"), Integer.parseInt(keyword)),
                        builder.equal(root.get("old"), Integer.parseInt(keyword))));
            } catch (NumberFormatException e){
            // if do not find Integer else exit
        }
        TypedQuery<Person> typedQuery = entityManager.createQuery(query);
        return typedQuery.getResultList();
    }

}
