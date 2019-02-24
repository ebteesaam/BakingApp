package com.example.myapplication;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;

public abstract class BaseTest {
    protected BaseApplication baseApplication;
    protected IdlingResource idlingResource;

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void registerIdlingResource() {
        baseApplication = (BaseApplication) mainActivityActivityTestRule.getActivity().getApplicationContext();
        idlingResource = baseApplication.getIdlingResources();
        // Register Idling Resources
        IdlingRegistry.getInstance().register(idlingResource);
    }

    @After
    public void unregisterIdlingResource() {
        if (idlingResource != null) {
            IdlingRegistry.getInstance().unregister(idlingResource);
        }
    }
}
