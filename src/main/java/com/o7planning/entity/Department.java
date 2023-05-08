package com.o7planning.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

/** class nay se duoc phat trien de tao quan he trong JPA **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "department")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idDepartment;

//    @Column(name = "department")
    @OneToMany(mappedBy="department")
    private Set<Person> department;

    @Column(name = "quantity")
    private int quantity;
}
