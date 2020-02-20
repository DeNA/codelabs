package com.google.samples.apps.sunflower.example

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.samples.apps.sunflower.PracticeActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ExamplePracticeActivityTest {


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

    // ActivityScenario

    // Activityを任意のStateに変更したいとき
    @Test
    fun changeActivityStateTest() {

        val scenario = scenarioRule.scenario

        // CREATED/STARTED/RESUME/DESTROYEDが使用可能
        scenario.moveToState(Lifecycle.State.STARTED)

        // STARTEDに変更されたときの状態のテストをここに書く
    }

    //リソースが不足でActivityが破棄された後に再生成されたときのSimulate
    // 具体的な実装例はPracticeFragmentTest#fragmentRecreateTestを参考
    @Test
    fun recreateActivityTest() {
        val scenario = scenarioRule.scenario
        scenario.recreate()
    }

    // Activityの任意のpublicなメソッド/フィールドにアクセスしたいとき
    // 具体的な実装例はPracticeFragmentTest#callFragmentMethodTestを参考
    @Test
    fun callActivityMethodTest() {
        scenario.onActivity { activity ->
            activity.someMethod()
        }
    }
}