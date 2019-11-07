package de.remmecke.android.habits.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import java.util.Date;

@Entity()
@TypeConverters(DateConverter.class)
public class Occurrence {

    @PrimaryKey(autoGenerate = true)
    public Integer id;

    private String name;

    private Date timeStamp;

    @ColumnInfo(name="target_time")
    private Date targetTime;

    private Integer success;

    public Occurrence(String name) {
        this.name = name;
        timeStamp = new Date();
        this.targetTime = new Date();
        success = 0;
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