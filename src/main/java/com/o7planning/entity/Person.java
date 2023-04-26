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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id ;

    @Column(name = "namePerson")
    private String namePerson;
    @Column(name = "old")
    private int old;
    @Column(name = "gender")
    private boolean gender;
    @Column(name = "country")
    private  String country;
    @Column(name = "department")
    private String department;

}
