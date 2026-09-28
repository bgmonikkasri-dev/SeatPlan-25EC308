package com.sece.seatplan.service;

import com.sece.seatplan.dto.SeatDto;
import com.sece.seatplan.entity.ExamSession;
import com.sece.seatplan.entity.Hall;
import com.sece.seatplan.entity.Seat;
import com.sece.seatplan.entity.Student;
import com.sece.seatplan.exception.CapacityExceededException;
import com.sece.seatplan.exception.ResourceNotFoundException;
import com.sece.seatplan.repository.ExamSessionRepository;
import com.sece.seatplan.repository.SeatRepository;
import com.sece.seatplan.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class SeatingService {

    @Autowired
    private ExamSessionRepository examSessionRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Transactional
    public List<SeatDto> generateSeatingPlan(Long sessionId) {

        ExamSession session = examSessionRepository.findById(sessionId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Exam session not found with id: " + sessionId));

        Hall hall = session.getHall();

        List<Student> students = new ArrayList<>(
                studentRepository.findAll()
        );

        if (students.size() > hall.getCapacity()) {
            throw new CapacityExceededException(
                    "Students (" + students.size() +
                    ") exceed hall capacity (" + hall.getCapacity() + ")"
            );
        }

        // Delete old seating plan before generating a new one
        seatRepository.deleteByExamSession_Id(sessionId);

        int rows = hall.getRowsCount();
        int cols = hall.getColsCount();

        Seat[][] grid = new Seat[rows][cols];

        List<SeatDto> result = new ArrayList<>();

        int studentIndex = 0;

        for (int row = 0; row < rows; row++) {

            for (int col = 0; col < cols; col++) {

                if (studentIndex >= students.size()) {
                    break;
                }

                if (studentIndex >= hall.getCapacity()) {
                    break;
                }

                Student student = findBestCandidate(
                        students,
                        grid,
                        row,
                        col
                );

                if (student == null) {
                    throw new IllegalArgumentException(
                            "Unable to generate a seating plan without placing students from the same class group next to each other"
                    );
                }

                Seat seat = new Seat();

                seat.setRowNo(row);
                seat.setColNo(col);
                seat.setStudent(student);
                seat.setExamSession(session);

                seatRepository.save(seat);

                grid[row][col] = seat;

                result.add(convertToDto(seat));

                students.remove(student);

                studentIndex++;
            }
        }

        return result;
    }

    private Student findBestCandidate(
            List<Student> students,
            Seat[][] grid,
            int row,
            int col) {

        Seat leftSeat = null;
        Seat topSeat = null;

        if (col > 0) {
            leftSeat = grid[row][col - 1];
        }

        if (row > 0) {
            topSeat = grid[row - 1][col];
        }

        Student softCandidate = null;

        for (Student student : students) {

            /*
             * HARD CONSTRAINT
             *
             * Same class/section cannot be adjacent.
             */
            boolean leftHardValid =
                    leftSeat == null ||
                    !leftSeat.getStudent()
                            .getClassGroup()
                            .equalsIgnoreCase(student.getClassGroup());

            boolean topHardValid =
                    topSeat == null ||
                    !topSeat.getStudent()
                            .getClassGroup()
                            .equalsIgnoreCase(student.getClassGroup());

            if (!leftHardValid || !topHardValid) {
                continue;
            }

            /*
             * SOFT CONSTRAINT
             *
             * Prefer different departments.
             *
             * Example:
             * CSE -> ECE -> CSE -> ECE
             */
            boolean leftSoftValid =
                    leftSeat == null ||
                    !leftSeat.getStudent()
                            .getDepartment()
                            .equalsIgnoreCase(student.getDepartment());

            boolean topSoftValid =
                    topSeat == null ||
                    !topSeat.getStudent()
                            .getDepartment()
                            .equalsIgnoreCase(student.getDepartment());

            if (leftSoftValid && topSoftValid) {
                return student;
            }

            /*
             * Student satisfies the hard rule,
             * so keep as fallback.
             */
            if (softCandidate == null) {
                softCandidate = student;
            }
        }

        return softCandidate;
    }

    public List<SeatDto> getSeatingBySession(Long sessionId) {

        List<Seat> seats =
                seatRepository.findByExamSession_Id(sessionId);

        List<SeatDto> result = new ArrayList<>();

        for (Seat seat : seats) {
            result.add(convertToDto(seat));
        }

        return result;
    }

    public List<SeatDto> getStudentSeating(String registerNo) {

        List<Seat> seats =
                seatRepository.findByStudent_RegisterNo(registerNo);

        if (seats.isEmpty()) {
            throw new ResourceNotFoundException(
                    "No seating allocation found for register number: "
                            + registerNo
            );
        }

        List<SeatDto> result = new ArrayList<>();

        for (Seat seat : seats) {
            result.add(convertToDto(seat));
        }

        return result;
    }

    private SeatDto convertToDto(Seat seat) {

        SeatDto dto = new SeatDto();

        dto.setRowNo(seat.getRowNo());
        dto.setColNo(seat.getColNo());

        Student student = seat.getStudent();

        dto.setStudentName(student.getName());
        dto.setClassGroup(student.getClassGroup());
        dto.setDepartment(student.getDepartment());
        dto.setStudentRegisterNo(student.getRegisterNo());

        dto.setHallName(
                seat.getExamSession()
                        .getHall()
                        .getHallName()
        );

        dto.setExamName(
                seat.getExamSession()
                        .getExamName()
        );

        return dto;
    }
}