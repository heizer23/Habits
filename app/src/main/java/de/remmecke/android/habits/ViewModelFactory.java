package de.remmecke.android.habits;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private Application mApplication;
    private int mHabitId;


    public ViewModelFactory(Application application, int habitId) {
        mApplication = application;
        mHabitId = habitId;
    }


   // @Override
   // public <T extends ViewModel> T create(Class<T> modelClass) {
     //   return (T) new HabitInfoViewModel(mApplication, mHabitId);
  //  }
}