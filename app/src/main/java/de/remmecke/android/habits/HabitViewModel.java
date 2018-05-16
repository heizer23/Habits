package de.remmecke.android.habits;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import de.remmecke.android.habits.data.HabitRepository;
import de.remmecke.android.habits.data.HabitWithInfo;

public class HabitViewModel extends AndroidViewModel {

    private HabitRepository mRepository;
    private LiveData<List<HabitWithInfo>> mAllHabits;


    public HabitViewModel(@NonNull Application application) {
        super(application);
        mRepository = new HabitRepository(application);
        mAllHabits = mRepository.getmAllHabits();
    }

    public LiveData<List<HabitWithInfo>> getmAllHabits() {return mAllHabits;}

    public void insertOccurrence(HabitWithInfo habit){
        mRepository.insertOccurrence(habit);
    }


}