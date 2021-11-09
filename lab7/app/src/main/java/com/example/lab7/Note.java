package com.example.lab7;

public class Note {
    private String date, note;

    public Note(String date, String note) {
        this.date = date;
        this.note = note;
    }

    public Note() {

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
