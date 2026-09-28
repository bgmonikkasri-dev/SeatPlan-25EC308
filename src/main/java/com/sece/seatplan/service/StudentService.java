package com.sece.seatplan.service;

import com.sece.seatplan.dto.StudentDto;
import com.sece.seatplan.entity.Student;
import com.sece.seatplan.exception.ResourceNotFoundException;
import com.sece.seatplan.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public Student saveStudent(StudentDto dto) {

        if (studentRepository.findByRegisterNo(dto.getRegisterNo()).isPresent()) {
            throw new IllegalArgumentException(
                    "Student with register number already exists"
            );
        }

        Student student = new Student();

        student.setRegisterNo(dto.getRegisterNo());
        student.setName(dto.getName());
        student.setDepartment(dto.getDepartment());
        student.setSection(dto.getSection());
        student.setClassGroup(dto.getClassGroup());

        return studentRepository.save(student);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Student not found with id: " + id));
    }

    public List<Student> getStudentsByClassGroup(String classGroup) {
        return studentRepository.findByClassGroup(classGroup);
    }

    public List<Student> getStudentsByDepartment(String department) {
        return studentRepository.findByDepartment(department);
    }

    public Student getStudentByRegisterNo(String registerNo) {
        return studentRepository.findByRegisterNo(registerNo)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Student not found with register number: " + registerNo));
    }

    public void deleteStudent(Long id) {
        Student student = getStudentById(id);
        studentRepository.delete(student);
    }
}