package com.example.sgs.Repository;

import com.example.sgs.model.Timetable;

public interface TimeTableRepository {
    Timetable findTimetableByStudentId(String id);
    void saveTimetable(String id, Timetable timetable);
    void deleteTimetable(String id);

}
