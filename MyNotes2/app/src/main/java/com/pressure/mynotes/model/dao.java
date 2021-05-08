package com.pressure.mynotes.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface dao {


    @Query("SELECT * FROM MyNotes")
    LiveData<List<Entity>> loadAllTasks();


    @Insert
    void insertTask(Entity taskEntry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateTask(Entity taskEntry);

    @Delete
    void deleteTask(Entity taskEntry);

    @Query("SELECT * FROM MyNotes WHERE id LIKE :id")
    Entity loadById(int id);
    @Query("SELECT * FROM mynotes WHERE title LIKE :text OR content LIKE :text")
    List<Entity> loadUpdatedList(String text);

    @Query("DELETE FROM mynotes")
     void deleteAllNotes();
   }
