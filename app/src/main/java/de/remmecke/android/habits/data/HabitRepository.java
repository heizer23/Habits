package de.remmecke.android.habits.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class HabitRepository {

    private  HabitDao mHabitDao;
    private OccurrenceDao mOccurrenceDao;

    private LiveData<List<HabitWithInfo>> mAllHabits;


    public HabitRepository(Application application){
        HabitRoomDatabase db = HabitRoomDatabase.getDatabase(application);
        mHabitDao = db.habitDao();
        mOccurrenceDao = db.occurenceDao();
        mAllHabits = mHabitDao.getAllHabitsWithInfo();
    }

    public LiveData<List<HabitWithInfo>> getmAllHabits(){
        return mAllHabits;
    }

    public void insertHabit(Habit habit){
        new insertAsyncTask(mHabitDao).execute(habit);
    }

    private static class insertAsyncTask extends AsyncTask<Habit, Void, Void> {

        private HabitDao mAsyncTaskDao;

        public insertAsyncTask(HabitDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Habit... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    public void insertOccurrence(HabitWithInfo habit){
        new insertAsyncOccurrence(mOccurrenceDao).execute(habit);
    }

    private static class insertAsyncOccurrence extends AsyncTask<HabitWithInfo, Void, Void> {

        private OccurrenceDao mAsyncOccDao;

        public insertAsyncOccurrence(OccurrenceDao dao) {
            mAsyncOccDao = dao;
        }

        @Override
        protected Void doInBackground(final HabitWithInfo... params) {
            Occurrence newOcc = new Occurrence(params[0].getHabitId());
            mAsyncOccDao.insert(newOcc);
            return null;
        }
    }


    //TODO Überflüssige  Methoden löschen
    public List<Occurrence> getOccurences(String habitName){
        GetOccAsync getOccAsync = new GetOccAsync(mOccurrenceDao);
        List<Occurrence> result = null;
        try {
            result= getOccAsync.execute(habitName).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static class GetOccAsync extends AsyncTask<String, Void, List<Occurrence>> {

        private OccurrenceDao mAsyncTaskDao;

        public GetOccAsync(OccurrenceDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected List<Occurrence> doInBackground(String... habitNames) {
            return mAsyncTaskDao.findOccurencesOfHabit(habitNames[0]);
        }

        @Override
        protected void onPostExecute(List<Occurrence> s) {
            super.onPostExecute(s);
        }
    }
}