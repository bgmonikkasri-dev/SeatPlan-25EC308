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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class SeatingService {

    private final ExamSessionRepository examSessionRepository;
    private final StudentRepository studentRepository;
    private final SeatRepository seatRepository;

    public SeatingService(ExamSessionRepository examSessionRepository,
                          StudentRepository studentRepository,
                          SeatRepository seatRepository) {

        this.examSessionRepository = examSessionRepository;
        this.studentRepository = studentRepository;
        this.seatRepository = seatRepository;
    }

    @Transactional
    public List<SeatDto> generateSeatingPlan(Long sessionId) {

        // Find exam session
        ExamSession session = examSessionRepository.findById(sessionId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Exam session not found with id: " + sessionId));

        // Get hall
        Hall hall = session.getHall();

        if (hall == null) {
            throw new ResourceNotFoundException(
                    "No hall assigned to this exam session.");
        }

        // Get all students
        List<Student> students = new ArrayList<>(
                studentRepository.findAll()
        );

        if (students.isEmpty()) {
            throw new ResourceNotFoundException(
                    "No students found for seating allocation.");
        }

        // Check capacity
        if (students.size() > hall.getCapacity()) {
            throw new CapacityExceededException(
                    "Students (" + students.size()
                            + ") exceed hall capacity ("
                            + hall.getCapacity() + ")");
        }

        // Delete previous seating plan
        seatRepository.deleteByExamSession_Id(sessionId);

        int rows = hall.getRowsCount();
        int cols = hall.getColsCount();

        Seat[][] grid = new Seat[rows][cols];

        List<Seat> seats = new ArrayList<>();

        /*
         * Allocate every student.
         */
        for (int row = 0; row < rows; row++) {

            for (int col = 0; col < cols; col++) {

                // Stop when every student has been allocated
                if (students.isEmpty()) {
                    break;
                }

                Student student = findBestCandidate(
                        students,
                        grid,
                        row,
                        col
                );

                /*
                 * If no suitable candidate is found,
                 * simply take the first remaining student.
                 *
                 * This guarantees that no student is skipped.
                 */
                if (student == null) {
                    student = students.get(0);
                }

                Seat seat = new Seat();

                seat.setRowNo(row);
                seat.setColNo(col);
                seat.setStudent(student);
                seat.setExamSession(session);

                // Put seat in grid
                grid[row][col] = seat;

                // Add seat to save list
                seats.add(seat);

                // Remove allocated student
                students.remove(student);
            }
        }

        /*
         * Safety check.
         *
         * If students are still remaining, the hall did not
         * actually have enough usable positions.
         */
        if (!students.isEmpty()) {
            throw new CapacityExceededException(
                    "Not enough seats available for all students.");
        }

        /*
         * Save ALL seats.
         */
        List<Seat> savedSeats = seatRepository.saveAll(seats);

        /*
         * Convert to DTO.
         */
        List<SeatDto> result = new ArrayList<>();

        for (Seat seat : savedSeats) {
            result.add(convertToDto(seat));
        }

        return result;
    }

    /*
     * Find a student that does not conflict with
     * the left or top adjacent student.
     */
    private Student findBestCandidate(List<Student> students,
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

        /*
         * First try to find a student with no conflict
         * with either adjacent seat.
         */
        for (Student student : students) {

            boolean leftValid = true;
            boolean topValid = true;

            if (leftSeat != null) {

                leftValid = !hasConflict(
                        student,
                        leftSeat.getStudent()
                );
            }

            if (topSeat != null) {

                topValid = !hasConflict(
                        student,
                        topSeat.getStudent()
                );
            }

            if (leftValid && topValid) {
                return student;
            }
        }

        /*
         * If no perfect candidate exists, return null.
         *
         * The caller will then use the first remaining student.
         */
        return null;
    }

    /*
     * Check whether two students have a conflict.
     *
     * Conflict:
     * 1. Same class + same section
     * OR
     * 2. Same department
     */
    private boolean hasConflict(Student s1, Student s2) {

        if (s1 == null || s2 == null) {
            return false;
        }

        boolean sameClassSection =
                s1.getClassGroup() != null
                        && s2.getClassGroup() != null
                        && s1.getSection() != null
                        && s2.getSection() != null
                        && s1.getClassGroup().equalsIgnoreCase(
                                s2.getClassGroup()
                        )
                        && s1.getSection().equalsIgnoreCase(
                                s2.getSection()
                        );

        boolean sameDepartment =
                s1.getDepartment() != null
                        && s2.getDepartment() != null
                        && s1.getDepartment().equalsIgnoreCase(
                                s2.getDepartment()
                        );

        return sameClassSection || sameDepartment;
    }

    /*
     * Get seating plan for an exam session.
     */
    public List<SeatDto> getSeatingBySession(Long sessionId) {

        List<Seat> seats =
                seatRepository.findByExamSession_Id(sessionId);

        if (seats.isEmpty()) {

            throw new ResourceNotFoundException(
                    "No seats found for exam session ID: "
                            + sessionId
            );
        }

        List<SeatDto> result = new ArrayList<>();

        for (Seat seat : seats) {
            result.add(convertToDto(seat));
        }

        return result;
    }

    /*
     * Get seating information for a student.
     */
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

    /*
     * Convert Seat entity to SeatDto.
     */
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