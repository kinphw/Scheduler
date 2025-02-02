package main.model;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class TestScheduleDAO extends ScheduleDAO {
    
    @Override
    public List<Schedule> getSchedulesByPerson(String person) {
        List<Schedule> schedules = new ArrayList<>();
        
        // 테스트용 Schedule 객체 생성
        Schedule testSchedule = new Schedule();
        testSchedule.setId(1);
        testSchedule.setPerson("gy");
        testSchedule.setDay("월");
        testSchedule.setStartTime(Time.valueOf("09:00:00"));
        testSchedule.setEndTime(Time.valueOf("09:30:00"));
        testSchedule.setContent("테스트 일정");
        
        schedules.add(testSchedule);
        return schedules;
    }
}