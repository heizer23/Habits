package de.remmecke.android.habits.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class HabitRepository {

    private HabitDao mHabitDao;
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


    public List<Occurrence> getOccurences(int habitId){
        GetOccAsync getOccAsync = new GetOccAsync(mOccurrenceDao);
        List<Occurrence> result = null;
        try {
            result= getOccAsync.execute(habitId).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static class GetOccAsync extends AsyncTask<Integer, Void, List<Occurrence>> {

        private OccurrenceDao mAsyncTaskDao;

        public GetOccAsync(OccurrenceDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected List<Occurrence> doInBackground(Integer... habitIds) {
            return mAsyncTaskDao.findOccurencesOfHabitId(habitIds[0]);
        }

        @Override
        protected void onPostExecute(List<Occurrence> s) {
            super.onPostExecute(s);
        }
    }


    public HabitWithInfo getHabitWithInfo(int habitId){
        GetHabitWithInfoAsync getHabitWithInfoAsync = new GetHabitWithInfoAsync(mHabitDao);
        HabitWithInfo result = null;
        try {
            result= getHabitWithInfoAsync.execute(habitId).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static class GetHabitWithInfoAsync extends AsyncTask<Integer, Void, HabitWithInfo> {

        private HabitDao mAsyncTaskDao;

        public GetHabitWithInfoAsync(HabitDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected HabitWithInfo doInBackground(Integer... habitIds) {
            return mAsyncTaskDao.getHabitWithInfo(habitIds[0]);
        }

        @Override
        protected void onPostExecute(HabitWithInfo habit) {
            super.onPostExecute(habit);
        }
    }

    public Habit getHabit(int habitId){
        GetHabitAsync getHabitAsync = new GetHabitAsync(mHabitDao);
        Habit result = null;
        try {
            result= getHabitAsync.execute(habitId).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static class GetHabitAsync extends AsyncTask<Integer, Void, Habit> {

        private HabitDao mAsyncTaskDao;

        public GetHabitAsync(HabitDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Habit doInBackground(Integer... habitIds) {
            return mAsyncTaskDao.getHabits(habitIds[0]);
        }

        @Override
        protected void onPostExecute(Habit habit) {
            super.onPostExecute(habit);
        }
    }

    public void updateHabit(Habit habit){
        new UpdateAsyncTask(mHabitDao).execute(habit);
    }

    private static class UpdateAsyncTask extends AsyncTask<Habit, Void, Void> {

        private HabitDao mAsyncTaskDao;

        public UpdateAsyncTask(HabitDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Habit... params) {
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }


}