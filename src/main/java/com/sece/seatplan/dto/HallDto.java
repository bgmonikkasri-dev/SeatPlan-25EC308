package com.sece.seatplan.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.AssertTrue;

public class HallDto {

    @NotBlank(message = "Hall name cannot be blank")
    private String hallName;

    @Positive(message = "Capacity must be greater than 0")
    private int capacity;

    @Positive(message = "Rows count must be greater than 0")
    private int rowsCount;

    @Positive(message = "Columns count must be greater than 0")
    private int colsCount;

    // Custom validation rule
    @AssertTrue(message = "Capacity cannot exceed rows × cols")
    public boolean isValidCapacity() {
        return capacity <= rowsCount * colsCount;
    }

    public HallDto() {
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
