
// Timetable.java
package com.example.sgs.model;

import java.util.Map;

/**
 * Represents a Timetable for a student or instructor.
 */
public class Timetable {
    private String ownerId;
    private Map<String, String> schedule;

    // Constructor
    public Timetable(String ownerId, Map<String, String> schedule) {
        this.ownerId = ownerId;
        this.schedule = schedule;
    }

    // Getters and Setters
    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public Map<String, String> getSchedule() {
        return schedule;
    }

    public void setSchedule(Map<String, String> schedule) {
        this.schedule = schedule;
    }

    @Override
    public String toString() {
        return "Timetable{" +
                "ownerId='" + ownerId + '\'' +
                ", schedule=" + schedule +
                '}';
    }
}