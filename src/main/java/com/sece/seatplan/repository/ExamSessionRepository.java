package com.sece.seatplan.repository;

import com.sece.seatplan.entity.ExamSession;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ExamSessionRepository extends JpaRepository<ExamSession, Long> {

    List<ExamSession> findByHall_Id(Long hallId);
}