package app.smartattend.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
        ArrayList<String> list = new ArrayList<String>();
        Map<String, Integer> hm = new HashMap<String, Integer>();
        for (String i : list) {
            Integer j = hm.get(i);
            hm.put(i, (j ==null)? 1 : j + 1);
        }
        for (Map.Entry<String, Integer> val : hm.entrySet()){

        }
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
