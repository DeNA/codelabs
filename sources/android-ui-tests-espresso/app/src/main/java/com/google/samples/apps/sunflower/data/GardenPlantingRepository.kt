/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.samples.apps.sunflower.data

import android.os.SystemClock
import com.google.samples.apps.sunflower.utilities.DispatchersWrapper
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class GardenPlantingRepository private constructor(
        private val gardenPlantingDao: GardenPlantingDao,
        private val ioDispatcher: CoroutineContext) {

    //ここが非同期処理になっているので明示的なdelayをいれる
    suspend fun createGardenPlanting(plantId: String): Boolean = withContext(ioDispatcher) {
        SystemClock.sleep(3000)
        val gardenPlanting = GardenPlanting(plantId)
        gardenPlantingDao.insertGardenPlanting(gardenPlanting)
        true
    }

    suspend fun removeGardenPlanting(gardenPlanting: GardenPlanting) {
        withContext(ioDispatcher) {
            gardenPlantingDao.deleteGardenPlanting(gardenPlanting)
        }
    }

    fun getGardenPlantingForPlant(plantId: String) =
            gardenPlantingDao.getGardenPlantingForPlant(plantId)

    fun getGardenPlantings() = gardenPlantingDao.getGardenPlantings()

    fun getPlantAndGardenPlantings() = gardenPlantingDao.getPlantAndGardenPlantings()

    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: GardenPlantingRepository? = null

        fun getInstance(gardenPlantingDao: GardenPlantingDao, ioDispatcher: CoroutineContext = DispatchersWrapper.IO) =
                instance ?: synchronized(this) {
                    instance
                            ?: GardenPlantingRepository(gardenPlantingDao, ioDispatcher).also { instance = it }
                }
    }
}