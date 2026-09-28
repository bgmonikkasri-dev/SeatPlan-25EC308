package com.sece.seatplan.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String registerNo;
    private String name;
    private String department; // e.g., "CSE", "ECE", "MECH"
    private String section;    // e.g., "A", "B"
    private String classGroup; // Combined e.g., "CSE-A", "ECE-B"

    // Default Constructor (Required by JPA)
    public Student() {
    }

    // Parameterized Constructor
    public Student(String registerNo, String name, String department, String section, String classGroup) {
        this.registerNo = registerNo;
        this.name = name;
        this.department = department;
        this.section = section;
        this.classGroup = classGroup;
    }

    // Explicit Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegisterNo() {
        return registerNo;
    }

    public void setRegisterNo(String registerNo) {
        this.registerNo = registerNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getClassGroup() {
        return classGroup;
    }

    public void setClassGroup(String classGroup) {
        this.classGroup = classGroup;
    }
}