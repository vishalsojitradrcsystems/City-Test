package com.city.test.presentation.gallery

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.city.test.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {


    @get:Rule
    var activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun container_IsDisplayed() {
        onView(withId(R.id.nav_host_fragment)).check(matches(isDisplayed()))
    }

}