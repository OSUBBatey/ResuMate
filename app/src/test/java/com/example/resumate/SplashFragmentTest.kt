package com.example.resumate

import org.junit.Assert
import org.junit.Test
import com.example.resumate.ui.fragment.SplashFragment

class SplashFragmentTest {

    @Test
    fun splashFragmentInit_isCorrect() {
        val splashFragment = SplashFragment.newInstance()
        Assert.assertEquals(true, splashFragment is SplashFragment)
    }
}