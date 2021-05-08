package com.pressure.mynotes.model;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.pressure.mynotes.methods.Executors;

import java.util.ArrayList;
import java.util.List;

public class Repository {

    private dao notedao;
   private MutableLiveData<LiveData<List<Entity>>> list;
    private List<Entity> updatedlist = new ArrayList<Entity>();
    private Database database;
    private Context context;

    public Repository(Application application)
    {
        context = application;
        database = Database.getInstance(context);
        notedao = database.taskDao();
        list = new MutableLiveData<>();

    }

    public MutableLiveData<LiveData<List<Entity>>> getList()
    {
       list.setValue(notedao.loadAllTasks());
       return list;

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
