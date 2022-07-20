package com.example.myapplication;

public class treatmenthelper {
    String cow,day,medication,amount;

    public treatmenthelper() {
    }

    public treatmenthelper(String cow, String day, String medication, String amount) {
        this.cow = cow;
        this.day = day;
        this.medication = medication;
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

    public String getMedication() {
        return medication;
    }

    public void setMedication(String medication) {
        this.medication = medication;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }



    @Override
    public String toString() {
        return

                "Cow= " + cow + "\n" +
                "Day= " + day + "\n" +
                "Medication= " + medication + '\n' +
                "Amount= " + amount+"\n"+
                "------------------------------------------------------------"

                ;
    }
}
