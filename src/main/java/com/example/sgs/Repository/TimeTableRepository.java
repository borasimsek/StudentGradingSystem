package com.example.sgs.Repository;

import com.example.sgs.Entities.Schedule;
import com.example.sgs.Entities.User;
import com.example.sgs.Entities.Course;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TimeTableRepository {

    private final Connection connection;

    public TimeTableRepository(Connection connection) {
        this.connection = connection;
    }

    // Fetch timetable for a specific user
    public List<Schedule> findByUserId(int userId) {
        String query = "SELECT * FROM schedules WHERE user_id = ?";
        List<Schedule> schedules = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    schedules.add(mapResultSetToSchedule(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching timetable for user ID: " + userId + " - " + e.getMessage());
        }
        return schedules;
    }

    // Fetch timetable for a specific course
    public List<Schedule> findByCourseId(int courseId) {
        String query = "SELECT * FROM schedules WHERE course_id = ?";
        List<Schedule> schedules = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, courseId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    schedules.add(mapResultSetToSchedule(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching timetable for course ID: " + courseId + " - " + e.getMessage());
        }
        return schedules;
    }

    // Save a new schedule
    public boolean save(Schedule schedule) {
        String query = "INSERT INTO schedules (user_id, course_id, day_of_week, start_time, end_time, room) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, schedule.getUser().getUserId()); // Access User entity
            stmt.setInt(2, schedule.getCourse().getCourseId()); // Access Course entity
            stmt.setString(3, schedule.getDayOfWeek().toString()); // Enum to String
            stmt.setString(4, schedule.getStartTime());
            stmt.setString(5, schedule.getEndTime());
            stmt.setString(6, schedule.getRoom());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error saving schedule: " + schedule + " - " + e.getMessage());
        }
        return false;
    }

    // Helper method to map a ResultSet to a Schedule object
    private Schedule mapResultSetToSchedule(ResultSet rs) throws SQLException {
        // Stub objects for User and Course, replace with actual repository calls if needed
        User user = new User(rs.getInt("user_id"), null, null, null, null, null);
        Course course = new Course(rs.getInt("course_id"), null, null, null, 0, 0, 0, null, null);

        return new Schedule(
                rs.getInt("schedule_id"),
                user,
                course,
                Schedule.DayOfWeek.valueOf(rs.getString("day_of_week").toUpperCase()),
                rs.getString("start_time"),
                rs.getString("end_time"),
                rs.getString("room")
        );
    }
}
