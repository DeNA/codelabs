package com.google.samples.apps.sunflower.example

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import com.google.samples.apps.sunflower.R
import org.hamcrest.Matchers

object PlantListPage {
    fun goBackMyGarden(): MyGardenPage {
        // PlantListPage#goBackMyGarden(): MyGardenPage
        val appCompatImageButton3 = Espresso.onView(
                Matchers.allOf(ViewMatchers.withContentDescription("Navigate up"),
                        childAtPosition(
                                Matchers.allOf(ViewMatchers.withId(R.id.toolbar),
                                        childAtPosition(
                                                ViewMatchers.withId(R.id.appbar),
                                                0)),
                                1),
                        ViewMatchers.isDisplayed()))
        appCompatImageButton3.perform(ViewActions.click())
        return MyGardenPage
    }

    fun showPlantDetail(plantName: String): PlantDetailPage {
        // PlantListPage#showPlantDetail(plantName: String): PlantDetailPage
        val appCompatTextView = Espresso.onView(
                Matchers.allOf(ViewMatchers.withId(R.id.plant_item_title), ViewMatchers.withText(plantName),
                        childAtPosition(
                                childAtPosition(
                                        ViewMatchers.withId(R.id.plant_list),
                                        1),
                                0),
                        ViewMatchers.isDisplayed()))
        appCompatTextView.perform(ViewActions.click())
        return PlantDetailPage
    }
}