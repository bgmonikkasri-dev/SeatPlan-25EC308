package com.sece.seatplan.repository;

import com.sece.seatplan.entity.SeatingArrangement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatingArrangementRepository extends JpaRepository<SeatingArrangement, Long> {

    List<SeatingArrangement> findByHallName(String hallName);

    void deleteByHallName(String hallName);
}