package com.google.samples.apps.sunflower

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PracticeActivityTest {


    // 各テストでの起動と終了を自動でやってくれる
    // 手動で各テストごとに起動させる場合はval scenario = launchActivity<GardenActivity>()が使用できる
    @get:Rule
    var scenarioRule = activityScenarioRule<PracticeActivity>()

    val scenario: ActivityScenario<PracticeActivity>
        get() = scenarioRule.scenario

    @Test
    fun titleIsDisplayed() {
        onView(withText("Practice")).check(matches(isDisplayed()))
    }
}