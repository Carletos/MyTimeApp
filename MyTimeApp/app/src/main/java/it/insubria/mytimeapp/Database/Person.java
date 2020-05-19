package it.insubria.mytimeapp.Database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;


import java.sql.Date;
import java.sql.Time;


@Entity(tableName = "person")
public class Person {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int pid;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "stuff")
    private String stuff;

    @ColumnInfo(name = "date")
    private Date date;

    @ColumnInfo(name = "time_from")
    private String timeFrom;

    @ColumnInfo(name = "time_to")
    private String timeTo;


    public Person(@NonNull int pid, String name, String stuff, Date date, String timeFrom, String timeTo) {
        this.pid = pid;
        this.name = name;
        this.stuff = stuff;
        this.date = date;
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
    }

    @Ignore
    public Person(String person) {
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

    /*  @Override
    public String toString(){
        return "Person name = " + name + "\n"
                + "stuff = " + stuff + "\n"
                + "data = " + date + "\n"
                + "ora inizio" + ora_inizio + "\n"
                + "ora fine" + ora_fine + "\n";
    }*/

}

