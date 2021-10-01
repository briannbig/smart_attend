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
    @PrimaryKey @NonNull private String courseCode;
    @ColumnInfo private Long startTime;
    @ColumnInfo private Long endTime;

    public Lesson(String courseCode, Long startTime, Long endTime) {
        this.courseCode = courseCode;
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

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
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
