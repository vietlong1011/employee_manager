package com.o7planning.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

/**
 * class nay se duoc phat trien de tao quan he trong JPA
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "department")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDepartment;

    @Column(name = "department")
    private String department;

    private int quantity_demanded;

    private String teacher;

    @OneToMany(targetEntity = Person.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "department", referencedColumnName = "department")
    private List<Person> personList;


    /** đang lỗi ở relationships khi insert obj vào thì bị lỗi khi insert department
     * -> cach khac phuc : thuc hien mo hinh 3 layer**/
}
