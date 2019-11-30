package de.remmecke.android.habits.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class HabitRepository {

    private OccurrenceDao mOccurrenceDao;

    private LiveData<List<Occurrence>> mAllHabits;


    public HabitRepository(Application application){
        HabitRoomDatabase db = HabitRoomDatabase.getDatabase(application);
        mOccurrenceDao = db.occurenceDao();
        mAllHabits = mOccurrenceDao.getOccurrences();
    }


    public LiveData<List<Occurrence>> getmAllHabits(){
        return mAllHabits;
    }


    public void insertOccurrence(Occurrence occ){
        new insertAsyncOccurrence(mOccurrenceDao).execute(occ);
    }

    private static class insertAsyncOccurrence extends AsyncTask<Occurrence, Void, Void> {

        private OccurrenceDao mAsyncOccDao;

        public insertAsyncOccurrence(OccurrenceDao dao) {
            mAsyncOccDao = dao;
        }

        @Override
        protected Void doInBackground(final Occurrence... params) {
            Occurrence newOcc = params[0];
            mAsyncOccDao.insert(newOcc);
            return null;
        }
    }


    public List<Occurrence> getOccurences(){
        GetOccAsync getOccAsync = new GetOccAsync(mOccurrenceDao);
        List<Occurrence> result = null;
        try {
            result= getOccAsync.execute(121).get();
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
            return mAsyncTaskDao.findOccurences();
        }

        @Override
        protected void onPostExecute(List<Occurrence> s) {
            super.onPostExecute(s);
        }
    }

    public void updateOcc(Occurrence occ){
        UpdateOccAsync updateAsync = new UpdateOccAsync(mOccurrenceDao);
        updateAsync.execute(occ);

    }

    public static class UpdateOccAsync extends AsyncTask<Occurrence, Void, Void>{
        private OccurrenceDao mAsyncTaskDao;

        public UpdateOccAsync(OccurrenceDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Occurrence... occ) {
            int betroffene =  mAsyncTaskDao.updateOcc(occ);
            return null;
        }
    }

}