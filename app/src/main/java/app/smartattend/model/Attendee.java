package app.smartattend.model;

public class Attendee {
    private String reg_No;
    private Long time_In;

    public Attendee() {
    }

    public Attendee(String reg_No, Long time_In) {
        this.reg_No = reg_No;
        this.time_In = time_In;
    }

    public String getReg_No() {
        return reg_No;
    }

    public void setReg_No(String reg_No) {
        this.reg_No = reg_No;
    }

    public Long getTime_In() {
        return time_In;
    }

    public void setTime_In(Long time_In) {
        this.time_In = time_In;
    }

}
