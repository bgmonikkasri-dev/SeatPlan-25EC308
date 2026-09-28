package com.sece.seatplan.repository;

import com.sece.seatplan.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByRegisterNo(String registerNo);

    List<Student> findByClassGroup(String classGroup);

    List<Student> findByDepartment(String department);
}