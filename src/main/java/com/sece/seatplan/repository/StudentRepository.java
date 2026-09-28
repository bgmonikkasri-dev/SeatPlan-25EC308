package com.sece.seatplan.repository;

import com.sece.seatplan.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByRegisterNo(String registerNo);

    List<Student> findByClassGroup(String classGroup);

    List<Student> findByDepartment(String department);
}