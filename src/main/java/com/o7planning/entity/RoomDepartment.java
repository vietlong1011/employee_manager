//package com.o7planning.entity;
//
//import jakarta.persistence.*;
//import lombok.Data;
//
//@Entity
//@Data
//@Table(name = "room_department")
//public class RoomDepartment {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne
//    @JoinColumn(name = "department_id")
//    private Department department;
//
//    @ManyToOne
//    @JoinColumn(name = "person_id")
//    private Person person;
//
//    // Constructors, getters, setters
//}
