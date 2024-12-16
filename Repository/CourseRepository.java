package Repository;

import model.Course;

public interface CourseRepository {
    Course findCourseById(String id);
    void save(Course course);
    void delete(String courseID);


}
