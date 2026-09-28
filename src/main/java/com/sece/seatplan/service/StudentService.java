package com.sece.seatplan.service;

import com.sece.seatplan.entity.Student;
import com.sece.seatplan.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public List<Student> getStudentsByClassGroup(String classGroup) {
        return studentRepository.findByClassGroup(classGroup);
    }

    public List<Student> getStudentsByDepartment(String department) {
        return studentRepository.findByDepartment(department);
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }
}