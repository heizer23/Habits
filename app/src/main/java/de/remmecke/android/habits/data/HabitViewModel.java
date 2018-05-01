package de.remmecke.android.habits.data;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class HabitViewModel extends AndroidViewModel {

    private HabitRepository mRepository;
    private LiveData<List<HabitWithInfo>> mAllHabits;

    public List<Occurrence> occurenceNr;

    public HabitViewModel(@NonNull Application application) {
        super(application);
        mRepository = new HabitRepository(application);
        mAllHabits = mRepository.getmAllHabits();
        occurenceNr = mRepository.getOccurences("Hello");
    }

    public LiveData<List<HabitWithInfo>> getmAllHabits() {return mAllHabits;}

    public void insertHabit(Habit habit){mRepository.insertHabit(habit); }

    public void setOccurenceNr(){
        occurenceNr = mRepository.getOccurences("Hello");
    }

    public void insertOccurrence(HabitWithInfo habit){
        mRepository.insertOccurrence(habit);
    }

}