package Repository;

import java.util.List;

public interface GradeRepository {
    void saveGrade(String studentID, String courseID, double grade);
    double findGrade(String studentID, String courseID);
    // New method to fetch all grades for a specific student
    List<Double> findGradesByStudentId(String studentID);

}
