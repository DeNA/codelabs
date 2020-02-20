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

package com.google.samples.apps.sunflower.adapters

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.samples.apps.sunflower.data.Plant

@BindingAdapter("isGone")
fun bindIsGone(view: View, isGone: Boolean) {
    view.visibility = if (isGone) {
        View.GONE
    } else {
        View.VISIBLE
    }
}

// 画像ロードがすごく遅いため、一部ローカルからロードする様に変更
@BindingAdapter("imageFromPlant")
fun setImageViewResource(view: ImageView, plant: Plant?) {
    if (!plant?.localImage.isNullOrEmpty()) {
        val resId = view.resources.getIdentifier(plant?.localImage, "drawable", view.context.packageName)
        Glide.with(view.context)
                .load(resId)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(view)
    } else if (!plant?.imageUrl.isNullOrEmpty()) {
        Glide.with(view.context)
                .load(plant?.imageUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(view)
    }
}