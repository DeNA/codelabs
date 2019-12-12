package com.google.samples.apps.sunflower.example

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import com.google.samples.apps.sunflower.R
import org.hamcrest.Matchers

object PlantDetailPage {
    fun goBackPlantList(): PlantListPage {
        // PlantDetailPage#goBackPlantList(): PlantListPage
        val appCompatImageButton2 = Espresso.onView(
                Matchers.allOf(ViewMatchers.withContentDescription("Navigate up"),
                        childAtPosition(
                                Matchers.allOf(ViewMatchers.withId(R.id.toolbar),
                                        childAtPosition(
                                                ViewMatchers.withId(R.id.appbar),
                                                0)),
                                1),
                        ViewMatchers.isDisplayed()))
        appCompatImageButton2.perform(ViewActions.click())
        return PlantListPage
    }

    fun addToMyGarden(): PlantDetailPage {
        // PlantDetailPage#addToMyGarden(): PlantDetailPage
        val floatingActionButton = Espresso.onView(
                Matchers.allOf(ViewMatchers.withId(R.id.fab),
                        childAtPosition(
                                childAtPosition(
                                        ViewMatchers.withId(R.id.garden_nav_fragment),
                                        0),
                                2),
                        ViewMatchers.isDisplayed()))
        floatingActionButton.perform(ViewActions.click())
        return this
    }
}