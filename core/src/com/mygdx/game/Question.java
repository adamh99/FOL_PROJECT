package com.mygdx.game;
public class Question {
    private String subject;
    private String title;
    private String instruction;
    private String[] options;
    private String validOption;

    public Question(String subject, String title, String instruction, String[] options, String validOption) {
        this.subject = subject;
        this.title = title;
        this.instruction = instruction;
        this.options = options;
        this.validOption = validOption;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instructions) {
        this.instruction = instructions;
    }

    public String[] getOptions() {
        return options;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }

    public String getValidOption() {
        return validOption;
    }

    public void setValidOption(String validOption) {
        this.validOption = validOption;
    }
}