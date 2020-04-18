package com.example.resumate.ui.activity

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.example.resumate.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class RecyclerActivityTest {

    @Rule
    @JvmField
    var nActivityTestRule = ActivityTestRule(RecyclerActivity::class.java)


    @Test
    fun recyclerActivityTest() {
        //Ensure view elements are properly loaded
        onView((withId(R.id.recyclerView))).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView((withId(R.id.skill_TextBox))).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView((withId(R.id.add_skill_button))).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView((withId(R.id.remove_skill_button))).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView((withId(R.id.edit_button))).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }
}