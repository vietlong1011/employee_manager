package com.o7planning.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @Column(name = "name_person")
    private String namePerson;
    @Column(name = "old")
    private int old;
    @Column(name = "gender")
    private boolean gender;
    @Column(name = "country")
    private  String country;
//    @Column(name = "department")
    @ManyToOne
    @JoinColumn(name = "department")
    private Department department;

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", namePerson='" + namePerson + '\'' +
                ", old=" + old +
                ", gender=" + gender +
                ", country='" + country + '\'' +
                ", department='" + department + '\'' +
                '}';
    }
}
