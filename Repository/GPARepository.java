package Repository;

public interface GPARepository {
    double findGPAbyStudentID(String studentID);
    void saveGPA(String studentID, double gpa);
}
