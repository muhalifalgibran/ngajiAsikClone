package id.alif.footbalmatchschedule


import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.swipeLeft
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import id.alif.footbalmatchschedule.R.id.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

import id.alif.footbalmatchschedule.main.MainActivity


@RunWith(AndroidJUnit4::class)
class AppTest {
    @Rule
    @JvmField var activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testRecyclerViewBehaviour() {


    }

    @Test
    fun TestAppBehavior(){

        Thread.sleep(5000);
        Espresso.onView(ViewMatchers.withId(R.id.recyclerLastMatchlist))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.recyclerLastMatchlist))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10))
        Espresso.onView(ViewMatchers.withId(R.id.recyclerLastMatchlist))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(5, click()))
        Thread.sleep(5000);
        Espresso.onView(ViewMatchers.withId(add_to_favorite))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(add_to_favorite)).perform(click())
        Espresso.pressBack()
        Espresso.pressBack()
        Espresso.onView(withId(R.id.viewpager_main)).perform(swipeLeft())

        Thread.sleep(5000);
        Espresso.onView(ViewMatchers.withId(R.id.recyclerNextMatchlist))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.recyclerNextMatchlist))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10))
        Espresso.onView(ViewMatchers.withId(R.id.recyclerNextMatchlist))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(5, click()))
        Thread.sleep(5000);
        Espresso.onView(ViewMatchers.withId(R.id.add_to_favorite))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(add_to_favorite)).perform(click())
        Espresso.pressBack()
        Espresso.pressBack()
        Espresso.onView(withId(R.id.viewpager_main)).perform(swipeLeft())

        Thread.sleep(5000);
        Espresso.onView(ViewMatchers.withId(R.id.recyclerFavMatchlist))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.recyclerFavMatchlist))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(1))
        Espresso.onView(ViewMatchers.withId(R.id.recyclerFavMatchlist))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        Thread.sleep(5000);
        Espresso.onView(ViewMatchers.withId(R.id.add_to_favorite))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(add_to_favorite)).perform(click())

    }

}