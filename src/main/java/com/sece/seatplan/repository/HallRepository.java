package com.sece.seatplan.repository;

import com.sece.seatplan.entity.Hall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HallRepository extends JpaRepository<Hall, Long> {

    Optional<Hall> findByHallName(String hallName);
}