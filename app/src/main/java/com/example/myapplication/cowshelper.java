package com.example.myapplication;

public class cowshelper {
    String Cowid,DOB,gender,parentid;

    public cowshelper() {
    }

    public cowshelper(String cowid, String DOB, String gender, String parentid) {
        this.Cowid = cowid;
        this.DOB = DOB;
        this.gender = gender;
        this.parentid = parentid;

    }

    public String getCowid() {
        return Cowid;
    }

    public void setCowid(String cowid) {
        Cowid = cowid;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }


    @Override
    public String toString() {
        return
                "Cow's id number : " + Cowid + "\n" +
                "Date of Birth : " + DOB + "\n" +
                "Gender : " + gender + "\n" +
                "Parentid : " + parentid + "\n"+
                "--------------------------------------------------------------"
                ;
    }
}
