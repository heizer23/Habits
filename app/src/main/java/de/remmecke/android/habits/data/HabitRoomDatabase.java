package de.remmecke.android.habits.data;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;


@Database(entities = {Occurrence.class}, version = 4)
public abstract class HabitRoomDatabase extends RoomDatabase {

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
                            .fallbackToDestructiveMigration()
                         //   .addMigrations(MIGRATION_1_2)
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
        private final OccurrenceDao mOccurrenceDao;


        PopulateDbAsync(HabitRoomDatabase db){
            mOccurrenceDao = db.occurenceDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mOccurrenceDao.deleteAll();
//            mDao.deleteAll();
//
//
//            Occurrence habit = new Occurrence("Hello");
//            mDao.insert(habit);
//
//            habit = new Occurrence("World");
//            mDao.insert(habit);
            return null;
        }
    }

}
