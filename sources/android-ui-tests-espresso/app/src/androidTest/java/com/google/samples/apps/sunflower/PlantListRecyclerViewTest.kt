package com.google.samples.apps.sunflower

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
import com.google.samples.apps.sunflower.adapters.PlantAdapter
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PlantListRecyclerViewTest {

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

        // TODO RecyclerView(id:plant_list)のアイテム(position = 3)をスクロール + クリック

        // 詳細画面が表示されて、plane nameがTomatoであること確認
        onView(withId(R.id.plant_name)).check(matches(withText("Tomato")))
    }

    @Test
    fun clickActionOnItemTest() {

        // TODO RecyclerView(id:plant_list)のPearをテキストに持つアイテムまでスクロール後、clickする

        // 詳細画面が表示されて、plane nameがPearであること確認
        onView(withId(R.id.plant_name)).check(matches(withText("Pear")))
    }

    @Test
    fun assertIsDisplayed_withItemViewAtPositionTest() {

        // 一番最後のリストアイテム内のR.id.plant_item_titleが正しいことを検証する

        // 一番最後の要素が表示されていないことを確認
        onView(withText("Pink & White Lady's Slipper")).check(doesNotExist())

        // 最後の要素までscrollTo
        onView(withId(R.id.plant_list))
                .perform(RecyclerViewActions.scrollToPosition<PlantAdapter.ViewHolder>(totalPlantSize - 1))

        // TODO カスタムマッチャーのwithItemViewAtPositionを使って、アイテムビュー子TextViewを指定する
        val matcher = withId(R.id.plant_list)

        onView(matcher)
                .check(matches(withText("Pink & White Lady's Slipper")))
                .check(matches(isDisplayed()))
    }

    @Test
    fun clickItemTitleView_clickDescendantViewWithIdTest() {


        // 一番最後のリストアイテム内のR.id.plant_item_titleを押したときにSnackBarが表示されることを検証する
        // アイテムビュー全体をクリックでは詳細画面に遷移してしまう

        // TODO click()をカスタムアクションのclickDescendantViewWithIdを使うように変更する
        val action = click()

        // カスタムアクションを使う
        onView(withId(R.id.plant_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition<PlantAdapter.ViewHolder>(
                        totalPlantSize - 1,
                        action))

        // 指定した位置のplane nameであることを確認
        onView(withId(R.id.snackbar_text)).check(matches(withText("clicked title: Pink & White Lady's Slipper")))
    }
}
