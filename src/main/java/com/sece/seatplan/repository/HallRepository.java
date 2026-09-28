package com.sece.seatplan.repository;

import com.sece.seatplan.entity.Hall;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HallRepository extends JpaRepository<Hall, Long> {

    Optional<Hall> findByHallName(String hallName);
}