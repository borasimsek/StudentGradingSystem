package com.example.sgs.Entities;

public class Schedule {

    private int scheduleId;
    private User user;
    private Course course;
    private DayOfWeek dayOfWeek;
    private String startTime;
    private String endTime;
    private String room;

    // Constructor
    public Schedule(int scheduleId, User user, Course course, DayOfWeek dayOfWeek, String startTime, String endTime, String room) {
        this.scheduleId = scheduleId;
        this.user = user;
        this.course = course;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
        this.room = room;
    }

    // Getters and Setters
    public int getScheduleId() { return scheduleId; }
    public void setScheduleId(int scheduleId) { this.scheduleId = scheduleId; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }

    public DayOfWeek getDayOfWeek() { return dayOfWeek; }
    public void setDayOfWeek(DayOfWeek dayOfWeek) { this.dayOfWeek = dayOfWeek; }

    public String getStartTime() { return startTime; }
    public void setStartTime(String startTime) { this.startTime = startTime; }

    public String getEndTime() { return endTime; }
    public void setEndTime(String endTime) { this.endTime = endTime; }

    public String getRoom() { return room; }
    public void setRoom(String room) { this.room = room; }

    // Enum for DayOfWeek
    public enum DayOfWeek {
        MONDAY,
        TUESDAY,
        WEDNESDAY,
        THURSDAY,
        FRIDAY
    }
}
