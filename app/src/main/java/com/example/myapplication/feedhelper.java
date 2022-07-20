package com.example.myapplication;

public class feedhelper {
    String feedsid,feedsname,amount,username;

    public feedhelper() {

    }

    public feedhelper(String feedsid, String feedsname, String amount, String username) {
        this.feedsid = feedsid;
        this.feedsname = feedsname;
        this.amount = amount;
        this.username = username;
    }

    public String getFeedsid() {
        return feedsid;
    }

    public void setFeedsid(String feedsid) {
        this.feedsid = feedsid;
    }

    public String getFeedsname() {
        return feedsname;
    }

    public void setFeedsname(String feedsname) {
        this.feedsname = feedsname;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return

                "id = " + feedsid + "\n" +
                "Name = " + feedsname + "\n" +
                "Amount = " + amount + "\n"+"\n"+
                "------------------------------------------------------------"
                ;
    }
}
