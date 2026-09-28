package com.sece.seatplan.controller;

import com.sece.seatplan.dto.SeatDto;
import com.sece.seatplan.service.SeatingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seating")
public class SeatingController {

    private final SeatingService seatingService;

    public SeatingController(SeatingService seatingService) {
        this.seatingService = seatingService;
    }

    @PostMapping("/generate/{sessionId}")
    public List<SeatDto> generateSeating(
            @PathVariable Long sessionId) {

        return seatingService.generateSeatingPlan(sessionId);
    }

    @GetMapping("/session/{sessionId}")
    public List<SeatDto> getSeatingBySession(
            @PathVariable Long sessionId) {

        return seatingService.getSeatingBySession(sessionId);
    }

    @GetMapping("/student/{registerNo}")
    public List<SeatDto> getStudentSeating(
            @PathVariable String registerNo) {

        return seatingService.getStudentSeating(registerNo);
    }
}