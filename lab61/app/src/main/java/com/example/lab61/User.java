package com.example.lab61;

public class User {
    private String name, srname, date, phone;

    public User(String name, String srname, String date, String phone) {
        this.name = name;
        this.srname = srname;
        this.date = date;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSrName() {
        return srname;
    }

    public void setSrName(String age) {
        this.srname = srname;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
