package de.remmecke.android.habits.data;

import android.app.Application;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;


@Database(entities = {Habit.class, Occurrence.class}, version = 2)
public abstract class HabitRoomDatabase extends RoomDatabase {

    public abstract HabitDao habitDao();
    public abstract OccurrenceDao occurenceDao();

    private Context context;
    private static HabitRoomDatabase INSTANCE;

    public static HabitRoomDatabase getDatabase(final Context context){

        if (INSTANCE == null){
            synchronized (HabitRoomDatabase.class){
                if(INSTANCE == null){
                    String dbName = "Habit_database.sqlite";
                    // Create database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            HabitRoomDatabase.class, dbName)
                            .addCallback(sRoomDataBaseCallback)
                            .addMigrations(MIGRATION_1_2)
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


    static final Migration MIGRATION_1_2 = new Migration(1, 2)  {
        @Override
        public void migrate(SupportSQLiteDatabase INSTANCE) {

            INSTANCE.execSQL("ALTER TABLE Occurrence "
                    + " ADD COLUMN success INTEGER");

            INSTANCE.execSQL("ALTER TABLE Occurrence  "
                    + " ADD COLUMN target_time INTEGER");
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
