package com.google.samples.apps.sunflower

import android.view.InputDevice
import android.view.MotionEvent
import android.view.View
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.GeneralClickAction
import androidx.test.espresso.action.GeneralLocation
import androidx.test.espresso.action.Press
import androidx.test.espresso.action.Tap
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

object CustomViewMatcher {
    fun withItemViewAtPosition(recyclerView: Matcher<View>, position: Int): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            override fun matchesSafely(view: View): Boolean {
                // アイテムビューの親は必ずRecyclerViewとなる
                val parent = view.parent
                if (parent !is RecyclerView || !recyclerView.matches(parent)) {
                    return false
                }

                // 親RecyclerViewから取得した位置番号positionのアイテムビューと比較する
                val viewHolder = parent.findViewHolderForAdapterPosition(position)
                return viewHolder != null && viewHolder.itemView == view
            }

            override fun describeTo(description: Description) {
                description.appendText("with error: ")
                recyclerView.describeTo(description)
            }
        }
    }
}

object CustomViewAction {
    fun clickDescendantViewWithId(@IdRes id: Int): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return ViewMatchers.hasDescendant(ViewMatchers.withId(id))
            }

            override fun getDescription(): String {
                return String.format(
                        "performing Click Action with id matching: %d", id)
            }

            override fun perform(uiController: UiController, view: View) {
                val action = GeneralClickAction(Tap.SINGLE,
                        GeneralLocation.VISIBLE_CENTER,
                        Press.FINGER,
                        InputDevice.SOURCE_UNKNOWN,
                        MotionEvent.BUTTON_PRIMARY)
                val target = view.findViewById<View>(id)
                action.perform(uiController, target)
            }
        }
    }
}