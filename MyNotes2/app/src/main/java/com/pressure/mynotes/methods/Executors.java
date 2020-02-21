package com.pressure.mynotes.methods;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import java.util.concurrent.Executor;

public class Executors {


    // For Singleton instantiation
    private static final Object LOCK = new Object();
    private static Executors sInstance;
    private final Executor diskIO;
    private final Executor mainThread;
    private final Executor networkIO;


    public Executors(Executor diskIO, Executor mainThread, Executor networkIO) {
        this.diskIO = diskIO;
        this.mainThread = mainThread;
        this.networkIO = networkIO;
    }

    public static Executors getInstance() {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new Executors(java.util.concurrent.Executors.newSingleThreadExecutor(),
                        java.util.concurrent.Executors.newFixedThreadPool(3),
                        new MainThreadExecutor());
            }
        }
        return sInstance;
    }
    public Executor diskIO() {
        return diskIO;
    }

    public Executor mainThread() {
        return mainThread;
    }

    public Executor networkIO() {
        return networkIO;
    }

    private static class MainThreadExecutor implements Executor {
        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable command) {
            mainThreadHandler.post(command);
        }
    }

}
