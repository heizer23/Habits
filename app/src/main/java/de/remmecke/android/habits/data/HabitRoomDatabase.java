package de.remmecke.android.habits.data;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = {Habit.class, Occurrence.class}, version = 1)
public abstract class HabitRoomDatabase extends RoomDatabase {

    public abstract HabitDao habitDao();
    public abstract OccurrenceDao occurenceDao();

    private static HabitRoomDatabase INSTANCE;

    public static HabitRoomDatabase getDatabase(final Context context){
        if (INSTANCE == null){
            synchronized (HabitRoomDatabase.class){
                if(INSTANCE == null){
                    // Create database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            HabitRoomDatabase.class, "Habit_database")
                            .addCallback(sRoomDataBaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDataBaseCallback=
            new RoomDatabase.Callback(){

                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                   // new PopulateDbAsync(INSTANCE).execute();
                }
            };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
        private final HabitDao mDao;
        private final OccurrenceDao mOccurrenceDao;


        PopulateDbAsync(HabitRoomDatabase db){
            mDao = db.habitDao();
            mOccurrenceDao = db.occurenceDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mOccurrenceDao.deleteAll();
            mDao.deleteAll();


            Habit habit = new Habit("Hello");
            mDao.insert(habit);

            habit = new Habit("World");
            mDao.insert(habit);
            return null;
        }
    }
}
