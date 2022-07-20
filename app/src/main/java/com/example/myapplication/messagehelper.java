package com.example.myapplication;

public class messagehelper {
    String message,user;

    public messagehelper() {
    }

    public messagehelper(String message, String user) {
        this.message = message;
        this.user = user;

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return
                  user + '\n' +
                  message + '\n'

                ;
    }
}
