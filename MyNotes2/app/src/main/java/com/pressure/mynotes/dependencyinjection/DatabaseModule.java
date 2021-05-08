package com.pressure.mynotes.dependencyinjection;

import android.app.Application;

import androidx.room.Room;

import com.pressure.mynotes.model.Database;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseModule {

    @Provides
    @Singleton
    Database providesDatabase(Application application)
    {

        return  Room.databaseBuilder(application.getApplicationContext(),
                Database.class, "listnotes")
                .build();
    }
}
