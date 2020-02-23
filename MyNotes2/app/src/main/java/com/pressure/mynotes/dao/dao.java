package com.pressure.mynotes.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.pressure.mynotes.entities.Entity;

import java.util.List;

@Dao
public interface dao {


    @Query("SELECT * FROM MyNotes")
    LiveData<List<Entity>> loadAllTasks();
    @Insert
    void insertTask(com.pressure.mynotes.entities.Entity taskEntry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateTask(com.pressure.mynotes.entities.Entity taskEntry);

    @Delete
    void deleteTask(com.pressure.mynotes.entities.Entity taskEntry);

    @Query("SELECT * FROM MyNotes WHERE id LIKE :id")
    Entity loadById(int id);
    @Query("SELECT * FROM mynotes WHERE title LIKE :text OR content LIKE :text")
    List<Entity> loadUpdatedList(String text);

    @Query("DELETE FROM mynotes")
     void deleteAllNotes();
   }
