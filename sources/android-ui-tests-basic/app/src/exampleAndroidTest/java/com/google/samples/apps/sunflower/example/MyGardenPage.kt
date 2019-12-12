package com.google.samples.apps.sunflower.example

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import com.google.samples.apps.sunflower.R
import org.hamcrest.Matchers

object MyGardenPage {
    fun assertPlanted(plantName: String): MyGardenPage {
        // MyGardenPage#assertPlanted(plantName: String): MyGardenPage
        val textView = Espresso.onView(
                Matchers.allOf(ViewMatchers.withId(R.id.plant_date), ViewMatchers.withText("$plantName planted"),
                        childAtPosition(
                                childAtPosition(
                                        ViewMatchers.withId(R.id.garden_list),
                                        0),
                                1),
                        ViewMatchers.isDisplayed()))
        textView.check(ViewAssertions.matches(ViewMatchers.withText("$plantName planted")))
        return this
    }

    fun goPlantList(): PlantListPage {
        // MyGardenPage#goPlantList(): PlantListPage
        val appCompatImageButton = Espresso.onView(
                Matchers.allOf(ViewMatchers.withContentDescription("Open navigation drawer"),
                        childAtPosition(
                                Matchers.allOf(ViewMatchers.withId(R.id.toolbar),
                                        childAtPosition(
                                                ViewMatchers.withId(R.id.appbar),
                                                0)),
                                1),
                        ViewMatchers.isDisplayed()))
        appCompatImageButton.perform(ViewActions.click())

        val navigationMenuItemView = Espresso.onView(
                Matchers.allOf(childAtPosition(
                        Matchers.allOf(ViewMatchers.withId(R.id.design_navigation_view),
                                childAtPosition(
                                        ViewMatchers.withId(R.id.navigation_view),
                                        0)),
                        2),
                        ViewMatchers.isDisplayed()))
        navigationMenuItemView.perform(ViewActions.click())
        return PlantListPage
    }
}