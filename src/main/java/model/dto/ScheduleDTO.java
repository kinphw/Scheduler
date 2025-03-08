// src/main/java/model/dto/ScheduleDTO.java
package model.dto;

import java.util.List;
import model.Schedule;

public class ScheduleDTO {
    private String person;
    private List<Schedule> schedules;
    private String schedulesJson;

    public ScheduleDTO(String person, List<Schedule> schedules, String schedulesJson) {
        this.person = person;
        this.schedules = schedules;
        this.schedulesJson = schedulesJson;
    }

    // Getters and setters
    public String getPerson() { return person; }
    public List<Schedule> getSchedules() { return schedules; }
    public String getSchedulesJson() { return schedulesJson; }
}