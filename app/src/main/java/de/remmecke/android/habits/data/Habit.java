package de.remmecke.android.habits.data;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class Habit {

    @PrimaryKey(autoGenerate = true)
    public Integer id;

    @NonNull
    private String name;

    public Habit(@NonNull String name){
        this.name =name;
    }

    public String getName(){
        return name;
    }

    public Integer getId() {
        return id;
    }
}