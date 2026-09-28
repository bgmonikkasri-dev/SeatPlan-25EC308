package com.sece.seatplan.repository;

import com.sece.seatplan.entity.ExamSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamSessionRepository extends JpaRepository<ExamSession, Long> {

    List<ExamSession> findByHall_Id(Long hallId);
}