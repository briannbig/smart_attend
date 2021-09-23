package app.smartattend.model;

public class Attendee {
    private String reg_no;
    private Long check_in;

    public Attendee() {
    }

    public Attendee(String reg_no, Long check_in) {
        this.reg_no = reg_no;
        this.check_in = check_in;
    }

    public String getReg_no() {
        return reg_no;
    }

    public void setReg_no(String reg_no) {
        this.reg_no = reg_no;
    }

    public Long getCheck_in() {
        return check_in;
    }

    public void setCheck_in(Long check_in) {
        this.check_in = check_in;
    }

}
