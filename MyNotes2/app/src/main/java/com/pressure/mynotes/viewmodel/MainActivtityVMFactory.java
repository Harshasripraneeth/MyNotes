package com.pressure.mynotes.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.pressure.mynotes.model.Repository;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MainActivtityVMFactory implements ViewModelProvider.Factory {
    private  final Repository repository;

    @Inject
    public MainActivtityVMFactory(Repository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MainActivityViewModel(repository);
    }
}
