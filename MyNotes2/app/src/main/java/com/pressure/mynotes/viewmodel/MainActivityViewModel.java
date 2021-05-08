package com.pressure.mynotes.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.pressure.mynotes.MainActivity;
import com.pressure.mynotes.methods.Executors;
import com.pressure.mynotes.model.Database;
import com.pressure.mynotes.model.Entity;
import com.pressure.mynotes.model.Repository;

import java.util.List;
import java.util.TreeSet;

public class MainActivityViewModel extends ViewModel {

    private Repository repository;
    private LiveData<List<Entity>> notes;


    public MainActivityViewModel(Repository repository) {
        this.repository = repository;
    }

    public LiveData<List<Entity>> getNotes() {
        notes = repository.getList().getValue();
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


}
