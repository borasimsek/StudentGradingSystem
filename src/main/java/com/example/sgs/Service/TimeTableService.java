package com.example.sgs.Service;

import com.example.sgs.Entities.Schedule;
import com.example.sgs.Repository.TimeTableRepository;

import java.util.List;

public class TimeTableService {

    private final TimeTableRepository timetableRepository;

    public TimeTableService(TimeTableRepository timetableRepository) {
        this.timetableRepository = timetableRepository;
    }

    // Fetch timetable for a specific user
    public List<Schedule> getUserTimetable(int userId) {
        return timetableRepository.findByUserId(userId);
    }

    // Fetch timetable for a specific course
    public List<Schedule> getCourseTimetable(int courseId) {
        return timetableRepository.findByCourseId(courseId);
    }

    // Add a new schedule
    public boolean addSchedule(Schedule schedule) {
        return timetableRepository.save(schedule);
    }
}
