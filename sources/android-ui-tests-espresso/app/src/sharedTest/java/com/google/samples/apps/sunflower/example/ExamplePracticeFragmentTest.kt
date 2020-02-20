package com.google.samples.apps.sunflower.example

import android.os.Bundle
import androidx.fragment.app.testing.launchFragment
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.samples.apps.sunflower.PracticeFragment
import com.google.samples.apps.sunflower.R
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ExamplePracticeFragmentTest {

    @Test
    fun isDisplayedEditButtonTest() {
        launchFragmentInContainer<PracticeFragment>(themeResId = R.style.AppTheme)
        onView(withId(R.id.enter_name_button)).check(matches(withText("Enter")))
    }

    @Test
    fun inputNameTest() {

        launchFragmentInContainer<PracticeFragment>(themeResId = R.style.AppTheme)

        // onView(ViewMatcher = withId(id)).perform(ViewAction = replaceText(string))
        onView(withId(R.id.name_edit_text)).perform(replaceText("Bob"))

        // onView(ViewMatcher = withId(id)).perform(ViewAction = click)
        onView(withId(R.id.enter_name_button)).perform(click())

        // onView(ViewMatcher = withId(id).check(ViewAssertion = matches(ViewMatcher = withText(string)))
        onView(withId(R.id.entered_name_text)).check(matches(withText("Bob")))
    }

    @Test
    fun withContentDescriptionMatcherTest() {
        launchFragmentInContainer<PracticeFragment>(themeResId = R.style.AppTheme)

        onView(withId(R.id.name_edit_text)).perform(replaceText("Bob"))
        onView(withId(R.id.enter_name_button)).perform(click())

        // ContentDescriptionでの検索
        onView(withContentDescription("entered name")).check(matches(withText("Bob")))
    }

    @Test
    fun descendantMatcherTest() {
        launchFragmentInContainer<PracticeFragment>(themeResId = R.style.AppTheme)

        //未入力のままedit_name_buttonを押すと、'名前が空です'とエラーがでる
        onView(withId(R.id.enter_name_button)).perform(click())

        // textinput_errorはTextInputLayout内のエラーメッセージを表示するTextViewに自動で設定されるID
        // 画面内にTextInputLayoutが複数ある場合、textinput_errorをidに持つTextViewも複数存在することになるので、
        // 親のTextInputLayoutをisDescendantOfAで指定することで、どのTextViewかを絞ることができる
        onView(allOf(
                isDescendantOfA(withId(R.id.name_input_layout)),
                withId(R.id.textinput_error))).check(matches(withText("名前が空です")))
    }


    @Test
    fun clearTextTest() {
        launchFragmentInContainer<PracticeFragment>(themeResId = R.style.AppTheme)
        //すこし長い文字列をいれたとき、スクロールしないとclearボタンが押せないレイアウト

        onView(withId(R.id.name_edit_text)).perform(replaceText("渋谷SWET太郎"))
        onView(withId(R.id.enter_name_button)).perform(click())
        onView(withId(R.id.entered_name_text)).check(matches(withText("渋谷SWET太郎")))

        // 画面外に表示される可能性があるViewのaction指定時にはscrollTo()をつけてあげると安心
        onView(withId(R.id.clear_button)).perform(scrollTo(), click())

        onView(withId(R.id.entered_name_text)).check(matches(withText("")))
    }

    // ここからはFragmentScenarioのUsage

    // Fragmentのpublicなメソッド/フィールドにアクセスしたいときはonFragmentを使用できる
    @Test
    fun callFragmentMethod() {

        val scenario = launchFragmentInContainer<PracticeFragment>(themeResId = R.style.AppTheme)

        onView(withId(R.id.name_edit_text)).perform(replaceText("Bob"))
        onView(withId(R.id.enter_name_button)).perform(click())
        onView(withId(R.id.entered_name_text)).check(matches(withText("Bob")))

        // Fragmentのpublicなメソッド/フィールドにアクセスしたいときはonFragmentを使用できる
        // onFragmentのblock内はメインスレッドで実行されるが、EspressoのAPIはメインスレッドで呼び出せないのでブロックの中では呼ばない
        scenario.onFragment {
            it.clearText()
        }

        // Fragment操作後にEspressoのAPIを使いたいときは、blockの外に続けて書いていく
        // Espressoが自動でonFragmentブロック内のメインスレッド操作を待ってくれるはずだが、もしうまくいかないときはUIAutomatorのwaitUntilなどのwait処理を入れる
        onView(withId(R.id.entered_name_text)).check(matches(withText("")))
    }

    // Fragmentを任意のStateに変更したいとき


    // リソースが不足でFragmentが破棄された後に再生成されたときのSimulate
    @Test
    fun fragmentRecreateTest() {

        // input_name_textの文字列をsavedInstanceStateに保存して、画面生成時に復元している

        val scenario = launchFragmentInContainer<PracticeFragment>(themeResId = R.style.AppTheme)

        onView(withId(R.id.name_edit_text)).perform(replaceText("Bob"))
        onView(withId(R.id.enter_name_button)).perform(click())
        onView(withId(R.id.entered_name_text)).check(matches(withText("Bob")))

        scenario.recreate()

        onView(withId(R.id.entered_name_text)).check(matches(withText("Bob")))
    }

    @Test
    fun changeFragmentStateTest() {

        // EspressoのAPIを使ってUIの検証をしない場合は、ヘッドレスでFragmentを起動できる
        val scenario = launchFragment<PracticeFragment>(themeResId = R.style.AppTheme)

        // CREATED/STARTED/RESUME/DESTROYEDが使用可能
        // STARTEDはAPI LEVEL 24以上でしか使えない
        scenario.moveToState(Lifecycle.State.STARTED)

        // STARTEDに変更されたときの状態のテストをここに書く
    }

    //FragmentへのArgsを渡して起動
    @Test
    fun launchFragmentWithArgsTest() {

        val bundle = Bundle()
        bundle.putString("key", "value")

        val scenario = launchFragment<PracticeFragment>(bundle, themeResId = R.style.AppTheme)

        scenario.onFragment {
            assertThat(bundle.getString("key"), equalTo("value"))
        }
    }

}