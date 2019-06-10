package com.example.hackermail;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class ClockRepository{

        private ClockDao mClockDao;
        private LiveData<List<Clock>>mAllClocks;

        ClockRepository(Application application) {
        ClockRoomDatabase db = ClockRoomDatabase.getDatabase(application);
        mClockDao = db.clockDao();
        mAllClocks = mClockDao.getAllclocks();
    }

    LiveData<List<Clock>> getmAllClocks() {
        return mAllClocks;
    }

    public void insert(Clock clock) {
        new insertAsyncTask(mClockDao).execute(clock);
    }

    public void deleteAll() {
        new deleteAllClocksAsyncTask(mClockDao).execute();
    }

    // Need to run off main thread
    public void deleteClock(Clock clock) {
        new deleteClockAsyncTask(mClockDao).execute(clock);
    }

    public void updateClock(Clock clock) {
        new updateAsyncTask(mClockDao).execute(clock);
    }

    // Static inner classes below here to run database interactions
    // in the background.


    /**
     * Insert a clock into the database.
     */
    private static class insertAsyncTask extends AsyncTask<Clock, Void, Void> {

        private ClockDao mAsyncTaskDao;

        insertAsyncTask(ClockDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Clock... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    /**
     * Delete all words from the database (does not delete the table)
     */
    private static class deleteAllClocksAsyncTask extends AsyncTask<Void, Void, Void> {
        private ClockDao mAsyncTaskDao;

        deleteAllClocksAsyncTask(ClockDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }

    /**
     *  Delete a single word from the database.
     */
    private static class deleteClockAsyncTask extends AsyncTask<Clock, Void, Void> {
        private ClockDao mAsyncTaskDao;


        deleteClockAsyncTask(ClockDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Clock... params) {
            mAsyncTaskDao.deleteClock(params[0]);
            return null;
        }
    }

    /**
     * Insert a word into the database.
     */
    private static class updateAsyncTask extends AsyncTask<Clock, Void, Void> {

        private ClockDao mAsyncTaskDao;

        updateAsyncTask(ClockDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Clock... params) {
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }
}
