package com.example.myapplication;

import android.support.annotation.Nullable;
import android.support.test.espresso.IdlingResource;

import java.util.concurrent.atomic.AtomicBoolean;

public class RecipeIdlingResources implements IdlingResource {

    @Nullable
    private volatile ResourceCallback callback;

    private AtomicBoolean isdleNow = new AtomicBoolean(true);

    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @Override
    public boolean isIdleNow() {
        return isdleNow.get();
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        this.callback = callback;
    }

    public void setIsdleNow(boolean idleNow){
        isdleNow.set(idleNow);
        if (idleNow && callback != null) {
            callback.onTransitionToIdle();
        }
    }
}
