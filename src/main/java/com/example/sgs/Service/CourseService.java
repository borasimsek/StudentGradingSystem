package com.example.sgs.Service;

import com.example.sgs.Repository.CourseRepository;
import com.example.sgs.model.Course;


public class CourseService {
    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Course findCourseById(String courseId) {
        Course course = courseRepository.findCourseById(courseId);
        if (course == null) {
            throw new IllegalArgumentException("Course not found with ID: " + courseId);
        }
        return course;
    }

    public void addCourse(Course course) {
        courseRepository.save(course);
    }

    public void deleteCourse(String courseId) {
        courseRepository.delete(courseId);
    }
}
