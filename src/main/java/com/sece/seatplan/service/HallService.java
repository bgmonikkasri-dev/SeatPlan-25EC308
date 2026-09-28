package com.sece.seatplan.service;

import com.sece.seatplan.dto.HallDto;
import com.sece.seatplan.entity.Hall;
import com.sece.seatplan.exception.ResourceNotFoundException;
import com.sece.seatplan.repository.HallRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HallService {

    @Autowired
    private HallRepository hallRepository;

    public Hall saveHall(HallDto dto) {

        if (dto.getCapacity() > dto.getRowsCount() * dto.getColsCount()) {
            throw new IllegalArgumentException(
                    "Capacity cannot be greater than rows multiplied by columns"
            );
        }

        Hall hall = new Hall();

        hall.setHallName(dto.getHallName());
        hall.setCapacity(dto.getCapacity());
        hall.setRowsCount(dto.getRowsCount());
        hall.setColsCount(dto.getColsCount());

        return hallRepository.save(hall);
    }

    public List<Hall> getAllHalls() {
        return hallRepository.findAll();
    }

    public Hall getHallById(Long id) {
        return hallRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Exam hall not found with id: " + id));
    }

    public Hall updateHall(Long id, HallDto dto) {

        Hall hall = getHallById(id);

        if (dto.getCapacity() > dto.getRowsCount() * dto.getColsCount()) {
            throw new IllegalArgumentException(
                    "Capacity cannot be greater than rows multiplied by columns"
            );
        }

        hall.setHallName(dto.getHallName());
        hall.setCapacity(dto.getCapacity());
        hall.setRowsCount(dto.getRowsCount());
        hall.setColsCount(dto.getColsCount());

        return hallRepository.save(hall);
    }

    public void deleteHall(Long id) {
        Hall hall = getHallById(id);
        hallRepository.delete(hall);
    }
}