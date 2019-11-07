package de.remmecke.android.habits;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import de.remmecke.android.habits.data.Occurrence;
import de.remmecke.android.habits.data.HabitRepository;

public class HabitViewModel extends AndroidViewModel {

    private HabitRepository mRepository;
    private LiveData<List<Occurrence>> mAllHabits;


    public HabitViewModel(@NonNull Application application) {
        super(application);
        mRepository = new HabitRepository(application);
        mAllHabits = mRepository.getmAllHabits();
    }

    public LiveData<List<Occurrence>> getmAllHabits() {return mAllHabits;}

    public void editOcc(Occurrence occ, String newName){
        occ.setName(newName);
        String test  = occ.getName();
        mRepository.updateOcc(occ);
    }


}