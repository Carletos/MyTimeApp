package it.insubria.mytimeapp.Database;

import androidx.annotation.NonNull;


import java.util.Date;

public class Person {

    private int pid;

    private String name;

    private String stuff;

    private java.util.Date date;

    private String timeFrom;


    private String timeTo;


    public Person(int pid, String name, String stuff, Date date, String timeFrom, String timeTo) {
        this.pid = pid;
        this.name = name;
        this.stuff = stuff;
        this.date = date;
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
    }


    @NonNull
    public int getPid() {
        return this.pid;
    }

    @NonNull
    public String getName() {
        return this.name;
    }

    @NonNull
    public String getStuff() {
        return this.stuff;
    }

    @NonNull
    public Date getDate() {
        return this.date;
    }

    @NonNull
    public String getTimeFrom() {
        return timeFrom;
    }

    @NonNull
    public String getTimeTo() {
        return timeTo;
    }
}
