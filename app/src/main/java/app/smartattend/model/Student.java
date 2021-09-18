package app.smartattend.model;

public class Student {
    private String reg_no, name, enrolledClass;
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
}
