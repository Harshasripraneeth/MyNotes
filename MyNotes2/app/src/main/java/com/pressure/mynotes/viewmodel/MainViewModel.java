package com.pressure.mynotes.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.pressure.mynotes.database.Database;
import com.pressure.mynotes.entities.Entity;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private LiveData<List<Entity>>list;
    public MainViewModel(@NonNull Application application) {
        super(application);


        Database database = Database.getInstance(this.getApplication());
        list = database.taskDao().loadAllTasks();

    }
    public LiveData<List<Entity>> getTasks()
    {
        return list;
    }
}
