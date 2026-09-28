package com.sece.seatplan.controller;

import com.sece.seatplan.dto.StudentDto;
import com.sece.seatplan.entity.Student;
import com.sece.seatplan.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping
    public Student createStudent(
            @Valid @RequestBody StudentDto dto) {

        return studentService.saveStudent(dto);
    }

    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id);
    }

    @GetMapping("/register/{registerNo}")
    public Student getStudentByRegisterNo(
            @PathVariable String registerNo) {

        return studentService.getStudentByRegisterNo(registerNo);
    }

    @GetMapping("/group/{classGroup}")
    public List<Student> getStudentsByGroup(
            @PathVariable String classGroup) {

        return studentService.getStudentsByClassGroup(classGroup);
    }

    @GetMapping("/department/{department}")
    public List<Student> getStudentsByDepartment(
            @PathVariable String department) {

        return studentService.getStudentsByDepartment(department);
    }

    @DeleteMapping("/{id}")
    public String deleteStudent(@PathVariable Long id) {

        studentService.deleteStudent(id);

        return "Student deleted successfully";
    }
}