package com.sece.seatplan.controller;

import com.sece.seatplan.dto.HallDto;
import com.sece.seatplan.entity.Hall;
import com.sece.seatplan.service.HallService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/halls")
public class HallController {

    private final HallService hallService;

    public HallController(HallService hallService) {
        this.hallService = hallService;
    }

    @PostMapping
    public Hall createHall(@Valid @RequestBody HallDto dto) {
        return hallService.saveHall(dto);
    }

    @GetMapping
    public List<Hall> getAllHalls() {
        return hallService.getAllHalls();
    }

    @GetMapping("/{id}")
    public Hall getHallById(@PathVariable Long id) {
        return hallService.getHallById(id);
    }

    @PutMapping("/{id}")
    public Hall updateHall(
            @PathVariable Long id,
            @Valid @RequestBody HallDto dto) {

        return hallService.updateHall(id, dto);
    }

    @DeleteMapping("/{id}")
    public String deleteHall(@PathVariable Long id) {

        hallService.deleteHall(id);

        return "Exam hall deleted successfully";
    }
}