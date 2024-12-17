package Service;
import model.Course;
import java.util.List;
public interface CourseService {
    List<Course> getAllCourses();
    Course getCourseById(int id);
    void addCourse(Course course);
    void updateCourse(Course course);
    void deleteCourse(int id);
}
