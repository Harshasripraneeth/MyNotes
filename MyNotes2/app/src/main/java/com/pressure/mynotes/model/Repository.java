package com.pressure.mynotes.model;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.pressure.mynotes.methods.Adapter;
import com.pressure.mynotes.methods.Executors;

import java.util.ArrayList;
import java.util.List;

public class Repository {

    private dao notedao;
    private LiveData<List<Entity>> list;
    private List<Entity> updatedlist = new ArrayList<Entity>();
    private Database database;
    private Context context;
    private Adapter adapter;

    public Repository(Application application)
    {
        context = application;
        database = Database.getInstance(context);
        notedao = database.taskDao();

    }

    public LiveData<List<Entity>> getList() {
        return notedao.loadAllTasks();
    }

    public void setList(LiveData<List<Entity>> list) {
        this.list = list;
    }
    public void insert(Entity note)
    {
        final Entity entity = note;
        Executors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                notedao.insertTask(entity);
            }
        });

    }
    public void delete(Entity entity)
    {
        final Entity note = entity;
        Executors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                notedao.deleteTask(note);
            }
        });

    }
    public void update(Entity note)
    {
        final Entity entity = note;
        Executors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                notedao.updateTask(entity);
            }
        });

    }
    public List<Entity> getSearchList(String s)
    {

       final String search = "%"+s+"%";
        Executors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                updatedlist =database.taskDao().loadUpdatedList(search);
                Log.d("repository","loaded list");
            }
        });

        return updatedlist;
    }
    public void deleteAllNotes()
    {
        Executors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                database.taskDao().deleteAllNotes();
            }
        });
    }

}
