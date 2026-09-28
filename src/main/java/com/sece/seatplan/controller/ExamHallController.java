package com.sece.seatplan.controller;

import com.sece.seatplan.entity.ExamHall;
import com.sece.seatplan.service.ExamHallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/halls")
public class ExamHallController {

    @Autowired
    private ExamHallService examHallService;

    @PostMapping
    public ExamHall createHall(@RequestBody ExamHall examHall) {
        return examHallService.saveHall(examHall);
    }

    @GetMapping
    public List<ExamHall> getAllHalls() {
        return examHallService.getAllHalls();
    }

    @GetMapping("/{id}")
    public ExamHall getHallById(@PathVariable Long id) {
        return examHallService.getHallById(id).orElse(null);
    }

    @DeleteMapping("/{id}")
    public String deleteHall(@PathVariable Long id) {
        examHallService.deleteHall(id);
        return "Exam hall deleted successfully with id: " + id;
    }
}