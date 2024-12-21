package com.example.sgs.Repository;

import com.example.sgs.Entities.Course;
import com.example.sgs.Entities.Prerequisite;
import com.example.sgs.Entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CourseRepository {

    private final Connection connection;

    public CourseRepository(Connection connection) {
        this.connection = connection;
    }

    // Find a course by ID
    public Optional<Course> findById(int courseId) {
        String query = "SELECT * FROM courses WHERE course_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, courseId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToCourse(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding course by ID: " + e.getMessage());
        }
        return Optional.empty();
    }

    // Find a course by name
    public List<Course> findByCourseName(String courseName) {
        String query = "SELECT * FROM courses WHERE course_name LIKE ?";
        List<Course> courses = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, "%" + courseName + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    courses.add(mapResultSetToCourse(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding courses by name: " + e.getMessage());
        }
        return courses;
    }

    // Find all courses
    public List<Course> findAll() {
        String query = "SELECT * FROM courses";
        List<Course> courses = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                courses.add(mapResultSetToCourse(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching all courses: " + e.getMessage());
        }
        return courses;
    }

    // Save a new course
    public boolean save(Course course) {
        String query = "INSERT INTO courses (course_name, course_code, instructor_id, credits, quota, year, term, faculty) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, course.getCourseName());
            stmt.setString(2, course.getCourseCode());
            stmt.setInt(3, course.getInstructor().getUserId());
            stmt.setInt(4, course.getCredits());
            stmt.setInt(5, course.getQuota());
            stmt.setInt(6, course.getYear());
            stmt.setString(7, course.getTerm().toString());
            stmt.setString(8, course.getFaculty());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error saving course: " + e.getMessage());
        }
        return false;
    }

    // Find prerequisites for a specific course
    public List<Prerequisite> findPrerequisitesByCourseId(int courseId) {
        String query = "SELECT p.course_id, p.prerequisite_id, " +
                "c1.course_name AS course_name, c1.course_code AS course_code, " +
                "c2.course_name AS prerequisite_name, c2.course_code AS prerequisite_code " +
                "FROM prerequisites p " +
                "JOIN courses c1 ON p.course_id = c1.course_id " +
                "JOIN courses c2 ON p.prerequisite_id = c2.course_id " +
                "WHERE p.course_id = ?";
        List<Prerequisite> prerequisites = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, courseId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Course course = new Course(
                            rs.getInt("course_id"),
                            rs.getString("course_name"),
                            rs.getString("course_code"),
                            null, // Instructor can be resolved using UserRepository if needed
                            0,    // Credits are not retrieved in this query
                            0,    // Quota is not retrieved in this query
                            0,    // Year is not retrieved in this query
                            null, // Term is not retrieved in this query
                            null  // Faculty is not retrieved in this query
                    );

                    Course prerequisite = new Course(
                            rs.getInt("prerequisite_id"),
                            rs.getString("prerequisite_name"),
                            rs.getString("prerequisite_code"),
                            null, // Instructor can be resolved using UserRepository if needed
                            0,    // Credits are not retrieved in this query
                            0,    // Quota is not retrieved in this query
                            0,    // Year is not retrieved in this query
                            null, // Term is not retrieved in this query
                            null  // Faculty is not retrieved in this query
                    );

                    prerequisites.add(new Prerequisite(course, prerequisite));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching prerequisites for course ID: " + courseId + " - " + e.getMessage());
        }
        return prerequisites;
    }


    // Helper method to map a ResultSet to a Course object
    private Course mapResultSetToCourse(ResultSet rs) throws SQLException {
        return new Course(
                rs.getInt("course_id"),
                rs.getString("course_name"),
                rs.getString("course_code"),
                null, // Instructor mapping should use UserRepository if necessary
                rs.getInt("credits"),
                rs.getInt("quota"),
                rs.getInt("year"),
                Course.Term.valueOf(rs.getString("term")),
                rs.getString("faculty")
        );
    }

    public List<User> findAllInstructors() {
        String query = "SELECT * FROM users WHERE user_type = 'instructor'";
        List<User> instructors = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                User instructor = new User(
                        rs.getInt("user_id"),
                        rs.getString("email"),
                        rs.getString("password_hash"),
                        User.UserType.INSTRUCTOR,
                        rs.getString("first_name"),
                        rs.getString("last_name")
                );
                instructors.add(instructor);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching instructors: " + e.getMessage());
        }

        return instructors;
    }

    public int getNextCourseId(String faculty) {
        int minId = 0, maxId = 0;

        // Fakülteye göre ID aralıklarını belirle
        switch (faculty) {
            case "Engineering":
                minId = 1;
                maxId = 100;
                break;
            case "Law":
                minId = 101;
                maxId = 200;
                break;
            case "Business":
                minId = 201;
                maxId = 300;
                break;
            case "Language":
                minId = 301;
                maxId = 400;
                break;
            case "Aviation":
                minId = 401;
                maxId = 500;
                break;
            case "Others":
                minId = 501;
                maxId = 1000;
                break;
            default:
                throw new IllegalArgumentException("Invalid faculty: " + faculty);
        }

        String query = "SELECT MAX(course_id) AS max_id FROM courses WHERE course_id BETWEEN ? AND ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, minId);
            stmt.setInt(2, maxId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int maxIdInRange = rs.getInt("max_id");
                    if (maxIdInRange == 0) { // Eğer ID aralığında bir kurs yoksa
                        return minId;
                    }
                    return maxIdInRange + 1; // Yeni kurs ID'sini oluştur
                }
            }
        } catch (SQLException e) {
            System.err.println("Error generating next course ID: " + e.getMessage());
        }

        return minId; // Hata durumunda varsayılan başlangıç ID'si
    }

    public List<Course> findCoursesByRoomAndTime(String building, String room, String day, String startTime, String endTime, int year) {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT * FROM courses WHERE building = ? AND room = ? AND day = ? AND start_time = ? AND end_time = ? AND year = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, building);
            statement.setString(2, room);
            statement.setString(3, day);
            statement.setString(4, startTime);
            statement.setString(5, endTime);
            statement.setInt(6, year);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                courses.add(new Course(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("code"),
                        null, // Eğer dersle ilişkilendirilmiş eğitmen yoksa null olabilir.
                        resultSet.getInt("credits"),
                        resultSet.getInt("quota"),
                        resultSet.getInt("year"),
                        Course.Term.valueOf(resultSet.getString("term").toUpperCase()),
                        resultSet.getString("faculty")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }

    public boolean delete(int courseId) {
        String sql = "DELETE FROM courses WHERE course_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, courseId);
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected); // Hata ayıklama için
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public List<Course> findAllCourses() {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT * FROM courses";

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            UserRepository userRepository = new UserRepository(connection);

            while (resultSet.next()) {
                // Eğitmen bilgisi için UserRepository kullanımı
                User instructor = null;
                int instructorId = resultSet.getInt("instructor_id");
                if (instructorId != 0) { // Eğer instructor_id null değilse
                    instructor = userRepository.findById(instructorId).orElse(null);
                }

                courses.add(new Course(
                        resultSet.getInt("course_id"),
                        resultSet.getString("course_name"),
                        resultSet.getString("course_code"),
                        instructor, // Eğitmen bilgisi atanır
                        resultSet.getInt("credits"),
                        resultSet.getInt("quota"),
                        resultSet.getInt("year"),
                        Course.Term.valueOf(resultSet.getString("term").toUpperCase()),
                        resultSet.getString("faculty")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }


    public int getNextCourseId() throws SQLException {
        String query = "SELECT MAX(course_id) FROM courses";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt(1) + 1;
            }
        }
        return 1; // Eğer veritabanında hiç kayıt yoksa, ID 1 ile başlasın.
    }



}
