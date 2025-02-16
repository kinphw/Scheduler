package main.model;

import java.sql.Time;
import java.time.LocalTime;
import java.sql.Timestamp;

public class Schedule {
    private int id;
    private String person;
    private String day;
    private LocalTime startTime;
    private LocalTime endTime;
    private String content;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String color;

    // 기본 생성자
    public Schedule() {
    }

    // 전체 필드 생성자
    public Schedule(int id, String person, String day, LocalTime startTime, LocalTime endTime,
                   String content, Timestamp createdAt, Timestamp updatedAt, String color) {
        this.id = id;
        this.person = person;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.color = color;
    }

    // Getter 메서드
    public int getId() { return id; }
    public String getPerson() { return person; }
    public String getDay() { return day; }
    public LocalTime getStartTime() { return startTime; }
    public LocalTime getEndTime() { return endTime; }
    public String getContent() { return content; }
    public Timestamp getCreatedAt() { return createdAt; }
    public Timestamp getUpdatedAt() { return updatedAt; }
    public String getColor() { return color; }

    // Setter 메서드
    public void setId(int id) { this.id = id; }
    public void setPerson(String person) { this.person = person; }
    public void setDay(String day) { this.day = day; }
    public void setStartTime(LocalTime startTime) { this.startTime = startTime; }
    public void setEndTime(LocalTime endTime) { this.endTime = endTime; }
    public void setContent(String content) { this.content = content; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; }
    public void setColor(String color) { this.color = color; }

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
                ", color='" + color + '\'' +
                '}';
    }
}