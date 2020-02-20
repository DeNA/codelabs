/*
 * Copyright (C) 2019 TOYAMA Sumio <jun.nama@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.samples.apps.sunflower.utilities

import androidx.annotation.VisibleForTesting
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

// ref:https://github.com/DroidKaigi/conference-app-2019/

object DispatchersWrapper {
    val Main get() = CoroutinePlugin.mainDispatcher
    val IO get() = CoroutinePlugin.ioDispatcher
    val Default get() = CoroutinePlugin.defaultDispatcher
}

object CoroutinePlugin {

    private val defaultIoDispatcher: CoroutineContext = Dispatchers.IO
    val ioDispatcher: CoroutineContext
        get() = ioDispatcherHandler?.invoke(
                defaultIoDispatcher
        ) ?: defaultIoDispatcher

    @set:VisibleForTesting
    var ioDispatcherHandler: ((CoroutineContext) -> CoroutineContext)? = null

    private val defaultComputationDispatcher: CoroutineContext = Dispatchers.Default
    val defaultDispatcher: CoroutineContext
        get() = computationDispatcherHandler?.invoke(
                defaultComputationDispatcher
        ) ?: defaultComputationDispatcher

    @set:VisibleForTesting
    var computationDispatcherHandler: ((CoroutineContext) -> CoroutineContext)? = null

    private val defaultMainDispatcher: CoroutineContext = Dispatchers.Main
    val mainDispatcher: CoroutineContext
        get() = mainDispatcherHandler?.invoke(
                defaultMainDispatcher
        ) ?: defaultMainDispatcher
    @set:VisibleForTesting
    var mainDispatcherHandler: ((CoroutineContext) -> CoroutineContext)? = null

    @VisibleForTesting @JvmStatic fun reset() {
        ioDispatcherHandler = null
        computationDispatcherHandler = null
        mainDispatcherHandler = null
    }
}
