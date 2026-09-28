package com.sece.seatplan.repository;

import com.sece.seatplan.entity.ExamHall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExamHallRepository extends JpaRepository<ExamHall, Long> {

    Optional<ExamHall> findByHallName(String hallName);
}