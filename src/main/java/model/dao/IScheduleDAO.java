package model.dao;

import model.Schedule;
import java.util.List;

public interface IScheduleDAO {
    // Read operations
    List<Schedule> getSchedulesByPerson(String person);
    Schedule getScheduleById(int id);
    
    // Write operations
    boolean saveSchedule(Schedule schedule);
    boolean updateSchedule(Schedule schedule);
    boolean deleteSchedule(int id);
}