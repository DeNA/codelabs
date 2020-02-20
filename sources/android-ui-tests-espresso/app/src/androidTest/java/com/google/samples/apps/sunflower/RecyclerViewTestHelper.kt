package com.google.samples.apps.sunflower

import android.app.Application
import androidx.test.core.app.ApplicationProvider
import com.google.gson.Gson
import com.google.samples.apps.sunflower.utilities.PLANT_DATA_FILENAME
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

data class PlantData(val json: JSONObject) {
    val plantId: String
    val name: String

    init {
        try {
            plantId = json.getString("plantId")
            name = json.getString("name")
        } catch (e: JSONException) {
            throw IllegalStateException("テストデータの生成に失敗しました. : $json", e) as Throwable
        }
    }
}

class PlantHelper {
    companion object {
        fun plantList(): Array<PlantData> {
            val plantList: Array<PlantData>
            try {
                val fileName = PLANT_DATA_FILENAME
                val jsonString = readFromAsset(fileName)
                plantList = Gson().fromJson(jsonString, Array<PlantData>::class.java)
            } catch (e: JSONException) {
                throw IllegalStateException("テストデータが読み込めませんでした", e)
            } catch (e: IOException) {
                throw IllegalStateException("テストデータが読み込めませんでした", e)
            }

            return plantList
        }

        @Throws(IOException::class)
        private fun readFromAsset(file: String): String {
            val manager = ApplicationProvider.getApplicationContext<Application>().assets
            return manager.open(file).reader(charset = Charsets.UTF_8).use { it.readText() }
        }
    }
}