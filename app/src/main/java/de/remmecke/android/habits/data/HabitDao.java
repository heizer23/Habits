package de.remmecke.android.habits.data;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface HabitDao {

    @Insert
    void insert(Habit habit);

    @Query("DELETE FROM habit")
    void deleteAll();

    @Query("SELECT * from habit ORDER BY name ASC")
    LiveData<List<Habit>> getAllHabits();

    @Query("SELECT habit.id as habitId, habit.name, 5 as count from habit ORDER BY name DESC")
    LiveData<List<HabitWithInfo>> getAllHabitsWithInfo2();

    @Query("SELECT habit.id as habitId, habit.name as name, COUNT(Occurrence.habit_id) as count, " +
            "max(Occurrence.timeStamp) as lastTime " +
            "FROM Habit " +
            "LEFT JOIN Occurrence ON Occurrence.habit_id = Habit.id " +
            " group by habit.id"
    )
    LiveData<List<HabitWithInfo>> getAllHabitsWithInfo();

}