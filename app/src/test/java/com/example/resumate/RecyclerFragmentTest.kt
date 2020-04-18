package com.example.resumate

import org.junit.Assert
import org.junit.Test
import com.example.resumate.ui.fragment.RecyclerFragment


class RecyclerFragmentTest {
    @Test
    fun displayResultsFragmentInit_isCorrect() {
        var recyclerFragment = RecyclerFragment.newInstance()
        Assert.assertEquals(true, recyclerFragment is RecyclerFragment)
    }
}