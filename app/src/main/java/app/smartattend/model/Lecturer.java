package app.smartattend.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Lecturer {
    @PrimaryKey(autoGenerate = true) private int id;
    @ColumnInfo private String lecNo;
    @ColumnInfo private String name;

    public Lecturer(String lecNo, String name) {
        this.lecNo = lecNo;
        this.name = name;
    }
    public Lecturer(){}

    public String getLecNo() {
        return lecNo;
    }

    public void setLecNo(String lecNo) {
        this.lecNo = lecNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
