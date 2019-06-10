package com.example.hackermail;


import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;


@Database(entities = {Clock.class}, version = 2, exportSchema = false)
public abstract class ClockRoomDatabase extends RoomDatabase {

    public abstract ClockDao clockDao();

    private static ClockRoomDatabase INSTANCE;

    public static ClockRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ClockRoomDatabase.class) {
                if (INSTANCE == null) {
                    // Create database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ClockRoomDatabase.class, "clock_database")
                            // Wipes and rebuilds instead of migrating if no Migration object.
                            // Migration is not part of this practical.
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();

                }
            }
        }
        return INSTANCE;
    }


    // This callback is called when the database has opened.
    // In this case, use PopulateDbAsync to populate the database
    // with the initial data set if the database has no entries.
    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback() {

                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };


    // Populate the database with the initial data set
    // only if the database has no entries.
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final ClockDao mDao;

        // Initial data set
        private static String [][] clocks = {{"xxxx@xx.com", "yyyyyyy@yy.com"}, {"11.11", "12.12"},{"xxxxxxxx","yyyyyyyy"}};


        PopulateDbAsync(ClockRoomDatabase db) { mDao = db.clockDao(); }

        @Override
        protected Void doInBackground(final Void... params) {
            // If we have no words, then create the initial list of words
            if (mDao.getAnyclock().length < 1) {



                for (int i = 0; i <= clocks.length - 1; i++) {
                    Clock clock = new Clock();
                    clock.setToemail(clocks[0][i]);
                    clock.setTime(clocks[1][i]);
                    clock.setContent(clocks[2][i]);
                    mDao.insert(clock);

                }
            }
            return null;
        }
    }

    public static void deleteInstance() {
        INSTANCE = null;
    }

}
