package app.smartattend.model;

public class Course {
    private String code, title, lecturer, classId;

    public Course(String code, String title, String lecturer, String classId) {
        this.code = code;
        this.title = title;
        this.lecturer = lecturer;
        this.classId = classId;
    }
    public Course(){}

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLecturer() {
        return lecturer;
    }

    public void setLecturer(String lecturer) {
        this.lecturer = lecturer;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }
}
