package com.sece.seatplan.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "seating_arrangements")
public class SeatingArrangement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String hallName;
    private int rowNo;
    private int colNo;
    private String studentRegisterNo;
    private String studentName;
    private String classGroup;
    private String department;

    // Default Constructor
    public SeatingArrangement() {
    }

    // Parameterized Constructor
    public SeatingArrangement(String hallName, int rowNo, int colNo, String studentRegisterNo, String studentName, String classGroup, String department) {
        this.hallName = hallName;
        this.rowNo = rowNo;
        this.colNo = colNo;
        this.studentRegisterNo = studentRegisterNo;
        this.studentName = studentName;
        this.classGroup = classGroup;
        this.department = department;
    }

    // Explicit Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHallName() {
        return hallName;
    }

    public void setHallName(String hallName) {
        this.hallName = hallName;
    }

    public int getRowNo() {
        return rowNo;
    }

    public void setRowNo(int rowNo) {
        this.rowNo = rowNo;
    }

    public int getColNo() {
        return colNo;
    }

    public void setColNo(int colNo) {
        this.colNo = colNo;
    }

    public String getStudentRegisterNo() {
        return studentRegisterNo;
    }

    public void setStudentRegisterNo(String studentRegisterNo) {
        this.studentRegisterNo = studentRegisterNo;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getClassGroup() {
        return classGroup;
    }

    public void setClassGroup(String classGroup) {
        this.classGroup = classGroup;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}