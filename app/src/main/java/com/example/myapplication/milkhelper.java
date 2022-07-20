package com.example.myapplication;

public class milkhelper {
    String cow,day;
    Integer amount;

    public milkhelper() {
    }

    public milkhelper(String cow, String day, Integer amount) {
        this.cow = cow;
        this.day = day;
        this.amount = amount;
    }

    public String getCow() {
        return cow;
    }

    public void setCow(String cow) {
        this.cow = cow;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }



    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return
                "Date : " + day + "" +"\n" +
                "Cow ID = " + cow + "\n" +
                "Amount = " + amount +" Litres"+"\n" +
        "------------------------------------------------------------";
    }
}
