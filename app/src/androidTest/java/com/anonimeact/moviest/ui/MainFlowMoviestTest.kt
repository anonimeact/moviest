package com.anonimeact.moviest.ui

import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario.launch
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeDown
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.anonimeact.moviest.MainActivity
import com.anonimeact.moviest.R
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainFlowMoviestTest {

    @Test
    fun testRecyclerViewScroll() {
        val scenario = launch(MainActivity::class.java)
        scenario.moveToState(Lifecycle.State.RESUMED)
        Thread.sleep(5000)
        onView(withId(R.id.rvMovieList)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(19)
        )
        Thread.sleep(5000)
        onView(withId(R.id.rvMovieList))
            .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(18, click()))
        Thread.sleep(5000)
        onView(withId(R.id.fab_favorite)).perform(click())
        Thread.sleep(5000)
        Espresso.pressBack()
        onView(withId(R.id.rvMovieList)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0)
        )
        Thread.sleep(1000)
        onView(withId(R.id.swipe_to_reload)).perform(swipeDown())
        Thread.sleep(5000)
        onView(withId(R.id.fab_show_favorite)).perform(click())
        Thread.sleep(3000)
        onView(withId(R.id.rv_favorite))
            .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        Thread.sleep(5000)
        Espresso.pressBack()
        Thread.sleep(5000)
        Espresso.pressBack()
        Thread.sleep(5000)
    }
}