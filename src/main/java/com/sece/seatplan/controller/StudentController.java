package com.sece.seatplan.controller;

import com.sece.seatplan.entity.Student;
import com.sece.seatplan.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentService.saveStudent(student);
    }

    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/group/{classGroup}")
    public List<Student> getStudentsByGroup(@PathVariable String classGroup) {
        return studentService.getStudentsByClassGroup(classGroup);
    }

    @GetMapping("/department/{department}")
    public List<Student> getStudentsByDepartment(@PathVariable String department) {
        return studentService.getStudentsByDepartment(department);
    }

    @DeleteMapping("/{id}")
    public String deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return "Student deleted successfully with id: " + id;
    }
}