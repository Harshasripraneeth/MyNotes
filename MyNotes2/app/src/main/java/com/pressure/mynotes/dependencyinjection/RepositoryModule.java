package com.pressure.mynotes.dependencyinjection;

import android.app.Application;

import com.pressure.mynotes.model.Repository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {


    @Provides
    @Singleton
    Repository providesRepository(Application application)
    {
        return new Repository(application);
    }
}
