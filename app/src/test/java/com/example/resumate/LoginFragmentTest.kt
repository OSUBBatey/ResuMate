package com.example.resumate

import org.junit.Assert
import org.junit.Test
import com.example.resumate.ui.fragment.LoginFragment

class LoginFragmentTest {
    @Test
    fun loginFragmentInit_isCorrect() {
        var loginFragment = LoginFragment.newInstance()
        Assert.assertEquals(true, loginFragment is LoginFragment)
    }

    @Test
    fun emailOrPassEmpty_isCorrect() {
        val loginFragment = LoginFragment.newInstance()
        val email = "test@gmail.com"
        val password = "123456"
        Assert.assertEquals(false, loginFragment.emailOrPassEmpty(email, password))
    }
}