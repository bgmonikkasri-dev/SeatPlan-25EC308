package com.sece.seatplan.repository;

import com.sece.seatplan.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Long> {

    List<Seat> findByExamSession_Id(Long examSessionId);

    List<Seat> findByStudent_RegisterNo(String registerNo);

    void deleteByExamSession_Id(Long examSessionId);
}