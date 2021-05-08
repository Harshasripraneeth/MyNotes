package com.pressure.mynotes.dependencyinjection;

import com.pressure.mynotes.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class,DatabaseModule.class,RepositoryModule.class})
public interface AppComponent {

    void inject(MainActivity mainActivity);
}
