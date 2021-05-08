package com.pressure.mynotes.model;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@androidx.room.Database(entities = {Entity.class}, version = 1, exportSchema = false)
public abstract class Database extends RoomDatabase {


    private static final String LOG_TAG = Database.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "listnotes";
    private static Database sInstance;
    public static Database getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                Log.d(LOG_TAG, "Creating new database instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        Database.class, Database.DATABASE_NAME)
                        .build();
            }
        }
        Log.d(LOG_TAG, "Getting the database instance");
        return sInstance;
    }

    public abstract dao taskDao();
}
