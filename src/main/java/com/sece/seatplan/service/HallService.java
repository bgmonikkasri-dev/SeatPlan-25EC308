package com.sece.seatplan.service;

import com.sece.seatplan.dto.HallDto;
import com.sece.seatplan.entity.Hall;
import com.sece.seatplan.exception.ResourceNotFoundException;
import com.sece.seatplan.repository.HallRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HallService {


private final HallRepository hallRepository;

public HallService(HallRepository hallRepository) {
    this.hallRepository = hallRepository;
}

public Hall saveHall(HallDto dto) {

    validateHall(dto);

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
                    new ResourceNotFoundException(
                            "Exam hall not found with id: " + id
                    ));
}

public Hall updateHall(Long id, HallDto dto) {

    Hall hall = getHallById(id);

    validateHall(dto);

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

private void validateHall(HallDto dto) {

    if (dto.getCapacity() <= 0) {
        throw new IllegalArgumentException(
                "Hall capacity must be greater than 0"
        );
    }

    if (dto.getRowsCount() <= 0) {
        throw new IllegalArgumentException(
                "Number of rows must be greater than 0"
        );
    }

    if (dto.getColsCount() <= 0) {
        throw new IllegalArgumentException(
                "Number of columns must be greater than 0"
        );
    }

    int totalSeats =
            dto.getRowsCount() * dto.getColsCount();

    if (dto.getCapacity() > totalSeats) {
        throw new IllegalArgumentException(
                "Capacity cannot be greater than rows multiplied by columns"
        );
    }
}

}
