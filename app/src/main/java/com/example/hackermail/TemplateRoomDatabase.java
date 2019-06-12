package com.example.hackermail;


import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;


@Database(entities = {Template.class}, version = 2, exportSchema = false)
public abstract class TemplateRoomDatabase extends RoomDatabase {

    public abstract TemplateDao TemplateDao();

    private static TemplateRoomDatabase INSTANCE;

    public static TemplateRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TemplateRoomDatabase.class) {
                if (INSTANCE == null) {
                    // Create database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TemplateRoomDatabase.class, "Template_database")
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
    private static Callback sRoomDatabaseCallback =
            new Callback() {

                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };


    // Populate the database with the initial data set
    // only if the database has no entries.
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final TemplateDao mDao;

        // Initial data set
        private static String [] Templates = { "要吃饭",  "要开会", "头痛", "作业写不完"};


        PopulateDbAsync(TemplateRoomDatabase db) { mDao = db.TemplateDao(); }

        @Override
        protected Void doInBackground(final Void... params) {
            // If we have no words, then create the initial list of words
            if (mDao.getAnyTemplate().length < 1) {
                for (int i = 0; i <= Templates.length - 1; i++) {
                    Template Template = new Template();
                    Template.setContent(Templates[i]);
                    mDao.insert(Template);

                }
            }
            return null;
        }
    }

    public static void deleteInstance() {
        INSTANCE = null;
    }

}
