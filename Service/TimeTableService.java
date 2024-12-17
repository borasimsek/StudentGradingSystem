package Service;
import model.Timetable;
public interface TimeTableService {
    Timetable getTimeTable(String courseID);
    void deleteTimetable(String studentId);
    Timetable getTimetable(String studentId);
    void saveTimetable(String studentId, Timetable timetable);
}
