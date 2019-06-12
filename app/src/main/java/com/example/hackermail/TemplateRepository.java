package com.example.hackermail;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class TemplateRepository {

        private TemplateDao mTemplateDao;
        private LiveData<List<Template>>mAllTemplates;

        TemplateRepository(Application application) {
        TemplateRoomDatabase db = TemplateRoomDatabase.getDatabase(application);
        mTemplateDao = db.TemplateDao();
        mAllTemplates = mTemplateDao.getAllTemplates();
    }

    LiveData<List<Template>> getmAllTemplates() {
        return mAllTemplates;
    }

    public void insert(Template Template) {
        new insertAsyncTask(mTemplateDao).execute(Template);
    }

    public void deleteAll() {
        new deleteAllTemplatesAsyncTask(mTemplateDao).execute();
    }

    // Need to run off main thread
    public void deleteTemplate(Template Template) {
        new deleteTemplateAsyncTask(mTemplateDao).execute(Template);
    }

    public void updateTemplate(Template Template) {
        new updateAsyncTask(mTemplateDao).execute(Template);
    }

    // Static inner classes below here to run database interactions
    // in the background.


    /**
     * Insert a Template into the database.
     */
    private static class insertAsyncTask extends AsyncTask<Template, Void, Void> {

        private TemplateDao mAsyncTaskDao;

        insertAsyncTask(TemplateDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Template... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    /**
     * Delete all words from the database (does not delete the table)
     */
    private static class deleteAllTemplatesAsyncTask extends AsyncTask<Void, Void, Void> {
        private TemplateDao mAsyncTaskDao;

        deleteAllTemplatesAsyncTask(TemplateDao dao) {
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
    private static class deleteTemplateAsyncTask extends AsyncTask<Template, Void, Void> {
        private TemplateDao mAsyncTaskDao;


        deleteTemplateAsyncTask(TemplateDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Template... params) {
            mAsyncTaskDao.deleteTemplate(params[0]);
            return null;
        }
    }

    /**
     * Insert a word into the database.
     */
    private static class updateAsyncTask extends AsyncTask<Template, Void, Void> {

        private TemplateDao mAsyncTaskDao;

        updateAsyncTask(TemplateDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Template... params) {
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }
}
