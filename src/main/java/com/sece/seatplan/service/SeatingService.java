package com.sece.seatplan.service;

import com.sece.seatplan.entity.ExamHall;
import com.sece.seatplan.entity.SeatingArrangement;
import com.sece.seatplan.entity.Student;
import com.sece.seatplan.repository.ExamHallRepository;
import com.sece.seatplan.repository.SeatingArrangementRepository;
import com.sece.seatplan.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SeatingService {

    @Autowired
    private ExamHallRepository examHallRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private SeatingArrangementRepository seatingArrangementRepository;

    @Transactional
    public List<SeatingArrangement> generateSeatingPlan(String hallName) {
        Optional<ExamHall> hallOpt = examHallRepository.findByHallName(hallName);
        if (hallOpt.isEmpty()) {
            throw new RuntimeException("Exam hall not found: " + hallName);
        }

        ExamHall hall = hallOpt.get();
        List<Student> availableStudents = new ArrayList<>(studentRepository.findAll());

        // Clear previous arrangements for this hall before regenerating
        seatingArrangementRepository.deleteByHallName(hallName);

        int rows = hall.getRowsCount();
        int cols = hall.getColsCount();
        SeatingArrangement[][] grid = new SeatingArrangement[rows][cols];
        List<SeatingArrangement> savedArrangements = new ArrayList<>();

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (availableStudents.isEmpty()) {
                    break;
                }

                Student chosenStudent = findBestCandidate(availableStudents, grid, r, c);

                if (chosenStudent != null) {
                    SeatingArrangement seat = new SeatingArrangement(
                            hallName, r, c,
                            chosenStudent.getRegisterNo(),
                            chosenStudent.getName(),
                            chosenStudent.getClassGroup(),
                            chosenStudent.getDepartment()
                    );
                    grid[r][c] = seat;
                    savedArrangements.add(seatingArrangementRepository.save(seat));
                    availableStudents.remove(chosenStudent);
                }
            }
        }

        return savedArrangements;
    }

    private Student findBestCandidate(List<Student> students, SeatingArrangement[][] grid, int row, int col) {
        SeatingArrangement leftNeighbor = (col > 0) ? grid[row][col - 1] : null;
        SeatingArrangement topNeighbor = (row > 0) ? grid[row - 1][col] : null;

        Student softCandidate = null;

        for (Student student : students) {
            // Check Hard Constraint: Same classGroup CANNOT sit next to each other
            boolean leftHardValid = (leftNeighbor == null) || !leftNeighbor.getClassGroup().equals(student.getClassGroup());
            boolean topHardValid = (topNeighbor == null) || !topNeighbor.getClassGroup().equals(student.getClassGroup());

            if (leftHardValid && topHardValid) {
                // Check Soft Constraint: Prefer different departments
                boolean leftSoftValid = (leftNeighbor == null) || !leftNeighbor.getDepartment().equals(student.getDepartment());
                boolean topSoftValid = (topNeighbor == null) || !topNeighbor.getDepartment().equals(student.getDepartment());

                if (leftSoftValid && topSoftValid) {
                    return student; // Best candidate (satisfies both Hard and Soft constraints)
                }

                if (softCandidate == null) {
                    softCandidate = student; // Fallback candidate (satisfies Hard constraint)
                }
            }
        }

        return softCandidate;
    }

    public List<SeatingArrangement> getSeatingByHall(String hallName) {
        return seatingArrangementRepository.findByHallName(hallName);
    }
}