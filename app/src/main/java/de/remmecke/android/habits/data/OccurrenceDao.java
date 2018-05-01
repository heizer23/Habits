package de.remmecke.android.habits.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface OccurrenceDao {

    @Insert
    void insert(Occurrence occurrence);


    @Query("SELECT * " +
            "FROM Occurrence " +
            "INNER JOIN Habit ON Occurrence.habit_id = Habit.id " +
            "WHERE Habit.name LIKE :habitName "
    )
    List<Occurrence> findOccurencesOfHabit(String habitName);

    @Query("SELECT habit_id from Occurrence")
    List<Occurrence> getOccurrences();

    @Query("DELETE FROM Occurrence")
    void deleteAll();
}
