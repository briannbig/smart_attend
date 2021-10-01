package app.smartattend.model;

public class Attendance {
    private String course, reg_No;

    public Attendance( String reg_No, String course) {
        this.course = course;
        this.reg_No = reg_No;
    }

    public Attendance() {
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getReg_No() {
        return reg_No;
    }

    public void setReg_No(String reg_No) {
        this.reg_No = reg_No;
    }
}
