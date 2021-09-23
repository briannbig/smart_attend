package app.smartattend.model;

public class Class {
   private String classCode, program;

    public Class(String classCode, String program) {
        this.classCode = classCode;
        this.program = program;
    }
    public Class(){}

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }
}
