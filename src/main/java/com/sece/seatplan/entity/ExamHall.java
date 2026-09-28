package com.sece.seatplan.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "exam_halls")
public class ExamHall {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String hallName; // e.g., "LH-101", "Lab-2"
    private int capacity;    // Total seats e.g., 30
    private int rowsCount;   // e.g., 5 rows
    private int colsCount;   // e.g., 6 columns

    // Default Constructor
    public ExamHall() {
    }

    // Parameterized Constructor
    public ExamHall(String hallName, int capacity, int rowsCount, int colsCount) {
        this.hallName = hallName;
        this.capacity = capacity;
        this.rowsCount = rowsCount;
        this.colsCount = colsCount;
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

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getRowsCount() {
        return rowsCount;
    }

    public void setRowsCount(int rowsCount) {
        this.rowsCount = rowsCount;
    }

    public int getColsCount() {
        return colsCount;
    }

    public void setColsCount(int colsCount) {
        this.colsCount = colsCount;
    }
}