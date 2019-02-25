package com.example.myapplication;


import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class RecipeListActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    public static void getMeToRecipeInfo(int recipePosition) {
        onView(withId(R.id.list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(recipePosition, click()));
    }
    @Test
    public void clickOnRecyclerViewStepItem_opensRecipeStepActivity_orFragment() {

//        onData(anything()).inAdapterView(withId(R.id.list)).atPosition(0).perform(click());

      //  onView(withId(R.id.toolbarTextV)).check(matches(withText("Nutella Pie")));

    }
}
