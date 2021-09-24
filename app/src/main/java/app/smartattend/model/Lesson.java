package app.smartattend.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Lesson {
    @PrimaryKey @NonNull private String course;
    @ColumnInfo private Long startTime;
    @ColumnInfo private Long endTime;

    public Lesson(String course, Long startTime, Long endTime) {
        this.course = course;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Lesson() {
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }


}
