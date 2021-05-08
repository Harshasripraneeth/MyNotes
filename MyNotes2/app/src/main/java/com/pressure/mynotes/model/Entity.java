package com.pressure.mynotes.model;


import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.pressure.mynotes.BR;

@androidx.room.Entity(tableName = "MyNotes")
public class Entity extends BaseObservable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String content;
    private String date;

    public Entity(int id, String title, String content,String date) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.date = date;
    }
    @Ignore
    public Entity( String title, String content,String date) {
        this.title = title;
        this.content = content;
        this.date = date;
    }
     @Bindable
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        notifyPropertyChanged(com.pressure.mynotes.BR.id);
    }

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(com.pressure.mynotes.BR.title);
    }

    @Bindable
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
        notifyPropertyChanged(com.pressure.mynotes.BR.content);
    }

    @Bindable
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
        notifyPropertyChanged(com.pressure.mynotes.BR.date);
    }
}
