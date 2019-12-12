package com.google.samples.apps.sunflower


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class GardenActivityTest2 {

    @get:Rule
    val activityScenarioRule = activityScenarioRule<GardenActivity>()

    @Test
    fun gardenActivityTest() {

        // MyGardenPage#goPlantList(): PlantListPage
        val appCompatImageButton = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withId(R.id.appbar),
                                                0)),
                                1),
                        isDisplayed()))
        appCompatImageButton.perform(click())

        val navigationMenuItemView = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.design_navigation_view),
                                childAtPosition(
                                        withId(R.id.navigation_view),
                                        0)),
                        2),
                        isDisplayed()))
        navigationMenuItemView.perform(click())


        // PlantListPage#showPlantDetail(plantName: String): PlantDetailPage
        val appCompatTextView = onView(
                allOf(withId(R.id.plant_item_title), withText("Avocado"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.plant_list),
                                        1),
                                0),
                        isDisplayed()))
        appCompatTextView.perform(click())

        // PlantDetailPage#addToMyGarden(): PlantDetailPage
        val floatingActionButton = onView(
                allOf(withId(R.id.fab),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.garden_nav_fragment),
                                        0),
                                2),
                        isDisplayed()))
        floatingActionButton.perform(click())

        // PlantDetailPage#goBackPlantList(): PlantListPage
        val appCompatImageButton2 = onView(
                allOf(withContentDescription("Navigate up"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withId(R.id.appbar),
                                                0)),
                                1),
                        isDisplayed()))
        appCompatImageButton2.perform(click())

        // PlantListPage#goBackMyGarden(): MyGardenPage
        val appCompatImageButton3 = onView(
                allOf(withContentDescription("Navigate up"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withId(R.id.appbar),
                                                0)),
                                1),
                        isDisplayed()))
        appCompatImageButton3.perform(click())

        // MyGardenPage#assertPlanted(plantName: String): MyGardenPage
        val textView = onView(
                allOf(withId(R.id.plant_date), withText("Avocado planted"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.garden_list),
                                        0),
                                1),
                        isDisplayed()))
        textView.check(matches(withText("Avocado planted")))
    }

    private fun childAtPosition(
            parentMatcher: Matcher<View>, position: Int): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
