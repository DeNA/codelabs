package com.google.samples.apps.sunflower

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PracticeFragmentTest {

    @Test
    fun isDisplayedEditButtonTest() {
        launchFragmentInContainer<PracticeFragment>(themeResId = R.style.AppTheme)

        //TODO idがenter_name_buttonのViewに表示されている文字列が'Enter'であることを確認する
    }

    @Test
    fun inputNameTest() {

        launchFragmentInContainer<PracticeFragment>(themeResId = R.style.AppTheme)

        //TODO idがname_edit_textのEditTextに、'Bob'と入力する

        //TODO idがenter_name_buttonのViewをクリックする

        //TODO idがentered_name_textのViewに表示されている文字列が'Bob'であることを確認する
    }

    @Test
    fun descendantMatcherTest() {
        launchFragmentInContainer<PracticeFragment>(themeResId = R.style.AppTheme)

        // 未入力のままedit_name_buttonを押すと、TextInputLayout内に'名前が空です'とエラーが表示されるので、そのエラーメッセージを検証する

        onView(withId(R.id.enter_name_button)).perform(click())

        //TODO テキストが'名前が空です'であることを検証する
        //   - TextInputLayoutのidはR.id.name_input_layout
        //   - その子孫であるエラーメッセージを表示しているTextViewのidはR.id.textinput_error
    }

    @Test
    fun withContentDescriptionMatcherTest() {
        launchFragmentInContainer<PracticeFragment>(themeResId = R.style.AppTheme)

        onView(withId(R.id.name_edit_text)).perform(replaceText("Bob"))
        onView(withId(R.id.enter_name_button)).perform(click())

        //TODO contentDescriptionが'entered name'のViewに表示されている 文字列が'Bob'であることを確認する
    }

    @Test
    fun clearTextTest() {
        launchFragmentInContainer<PracticeFragment>(themeResId = R.style.AppTheme)
        //すこし長い文字列をいれたとき、スクロールしないとclearボタンが押せないレイアウト

        onView(withId(R.id.name_edit_text)).perform(replaceText("渋谷SWET太郎"))
        onView(withId(R.id.enter_name_button)).perform(click())
        onView(withId(R.id.entered_name_text)).check(matches(withText("渋谷SWET太郎")))

        //TODO ViewActionに複数指定する: idがclear_buttonのViewまでScrollした後Clickをする

        onView(withId(R.id.entered_name_text)).check(matches(withText("")))
    }
}