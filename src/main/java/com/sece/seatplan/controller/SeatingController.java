package com.sece.seatplan.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/seating")
@CrossOrigin(origins = "*") // Allows request handling from local web pages
public class SeatingController {

    @PostMapping("/generate/{hallName}")
    public ResponseEntity<List<SeatDto>> generateSeating(@PathVariable String hallName) {
        if (hallName == null || hallName.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        // List of valid hall names for testing
        List<String> validHalls = List.of("FF-07", "LH-101", "LH-102", "HALL-A");

        // Returns 404 Not Found if hall isn't in system (triggers JS 'else' alert)
        if (!validHalls.contains(hallName.toUpperCase())) {
            return ResponseEntity.notFound().build();
        }

        // Generate seating arrangement matrix for the requested hall
        List<SeatDto> seatingList = generateSeatingGrid(hallName);

        return ResponseEntity.ok(seatingList);
    }

    /**
     * Generates a sample 3x3 grid layout with alternating departments.
     * Replace this method body with your database query or allocation algorithm.
     */
    private List<SeatDto> generateSeatingGrid(String hallName) {
        List<SeatDto> seats = new ArrayList<>();
        String[] departments = {"CSE", "ECE", "MECH", "CIVIL", "EEE"};
        String[] classes = {"CS-3A", "EC-3B", "ME-3A", "CV-2A", "EE-4A"};

        int studentCounter = 101;

        // Generates a 3-row x 3-column seating matrix
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                int deptIndex = (r + c) % departments.length;
                seats.add(new SeatDto(
                        r,
                        c,
                        "Student " + studentCounter,
                        classes[deptIndex],
                        departments[deptIndex],
                        "REG2026" + studentCounter
                ));
                studentCounter++;
            }
        }
        return seats;
    }

    // Data Transfer Object (DTO) matching frontend JavaScript fields exactly
    public static class SeatDto {
        private int rowNo;
        private int colNo;
        private String studentName;
        private String classGroup;
        private String department;
        private String studentRegisterNo;

        public SeatDto() {}

        public SeatDto(int rowNo, int colNo, String studentName, String classGroup, String department, String studentRegisterNo) {
            this.rowNo = rowNo;
            this.colNo = colNo;
            this.studentName = studentName;
            this.classGroup = classGroup;
            this.department = department;
            this.studentRegisterNo = studentRegisterNo;
        }

        public int getRowNo() { return rowNo; }
        public void setRowNo(int rowNo) { this.rowNo = rowNo; }

        public int getColNo() { return colNo; }
        public void setColNo(int colNo) { this.colNo = colNo; }

        public String getStudentName() { return studentName; }
        public void setStudentName(String studentName) { this.studentName = studentName; }

        public String getClassGroup() { return classGroup; }
        public void setClassGroup(String classGroup) { this.classGroup = classGroup; }

        public String getDepartment() { return department; }
        public void setDepartment(String department) { this.department = department; }

        public String getStudentRegisterNo() { return studentRegisterNo; }
        public void setStudentRegisterNo(String studentRegisterNo) { this.studentRegisterNo = studentRegisterNo; }
    }
}