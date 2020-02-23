package com.pressure.mynotes.entities;


import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@androidx.room.Entity(tableName = "MyNotes")
public class Entity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String content;

    public Entity(int id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }
    @Ignore
    public Entity( String title, String content) {
        this.title = title;
        this.content = content;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }



}
