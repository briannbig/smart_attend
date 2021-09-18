package app.smartattend.model;

public class Lecturer {
    private String lecNo, name;

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
}
