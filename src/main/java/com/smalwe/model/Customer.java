package com.smalwe.model;

public class Customer {

    private Integer id;
    private String fname;
    private String lname;

    public Customer() {

    }

    public Customer(Integer id, String fname, String lname) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }
}
