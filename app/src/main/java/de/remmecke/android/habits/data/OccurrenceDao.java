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
            "WHERE habit_id = :habitId " +
            "order by id asc"
    )
    List<Occurrence> findOccurencesOfHabitId(int habitId);

    @Query("SELECT habit_id from Occurrence")
    List<Occurrence> getOccurrences();

    @Query("DELETE FROM Occurrence")
    void deleteAll();
}
