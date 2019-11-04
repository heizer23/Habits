package de.remmecke.android.habits.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Date;

@Entity
public class PlannedDay {

    @PrimaryKey(autoGenerate = true)
    public Integer id;

    @NonNull
    private Date datum;

}
