package com.example.sgs.Service;

import com.example.sgs.Entities.Course;
import com.example.sgs.Entities.Prerequisite;
import com.example.sgs.Repository.CourseRepository;

import java.util.List;
import java.util.Optional;

public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    // Fetch a course by ID
    public Optional<Course> getCourseById(int courseId) {
        return courseRepository.findById(courseId);
    }

    // Fetch all courses
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    // Save a new course
    public boolean addCourse(Course course) {
        return courseRepository.save(course);
    }

    // Fetch prerequisites for a course
    public List<Prerequisite> getPrerequisitesByCourseId(int courseId) {
        return courseRepository.findPrerequisitesByCourseId(courseId);
    }

    // Search courses by name
    public List<Course> searchCoursesByName(String courseName) {
        return courseRepository.findByCourseName(courseName);
    }
}
