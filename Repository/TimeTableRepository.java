package Repository;

import model.Timetable;

public interface TimeTableRepository {
    Timetable findTimetableByStudentId(String id);
    void saveTimetable(String id, Timetable timetable);
    void deleteTimetable(String id);

}
