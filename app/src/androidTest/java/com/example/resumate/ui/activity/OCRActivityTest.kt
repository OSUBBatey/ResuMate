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
class OCRActivityTest {

    @Rule
    @JvmField
    var nActivityTestRule = ActivityTestRule(OCRActivity::class.java)


    @Test
    fun ocrActivityTest() {
        //Ensure view elements are properly loaded
        onView((withId(R.id.bgImage))).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView((withId(R.id.choose_button))).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView((withId(R.id.ocr_button))).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView((withId(R.id.view_button))).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView((withId(R.id.compare_button))).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView((withId(R.id.logout_button))).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }
}