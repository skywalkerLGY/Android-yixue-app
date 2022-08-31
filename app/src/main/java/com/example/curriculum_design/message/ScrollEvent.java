package com.example.curriculum_design.message;


public class ScrollEvent {
    private String letter;
    private boolean isLast;

     public ScrollEvent(String letter, boolean isLast) {
        this.letter = letter;
        this.isLast = isLast;
    }

    public String getLetter() {
        return letter;
    }

    public boolean isLast() {
        return isLast;
    }
}
