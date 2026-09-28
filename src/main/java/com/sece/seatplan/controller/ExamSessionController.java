package com.sece.seatplan.controller;

import com.sece.seatplan.entity.ExamSession;
import com.sece.seatplan.service.ExamSessionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/examsessions")
public class ExamSessionController {

    private final ExamSessionService examSessionService;

    public ExamSessionController(ExamSessionService examSessionService) {
        this.examSessionService = examSessionService;
    }

    @PostMapping
    public ExamSession createSession(
            @RequestBody ExamSession session) {

        return examSessionService.saveSession(session);
    }

    @GetMapping
    public List<ExamSession> getAllSessions() {
        return examSessionService.getAllSessions();
    }

    @GetMapping("/{id}")
    public ExamSession getSessionById(
            @PathVariable Long id) {

        return examSessionService.getSessionById(id);
    }

    @PutMapping("/{id}")
    public ExamSession updateSession(
            @PathVariable Long id,
            @RequestBody ExamSession session) {

        return examSessionService.updateSession(id, session);
    }

    @DeleteMapping("/{id}")
    public String deleteSession(
            @PathVariable Long id) {

        examSessionService.deleteSession(id);

        return "Exam session deleted successfully";
    }
}