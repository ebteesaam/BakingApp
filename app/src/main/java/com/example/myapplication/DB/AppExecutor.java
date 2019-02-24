package com.example.myapplication.DB;

import android.support.annotation.NonNull;

import java.util.concurrent.Executor;

/**
 * Created by ebtesam on 24/12/2018 AD.
 */

public class AppExecutor implements Executor {
    @Override
    public void execute(@NonNull Runnable runnable) {
        new Thread(runnable).start();
    }
}