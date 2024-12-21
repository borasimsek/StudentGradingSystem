package com.example.sgs.Service;

import com.example.sgs.Repository.GPARepository;

public class GPAService {
    private final GPARepository gpaRepository;

    public GPAService(GPARepository gpaRepository) {
        this.gpaRepository = gpaRepository;
    }

    public double getCumulativeGPA(int studentId) {
        return gpaRepository.calculateCumulativeGPA(studentId);
    }

    public double getTermGPA(int studentId, int year, String term) {
        return gpaRepository.calculateTermGPA(studentId, year, term);
    }
}
