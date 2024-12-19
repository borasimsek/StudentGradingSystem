package com.example.sgs.Service;
import com.example.sgs.Repository.CourseRepository;
import com.example.sgs.model.Course;
import java.util.ArrayList;
import java.util.List;

public abstract class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }
    @Override
    public List<Course> getAllCourses() {
        // Ideally, fetch all courses from the repository
        List<Course> courses = new ArrayList<>();
        // Placeholder for logic to fetch all courses from the database
        // Replace with actual implementation as needed
        System.out.println("Fetching all courses...");
        return courses;
    }
    @Override
    public Course getCourseById(int id) {
        // Fetch course by ID from the repository
        Course course = courseRepository.findCourseById(String.valueOf(id));
        if (course != null) {
            System.out.println("Course found: " + course.getCourseName());
        } else {
            System.out.println("Course with ID " + id + " not found.");
        }
        return course;
    }
    @Override
    public void addCourse(Course course) {
        // Add a new course using the repository
        courseRepository.save(course);
        System.out.println("Course added: " + course.getCourseName());
    }
    @Override
    public void updateCourse(Course course) {
        // Update an existing course
        courseRepository.delete(course.getCourseId()); // Delete the old version
        courseRepository.save(course); // Save the updated version
        System.out.println("Course updated: " + course.getCourseName());
    }
    @Override
    public void deleteCourse(int id) {
        // Delete a course using its ID
        courseRepository.delete(String.valueOf(id));
        System.out.println("Course deleted with ID: " + id);
    }


}
