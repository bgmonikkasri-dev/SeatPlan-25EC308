package com.sece.seatplan.service;

import com.sece.seatplan.entity.ExamSession;
import com.sece.seatplan.entity.Hall;
import com.sece.seatplan.exception.ResourceNotFoundException;
import com.sece.seatplan.repository.ExamSessionRepository;
import com.sece.seatplan.repository.HallRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamSessionService {

    private final ExamSessionRepository examSessionRepository;
    private final HallRepository hallRepository;

    public ExamSessionService(ExamSessionRepository examSessionRepository,
                              HallRepository hallRepository) {
        this.examSessionRepository = examSessionRepository;
        this.hallRepository = hallRepository;
    }

    public ExamSession saveSession(ExamSession session) {

        if (session.getHall() == null || session.getHall().getId() == null) {
            throw new IllegalArgumentException("Hall must be selected");
        }

        Hall hall = hallRepository.findById(session.getHall().getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Hall not found"));

        session.setHall(hall);

        return examSessionRepository.save(session);
    }

    public List<ExamSession> getAllSessions() {
        return examSessionRepository.findAll();
    }

    public ExamSession getSessionById(Long id) {
        return examSessionRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Exam session not found with id: " + id));
    }

    public ExamSession updateSession(Long id, ExamSession newSession) {

        ExamSession session = getSessionById(id);

        session.setExamName(newSession.getExamName());
        session.setExamDate(newSession.getExamDate());

        if (newSession.getHall() == null ||
                newSession.getHall().getId() == null) {
            throw new IllegalArgumentException("Hall must be selected");
        }

        Hall hall = hallRepository.findById(newSession.getHall().getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Hall not found"));

        session.setHall(hall);

        return examSessionRepository.save(session);
    }

    public void deleteSession(Long id) {
        ExamSession session = getSessionById(id);
        examSessionRepository.delete(session);
    }
}