package com.google.samples.apps.sunflower.example

import androidx.navigation.findNavController
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.samples.apps.sunflower.*
import com.google.samples.apps.sunflower.CustomViewAction.clickDescendantViewWithId
import com.google.samples.apps.sunflower.CustomViewMatcher.withItemViewAtPosition
import com.google.samples.apps.sunflower.adapters.PlantAdapter
import org.hamcrest.CoreMatchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ExamplePlantListRecyclerViewTest {

    @get:Rule
    var scenarioRule = activityScenarioRule<GardenActivity>()

    private val scenario: ActivityScenario<GardenActivity>
        get() = scenarioRule.scenario

    private val plantList: Array<PlantData> = PlantHelper.plantList()
    private val totalPlantSize: Int = plantList.size

    private val dataBindingIdlingResource = DataBindingIdlingResource()

    //PlantListFragmentからPlantDetailFragmentに遷移するテストを実装するため、ActivityからPlantListFragmentにを起動する
    @Before
    fun setUp() {

        IdlingRegistry.getInstance().register(dataBindingIdlingResource)

        scenario.onActivity {
            it.findNavController(R.id.garden_nav_fragment).navigate(R.id.plant_list_fragment, null)
        }
        dataBindingIdlingResource.monitorActivity(scenario)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(dataBindingIdlingResource)
    }

    @Test
    fun clickActionOnItemAtPositionTest() {

        onView(withId(R.id.plant_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition<PlantAdapter.ViewHolder>(3, click()))

        // 詳細画面が表示されて、plane nameがTomatoであること確認
        onView(withId(R.id.plant_name))
                .check(matches(withText("Tomato")))
    }

    @Test
    fun clickActionOnItemTest() {

        onView(withId(R.id.plant_list))
                .perform(RecyclerViewActions.actionOnItem<PlantAdapter.ViewHolder>(
                        hasDescendant(withText("Pear")), click()))

        // 詳細画面が表示されて、plane nameがPearであること確認
        onView(withId(R.id.plant_name))
                .check(matches(withText("Pear")))
    }

    @Test
    fun assertIsDisplayed_withItemViewAtPositionTest() {

        onView(withText("Pink & White Lady's Slipper"))
                .check(doesNotExist())

        // position指定してscrollTo
        onView(withId(R.id.plant_list))
                .perform(RecyclerViewActions.scrollToPosition<PlantAdapter.ViewHolder>(totalPlantSize - 1))

        // カスタムマッチャーを使う
        // 指定positionのitemのTextViewがプラントのネームを表示している
        onView(allOf(
                isDescendantOfA(withItemViewAtPosition(withId(R.id.plant_list), totalPlantSize - 1)),
                withId(R.id.plant_item_title)))
                .check(matches(withText("Pink & White Lady's Slipper")))
                .check(matches(isDisplayed()))
    }

    @Test
    fun clickItemTitleView_clickDescendantViewWithIdTest() {

        // カスタムアクションを使う
        onView(withId(R.id.plant_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition<PlantAdapter.ViewHolder>(
                        totalPlantSize - 1,
                        clickDescendantViewWithId(R.id.plant_item_title)))

        // Snackbarが表示されて、指定した位置のplane nameであること確認
        onView(withId(R.id.snackbar_text)).check(matches(withText("clicked title: Pink & White Lady's Slipper")))
    }
}
