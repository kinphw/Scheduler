package main.model;

import java.sql.Time;
import java.sql.Timestamp;

public class Schedule {
    private int id;
    private String person;
    private String day;
    private Time startTime;
    private Time endTime;
    private String content;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    // 기본 생성자
    public Schedule() {
    }

    // 전체 필드 생성자
    public Schedule(int id, String person, String day, Time startTime, Time endTime, 
                   String content, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.person = person;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getter 메서드
    public int getId() { return id; }
    public String getPerson() { return person; }
    public String getDay() { return day; }
    public Time getStartTime() { return startTime; }
    public Time getEndTime() { return endTime; }
    public String getContent() { return content; }
    public Timestamp getCreatedAt() { return createdAt; }
    public Timestamp getUpdatedAt() { return updatedAt; }

    // Setter 메서드
    public void setId(int id) { this.id = id; }
    public void setPerson(String person) { this.person = person; }
    public void setDay(String day) { this.day = day; }
    public void setStartTime(Time startTime) { this.startTime = startTime; }
    public void setEndTime(Time endTime) { this.endTime = endTime; }
    public void setContent(String content) { this.content = content; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; }

    @Override
    public String toString() {
        return "Schedule{" +
                "id=" + id +
                ", person='" + person + '\'' +
                ", day='" + day + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", content='" + content + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}