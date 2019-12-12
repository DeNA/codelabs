package com.google.samples.apps.sunflower.example

import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.google.samples.apps.sunflower.GardenActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * テストを実行する場合、app/build.gradleの以下のコメントアウトを外した後にプロジェクトのSyncをしてください
 *
 * sourceSets {
 *        androidTest {
 *            java.srcDir 'src/exampleAndroidTest/java' }
 * }
 *
 */
@LargeTest
@RunWith(AndroidJUnit4::class)
class GardenActivityTest {

    @get:Rule
    val activityScenarioRule = activityScenarioRule<GardenActivity>()

    @Test
    fun gardenActivityTest() {
        MyGardenPage.goPlantList()
                .showPlantDetail("Avocado")
                .addToMyGarden()
                .goBackPlantList()
                .goBackMyGarden()
                .assertPlanted("Avocado")
    }
}

