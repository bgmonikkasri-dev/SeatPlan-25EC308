package com.sece.seatplan.service;

import com.sece.seatplan.entity.ExamHall;
import com.sece.seatplan.repository.ExamHallRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExamHallService {

    @Autowired
    private ExamHallRepository examHallRepository;

    public ExamHall saveHall(ExamHall examHall) {
        return examHallRepository.save(examHall);
    }

    public List<ExamHall> getAllHalls() {
        return examHallRepository.findAll();
    }

    public Optional<ExamHall> getHallById(Long id) {
        return examHallRepository.findById(id);
    }

    public void deleteHall(Long id) {
        examHallRepository.deleteById(id);
    }
}