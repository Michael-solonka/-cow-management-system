package com.example.myapplication;

public class announcehelper {
    String level,subject,description,user;

    public announcehelper() {
    }

    public announcehelper(String level, String subject, String description,String user) {
        this.level = level;
        this.subject = subject;
        this.description = description;
        this.user=user;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
                user + "\n" +
                "IMPORTANCE : " + level + "\n" +
                "SUBJECT : " + subject + "\n" +"\n"+
                " BODY :" + "\n" + description + "\n" +
                        "------------------------------------------------------------"

                ;
    }
}


