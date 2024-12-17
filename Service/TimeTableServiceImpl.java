package Service;
import Repository.TimeTableRepository;
import model.Timetable;
public abstract class TimeTableServiceImpl implements TimeTableService {
    private final TimeTableRepository timeTableRepository;
    public TimeTableServiceImpl(TimeTableRepository timeTableRepository) {
        this.timeTableRepository = timeTableRepository;
    }
    @Override
    public Timetable getTimetable(String studentId) {
        // Fetch the timetable for a specific student
        Timetable timetable = timeTableRepository.findTimetableByStudentId(studentId);
        if (timetable != null) {
            System.out.println("Retrieved timetable for Student ID: " + studentId);
        } else {
            System.out.println("No timetable found for Student ID: " + studentId);
        }
        return timetable;
    }
    @Override
    public void saveTimetable(String studentId, Timetable timetable) {
        // Save or update the timetable for the student
        timeTableRepository.saveTimetable(studentId, timetable);
        System.out.println("Timetable saved for Student ID: " + studentId);
    }
    @Override
    public void deleteTimetable(String studentId) {
        // Delete the timetable for the student
        timeTableRepository.deleteTimetable(studentId);
        System.out.println("Timetable deleted for Student ID: " + studentId);
    }

}
