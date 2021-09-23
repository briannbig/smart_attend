package app.smartattend.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Student {
    @PrimaryKey(autoGenerate = true) private int id;
    @ColumnInfo private String reg_no;
    @ColumnInfo private String name;
    @ColumnInfo private String enrolledClass;
    public Student(){}
    public Student(String reg_no, String name, String enrolledClass) {
        this.reg_no = reg_no;
        this.name = name;
        this.enrolledClass = enrolledClass;
    }

    public String getReg_no() {
        return reg_no;
    }

    public void setReg_no(String reg_no) {
        this.reg_no = reg_no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnrolledClass() {
        return enrolledClass;
    }

    public void setEnrolledClass(String enrolledClass) {
        this.enrolledClass = enrolledClass;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
