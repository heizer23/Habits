package de.remmecke.android.habits.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface OccurrenceDao {

    @Insert
    void insert(Occurrence occurrence);

    @Update
    int updateOcc(Occurrence... occs);

    @Query("SELECT * " +
            "FROM Occurrence " +
            "order by id asc"
    )
    List<Occurrence> findOccurences();

    @Query("SELECT name from Occurrence")
    LiveData<List<Occurrence>> getOccurrences();

    @Query("UPDATE Occurrence set name = :name where id = :id")
    void editName(int id, String name);

    @Query("DELETE FROM Occurrence")
    void deleteAll();
}
