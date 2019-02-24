package com.example.myapplication.DB.DBR;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.example.myapplication.Model.BakingRecipe;

import java.util.List;

/**
 * Created by ebtesam on 24/12/2018 AD.
 */

public class MainViewModel extends AndroidViewModel {

    // Constant for logging
    private static final String TAG = MainViewModel.class.getSimpleName();

    // COMPLETED (2) Add a tasks member variable for a list of TaskEntry objects wrapped in a LiveData
    private LiveData<List<BakingRecipe>> tasks;

    public MainViewModel(Application application) {
        super(application);
        // COMPLETED (4) In the constructor use the loadAllTasks of the taskDao to initialize the tasks variable

        AppDatabase database = AppDatabase.getDatabase(this.getApplication());
        Log.d(TAG, "Actively retrieving the movie from the DataBase");
        tasks = database.RecipeDao().getAll();
    }

    // COMPLETED (3) Create a getter for the tasks variable
    public LiveData<List<BakingRecipe>> getTasks() {
        return tasks;
    }
}
