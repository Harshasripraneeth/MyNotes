package com.pressure.mynotes.methods;

import android.app.Application;

import com.pressure.mynotes.dependencyinjection.AppComponent;
import com.pressure.mynotes.dependencyinjection.ApplicationModule;
import com.pressure.mynotes.dependencyinjection.DaggerAppComponent;
import com.pressure.mynotes.dependencyinjection.RepositoryModule;

public class AppApplication extends Application {
    private static AppApplication appApplication;
    private AppComponent appComponent;

    public AppComponent getAppComponent() {
        return appComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appApplication = this;
        appComponent = DaggerAppComponent.builder()
        .applicationModule(new ApplicationModule(this))
                .build();
    }

    public static AppApplication getAppApplication() {
        return appApplication;
    }

}
