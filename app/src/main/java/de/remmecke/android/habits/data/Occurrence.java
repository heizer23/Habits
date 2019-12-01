package de.remmecke.android.habits.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import java.util.Date;

@Entity
@TypeConverters(DateConverter.class)
public class Occurrence {

    @PrimaryKey(autoGenerate = true)
    public Integer id;

    public String name;

    public Date timeStamp;

    @ColumnInfo(name="target_time")
    public Date targetTime;

    public Integer success;

    @Ignore
    public Occurrence(String name) {
        this.name = name;
        timeStamp = new Date();
        this.targetTime = new Date();
        success = 0;
    }

    public Occurrence(int id, String name, Date timeStamp, Date targetTime, int success) {
        this.id = id;
        this.name = name;
        this.timeStamp = timeStamp;
        this.targetTime = targetTime;
        this.success = success;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Date getTargetTime() {
        return targetTime;
    }

    public void setTargetTime(Date targetTime) {
        this.targetTime = targetTime;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }
}