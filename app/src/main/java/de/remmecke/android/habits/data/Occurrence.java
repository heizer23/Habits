package de.remmecke.android.habits.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import java.util.Date;

@Entity(foreignKeys = {
        @ForeignKey(entity = Habit.class,
                parentColumns = "id",
                childColumns = "habit_id")})
@TypeConverters(DateConverter.class)
public class Occurrence {

    @PrimaryKey(autoGenerate = true)
    public Integer id;

    @ColumnInfo(name="habit_id")
    public Integer habitId;

    public Date timeStamp;

    @ColumnInfo(name="target_time")
    public Date targetTime;

    public Integer success;

    public Occurrence(Integer habitId) {
        this.habitId = habitId;
        timeStamp = new Date();
        targetTime = new Date();
        success = 1;
    }


}