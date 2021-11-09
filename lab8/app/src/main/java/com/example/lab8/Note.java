package com.example.lab8;

import java.io.Serializable;

public class Note implements Serializable {
    public String date, noteValue, category;

    public Note(String date, String noteValue, String category) {
        this.date = date;
        this.noteValue = noteValue;
        this.category = category;
    }

    public Note() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getValue() {
        return noteValue;
    }

    public void setValue(String noteValue) {
        this.noteValue = noteValue;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

}
