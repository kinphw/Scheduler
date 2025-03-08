package model;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

// public class TestScheduleDAO extends ScheduleDAO {
    
//     @Override
//     public List<Schedule> getSchedulesByPerson(String person) {
//         List<Schedule> schedules = new ArrayList<>();
        
//         // 첫 번째 테스트 일정
//         Schedule schedule1 = new Schedule();
//         schedule1.setId(1);
//         schedule1.setPerson("gy");
//         schedule1.setDay("월");
//         schedule1.setStartTime(Time.valueOf("09:00:00"));
//         schedule1.setEndTime(Time.valueOf("09:30:00"));
//         schedule1.setContent("테스트 일정");
//         schedule1.setColor("blue");
//         schedules.add(schedule1);

//         // 두 번째 테스트 일정 - 새로운 객체 생성
//         Schedule schedule2 = new Schedule();
//         schedule2.setId(2);
//         schedule2.setPerson("gy");
//         schedule2.setDay("수");
//         schedule2.setStartTime(Time.valueOf("09:30:00"));
//         schedule2.setEndTime(Time.valueOf("10:30:00"));
//         schedule2.setContent("테스트2");
//         schedule2.setColor("yellow");
//         schedules.add(schedule2);

//         return schedules;
//     }
// }