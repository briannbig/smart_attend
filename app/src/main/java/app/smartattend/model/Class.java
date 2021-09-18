package app.smartattend.model;

public class Class {
    private String id, program;

    public Class(String id, String program) {
        this.id = id;
        this.program = program;
    }
    public Class(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }
}
