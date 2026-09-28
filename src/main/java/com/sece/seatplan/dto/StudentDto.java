package com.sece.seatplan.dto;

import jakarta.validation.constraints.NotBlank;

public class StudentDto {

    @NotBlank
    private String registerNo;

    @NotBlank
    private String name;

    @NotBlank
    private String department;

    @NotBlank
    private String section;

    @NotBlank
    private String classGroup;

    public StudentDto() {
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