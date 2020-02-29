package com.pressure.mynotes.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.pressure.mynotes.MainActivity;
import com.pressure.mynotes.methods.Executors;
import com.pressure.mynotes.model.Database;
import com.pressure.mynotes.model.Entity;
import com.pressure.mynotes.model.Repository;

import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {

    private Repository repository;
    private LiveData<List<Entity>> notes;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
    }

    public LiveData<List<Entity>> getNotes() {
        notes = repository.getList();
        return notes;
    }

    public void insert(Entity note) {
        repository.insert(note);
    }

    public void delete(Entity note) {
        repository.delete(note);
    }
    public void deleteAllNotes()
    {
        repository.deleteAllNotes();
    }

    public void update(Entity note) {
        repository.update(note);
    }

    public List<Entity> getSearchList(String s) {
        List<Entity> list = repository.getSearchList(s);
        return list;
    }
}
