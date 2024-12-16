package Repository;

public interface GradeRepository {
    void saveGrade(String studentID, String courseID, double grade);
    double findGrade(String studentID, String courseID);


}
